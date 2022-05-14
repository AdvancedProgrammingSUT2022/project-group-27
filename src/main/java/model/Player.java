package model;

import java.util.ArrayList;

import Enum.TechnologyType;
import controller.Game;
import Enum.LuxuryResource;
import Enum.StrategicResource;
import controller.UnitController;

public class Player {
    private int numberOfPlayer;
    private int gold;
    //private int science;
    private int extraHappiness = 0;
    private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Ground> clearToSeeGrounds = new ArrayList<>();
    private ArrayList<Ground> wasClearedToSeeGrounds = new ArrayList<>();
    private static int counterOfNextRound = 0;
    private User user;
    private TechnologyType underConstructionTechnology=null;
    private boolean isAlive = true;
    private final ArrayList<Notification> notificationHistory = new ArrayList<>();
    private ArrayList<LuxuryResource> allLuxuryResources = new ArrayList<>();
    private ArrayList<StrategicResource> allStrategicResources = new ArrayList<>();

    public ArrayList<LuxuryResource> getAllLuxuryResources() {
        return allLuxuryResources;
    }

    public void setAllLuxuryResources(ArrayList<LuxuryResource> allLuxuryResources) {
        this.allLuxuryResources = allLuxuryResources;
    }

    public ArrayList<StrategicResource> getAllStrategicResources() {
        return allStrategicResources;
    }

    public void setAllStrategicResources(ArrayList<StrategicResource> allStrategicResources) {
        this.allStrategicResources = allStrategicResources;
    }

    public ArrayList<Notification> getNotificationHistory() {
        return notificationHistory;
    }

