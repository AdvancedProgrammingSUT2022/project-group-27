package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Enum.TechnologyType;
import controller.Game;
import Enum.LuxuryResource;
import Enum.StrategicResource;
import controller.UnitController;
import Enum.*;
public class Player {
    private int numberOfPlayer;
    private int gold;
    private City mainCapital = null;
    //private int science;
    private int extraHappiness = 0;
    private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Ground> clearToSeeGrounds = new ArrayList<>();
    private ArrayList<Ground> wasClearedToSeeGrounds = new ArrayList<>();
    private static int counterOfNextRound = 0;
    private User user;
    private Technology underConstructionTechnology=null;
    private boolean isAlive = true;
    private final ArrayList<Notification> notificationHistory = new ArrayList<>();
    private ArrayList<LuxuryResource> allLuxuryResources = new ArrayList<>();
    private ArrayList<StrategicResource> allStrategicResources = new ArrayList<>();
    private ArrayList<TechnologyType> technologyType=new ArrayList<>();
    private ArrayList<Technology> AllTechnologyTypes=new ArrayList<>();

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

    public Player(User user) {
        this.user = user;
        allPlayers.add(this);
        this.numberOfPlayer=allPlayers.size();
        for (TechnologyType technologyType : TechnologyType.values()) AllTechnologyTypes.add(new Technology(technologyType,technologyType.getCost()));

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

    public Technology getUnderConstructionTechnology() {
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
            for (Ground ground1 : ground.getAdjacentGrounds()){
                if (newArray.contains(ground1) || clearToSeeGrounds.contains(ground1)) continue;
                else newArray.add(ground1);
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
            if (unit.getMilitaryType()== MilitaryType.TREBUCHET || unit.getMilitaryType()== MilitaryType.PANZER ||
                    unit.getMilitaryType()== MilitaryType.CATAPULT) continue;
            this.addGroundToClearGround(unit.getGround());
        }

        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
        for (Unit unit : this.units) {
            if (unit.getMilitaryType() == MilitaryType.TREBUCHET || unit.getMilitaryType() == MilitaryType.PANZER ||
                    unit.getMilitaryType() == MilitaryType.CATAPULT) this.addGroundToClearGround(unit.getGround());
        }
        for (City city : this.cities) {
            for (int j = 0; j < city.getRangeOfCity().size(); j++) {
                this.addGroundToClearGround(city.getRangeOfCity().get(j));
            }
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds); //for the second depth
    }

    public static void nextTurn() {
        if (!Game.getInstance().canWeGoNextTurn()) return ;
        counterOfNextRound++;
        while (!whichPlayerTurnIs().isAlive) counterOfNextRound++;
        Game.getInstance().nextTurn();
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
    public void handleVisitedGrounds(){
        for (Ground ground : clearToSeeGrounds){
            if (!wasClearedToSeeGrounds.contains(ground)) wasClearedToSeeGrounds.add(ground);
        }
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
            if (cities.size() == 0) {
                city.setMainCapital();
                mainCapital = city;
            }
            this.cities.add(city);
            ground.setHasRuin(false);
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

    public void setUnderConstructionTechnology(Technology technology){
        underConstructionTechnology = technology;
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
            for (Building building: city.getBuildings()) {
                goldDifference -= building.getMaintenance();
            }
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
        int happiness = 15;
        int population = 0;
        happiness -= this.cities.size() * 3;
        for (City city : cities) {
            for (Building building : city.getBuildings()) {
                if (building.getType().equals(BuildingsType.BURIAL_TOMB) || building.getType().equals(BuildingsType.SATRAPS_COURT)) {
                    happiness += 2;
                }
                if (building.getType().equals(BuildingsType.CIRCUS) || building.getType().equals(BuildingsType.COURTHOUSE)) {
                    happiness += 3;
                }
                if (building.getType().equals(BuildingsType.COLOSSEUM) || building.getType().equals(BuildingsType.THEATER)) {
                    happiness += 4;
                }


            }
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

    public boolean doWeHaveOurCapital() {
        return mainCapital != null && mainCapital.getPlayer() == this;
    }

    public static int numberOfAliveAndHaveCapitalPlayer() {
        int count = 0;
        for (Player player: allPlayers) {
            if (player.isAlive() && player.doWeHaveOurCapital()) count++;
        }

        return count;
    }

    public static int getYear() {
        return counterOfNextRound / allPlayers.size();
    }

    public void checkDies() {
        if (isAlive && mainCapital != null) {
            if (cities.size() == 0) {
                isAlive = false;
                new Notification("You lose the game :(", getCounterOfNextRound(), this);
                nextTurn(); //TODO where should we call this method... for now we call it on gameView.run(); ...
            }
        }
    }

    public void setScoreAndTimeAtEnd() {
        int score = countScore();
        if (!isAlive) user.setScore(0);
        else if (getYear() < 2050){
            score *= (2050 - getYear());
            user.setScore(score);
        } else user.setScore(score);

        user.setTimeOfScoreGame(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public int countScore() {
        int count = 0;
        
        return count;
    }

    public City getCapital() {
        if (doWeHaveOurCapital()) return mainCapital;
        else if (!cities.isEmpty()) return cities.get(0);
        else return null;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