    public void increaseHappiness(int amount) {
        this.extraHappiness += amount;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

   // public void setScience(int science) {
    //    this.science = science;
   // }

    private ArrayList<TechnologyType> technologyType=new ArrayList<>();

    private ArrayList<Technology> AllTechnologyTypes=new ArrayList<>();

    public Player(User user) {
        this.user = user;
        allPlayers.add(this);
        this.numberOfPlayer=allPlayers.size();
        for (TechnologyType technologyType : TechnologyType.values()) AllTechnologyTypes.add(new Technology(technologyType));

    }
    public int getFood() {
        int food = 0;
        for (City city: getCities()) food += city.getSavedFood();
        return food;
    }

    public int getGold() {
        return gold;
    }

  //  public int getScience() {
  //      return science;
   // }

    public ArrayList<Ground> getClearToSeeGrounds() {
        return clearToSeeGrounds;
    }

    public ArrayList<Ground> getWasClearedToSeeGrounds() {
        return wasClearedToSeeGrounds;
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public static int getCounterOfNextRound() {
        return counterOfNextRound;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Technology> getAllTechnologyTypes() {
        return AllTechnologyTypes;
    }

    public TechnologyType getUnderConstructionTechnology() {
        return underConstructionTechnology;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean haveEnoughMoney(int amount) {
        return this.gold >= amount;
    }

    public void giveMoneyForBuying(int amount) {
        this.gold -= amount;
    }

    public void handleClearToSeeGrounds1depth(ArrayList<Ground> clearToSeeGrounds) {
        GlobalVariables globalVariables = new GlobalVariables();
        ArrayList<Ground> newArray = new ArrayList<>();
        for (int i = 0; i < clearToSeeGrounds.size(); i++) {
            Ground ground = clearToSeeGrounds.get(i);

            if (ground.getGroundType().isBlock() && ground.unitsOfASpecificPlayerInThisGround(this).size() == 0)
                continue;
            for (int x = ground.getxLocation() - globalVariables.tool6Zelie; x <= ground.getxLocation() + globalVariables.tool6Zelie; x++) {
                for (int y = ground.getyLocation() - globalVariables.arz6Zelie; y <= ground.getyLocation() + globalVariables.arz6Zelie; y++) {
                    if (x == 0 && y == 0) continue;

                    Ground newGround = Ground.getGroundByXY(x, y);
                    if (newGround == null) continue;

                    if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0
                            || newGround.getyLocation() == globalVariables.surfaceWidth - 1) continue;

                    boolean shouldWeAddThisGround = false;
                    for (Ground clearToSeeGround : clearToSeeGrounds) {
                        if (clearToSeeGround.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    for (Ground value : newArray) {
                        if (value.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    if (shouldWeAddThisGround) continue;

                    newArray.add(newGround);
                }
            }
        }

        clearToSeeGrounds.addAll(newArray);
    }
    public void addGroundToClearGround(Ground ground){
        boolean exist = false;
        for (Ground clearToSeeGround : clearToSeeGrounds) {
            if (clearToSeeGround.getNumber() == ground.getNumber()) {
                exist = true;
                break;
            }
        }

        if (exist) return;
        this.clearToSeeGrounds.add(ground);
    }
    public void handleClearToSee() {
        this.clearToSeeGrounds = new ArrayList<>();
        for (Unit unit : this.units) {
            this.addGroundToClearGround(unit.getGround());
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
        for (City city : this.cities) {
            for (int j = 0; j < city.getRangeOfCity().size(); j++) {
                this.addGroundToClearGround(city.getRangeOfCity().get(j));
            }
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds); //for the second depth
    }

    public static void nextTurn() {
        while (!whichPlayerTurnIs().isAlive) counterOfNextRound++;

        if (Game.getInstance().nextTurn()) counterOfNextRound++;
    }

    public static Player whichPlayerTurnIs() {
        return allPlayers.get(counterOfNextRound % allPlayers.size());
    }

    public void addGroundToVisitedGround(Ground ground) {
        boolean exist = false;
        for (Ground wasClearedToSeeGround : wasClearedToSeeGrounds) {
            if (wasClearedToSeeGround.getNumber() == ground.getNumber()) {
                exist = true;
                break;
            }
        }

        if (exist) return;
        Ground copyGround = ground.copyOfCurrentGround();
        wasClearedToSeeGrounds.add(copyGround);
    }

    public boolean isThisGroundVisible(Ground ground) {
        for (Ground clearToSeeGround : clearToSeeGrounds) {
            if (clearToSeeGround.getNumber() == ground.getNumber()) return true;
        }
        return false;
    }

    public void addCityToThisGround(Ground ground){

        boolean settlerExist=false;
        for (Unit unit : this.getUnits()){
            if (unit instanceof SettlerUnit && unit.ground.getNumber()==ground.getNumber()) settlerExist=true;
        }
        if (!settlerExist) return ;
        boolean canWeCreateCity = false;
        for (Ground adjacentGround : ground.getAdjacentGrounds()){
            if (adjacentGround.isInRangeOfCity()) return ;
        }
        for (Unit unit : this.units) {
            if (unit instanceof UnMilitaryUnit && unit.ground.getNumber() == ground.getNumber()) {
                canWeCreateCity = true;
                break;
            }
        }
        if (canWeCreateCity){
            /// TODO : change something;
            City city = new City(ground,"something", this);
            this.cities.add(city);
        }

        for (int i=0;i<this.getUnits().size();i++){
            if (this.getUnits().get(i) instanceof SettlerUnit && this.getUnits().get(i).getGround().getNumber()==ground.getNumber()){
                UnitController.deleteUnit(this.getUnits().get(i));
            }
        }

    }
    public boolean doWeHaveThisTechnology(TechnologyType technologyType){
        if (technologyType == null) return true;
        for (TechnologyType type : this.technologyType) {
            if (type == technologyType) return true;
        }
        return false;
    }

    public boolean canWeAddThisTechnology(TechnologyType technologyType){
        for (int i=0;i<technologyType.getPrerequisites().size();i++){
            if (!doWeHaveThisTechnology(technologyType.getPrerequisites().get(i))) return false;
        }
        return true ;
    }

    public ArrayList<Technology> technologiesThatCanBeObtained(){
        ArrayList<Technology> answer=new ArrayList<>();
        for (Technology allTechnologyType : AllTechnologyTypes) {
            if (doWeHaveThisTechnology(allTechnologyType.getTechnologyType())) continue;
            if (canWeAddThisTechnology(allTechnologyType.getTechnologyType())) answer.add(allTechnologyType);
        }
        return answer;
    }

    public void setUnderConstructionTechnology(TechnologyType technologyType){
        underConstructionTechnology=technologyType;
    }

    public void addTechnology(TechnologyType technologyType){
        this.technologyType.add(technologyType);
    }

    public ArrayList<TechnologyType> getTechnologyType() {
        return technologyType;
    }

    public int getGoldDifference() {
        double goldDifference = 0;
        for (City city : this.getCities()) {
            goldDifference += city.getGold();
        }
        for (Unit unit : this.getUnits()) {
            goldDifference -= (double) unit.getMilitaryType().getCost() / 100;
        }

        for (Ground ground: Ground.getAllGround()) {
            if (ground.ownerOfThisGround()!=null && ground.ownerOfThisGround().equals(this) && ground.getRoad() != null) {
                goldDifference -= 0.1;
            }
        }

        return (int) (goldDifference + 0.5);
    }

    public void increaseGold(int amount) {
        this.gold += amount;
    }

    public int getHappiness() {
        int happiness = 20;
        int population = 0;
        happiness -= this.cities.size() * 3;
        for (City city : cities) {
            if (city.isPuppet())
                happiness++;
            population += city.getListOfCitizens().size();
        }
        happiness -= population / 7;
        happiness += this.allLuxuryResources.size() * 3;
        return happiness + this.extraHappiness;
    }

    public boolean hasStrategicResource(StrategicResource strategicResource) {
        for (StrategicResource eachStrategicResource : this.allStrategicResources) {
            if (strategicResource.equals(eachStrategicResource))
                return true;
        }
        return false;
    }

    public int getScience() {
        int science = 0;
        for (City city : this.cities) {
            science += city.getScience();
        }
        if (this.gold <= 0)
            science = 0;
        return science;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
