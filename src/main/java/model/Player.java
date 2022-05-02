package model;

import java.util.ArrayList;
import java.util.List;

import Enum.TechnologyType;
import controller.Game;

public class Player {
    private int gold;
    private int science;
    private int food;
    private int happiness;
    private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Ground> clearToSeeGrounds = new ArrayList<>();
    private ArrayList<Ground> wasClearedToSeeGrounds = new ArrayList<>();
    private static int counterOfNextRound = 0;
    private User user;
    private TechnologyType underConstructionTechnology=null;
    private boolean isAlive = true;

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    private ArrayList<TechnologyType> technologyType=new ArrayList<>();

    private ArrayList<Technology> AllTechnologyTypes=new ArrayList<>();

    public Player(User user) {
        this.user = user;
        allPlayers.add(this);
        AllTechnologyTypes.add(new Technology(TechnologyType.AGRICULTURE));
        AllTechnologyTypes.add(new Technology(TechnologyType.ANIMAL_HUSBANDRY));
        AllTechnologyTypes.add(new Technology(TechnologyType.ARCHERY));
        AllTechnologyTypes.add(new Technology(TechnologyType.MINING));
        AllTechnologyTypes.add(new Technology(TechnologyType.POTTERY));
        AllTechnologyTypes.add(new Technology(TechnologyType.BRONZE_WORKING));
        AllTechnologyTypes.add(new Technology(TechnologyType.MASONRY));
        AllTechnologyTypes.add(new Technology(TechnologyType.THE_WHEEL));
        AllTechnologyTypes.add(new Technology(TechnologyType.TRAPPING));
        AllTechnologyTypes.add(new Technology(TechnologyType.WRITING));
        AllTechnologyTypes.add(new Technology(TechnologyType.CALENDAR));
        AllTechnologyTypes.add(new Technology(TechnologyType.CONSTRUCTION));
        AllTechnologyTypes.add(new Technology(TechnologyType.HORSEBACK_RIDING));
        AllTechnologyTypes.add(new Technology(TechnologyType.MATHEMATICS));
        AllTechnologyTypes.add(new Technology(TechnologyType.PHILOSOPHY));
        AllTechnologyTypes.add(new Technology(TechnologyType.IRON_WORKING));
        AllTechnologyTypes.add(new Technology(TechnologyType.METAL_CASTING));
        AllTechnologyTypes.add(new Technology(TechnologyType.CURRENCY));
        AllTechnologyTypes.add(new Technology(TechnologyType.ENGINEERING));
        AllTechnologyTypes.add(new Technology(TechnologyType.THEOLOGY));
        AllTechnologyTypes.add(new Technology(TechnologyType.CIVIL_SERVICE));
        AllTechnologyTypes.add(new Technology(TechnologyType.CHIVALRY));
        AllTechnologyTypes.add(new Technology(TechnologyType.EDUCATION));
        AllTechnologyTypes.add(new Technology(TechnologyType.MACHINERY));
        AllTechnologyTypes.add(new Technology(TechnologyType.PHYSICS));
        AllTechnologyTypes.add(new Technology(TechnologyType.STEEL));
        AllTechnologyTypes.add(new Technology(TechnologyType.ACOUSTICS));
        AllTechnologyTypes.add(new Technology(TechnologyType.BANKING));
        AllTechnologyTypes.add(new Technology(TechnologyType.PRINTING_PRESS));
        AllTechnologyTypes.add(new Technology(TechnologyType.GUNPOWDER));
        AllTechnologyTypes.add(new Technology(TechnologyType.CHEMISTRY));
        AllTechnologyTypes.add(new Technology(TechnologyType.ECONOMICS));
        AllTechnologyTypes.add(new Technology(TechnologyType.METALLURGY));
        AllTechnologyTypes.add(new Technology(TechnologyType.ARCHAEOLOGY));
        AllTechnologyTypes.add(new Technology(TechnologyType.FERTILIZER));
        AllTechnologyTypes.add(new Technology(TechnologyType.MILITARY_SCIENCE));
        AllTechnologyTypes.add(new Technology(TechnologyType.SCIENTIFIC_THEORY));
        AllTechnologyTypes.add(new Technology(TechnologyType.RIFLING));
        AllTechnologyTypes.add(new Technology(TechnologyType.BIOLOGY));
        AllTechnologyTypes.add(new Technology(TechnologyType.STEAM_POWER));
        AllTechnologyTypes.add(new Technology(TechnologyType.DYNAMITE));
        AllTechnologyTypes.add(new Technology(TechnologyType.ELECTRICITY));
        AllTechnologyTypes.add(new Technology(TechnologyType.RAILROAD));
        AllTechnologyTypes.add(new Technology(TechnologyType.REPLACEABLE_PARTS));
        AllTechnologyTypes.add(new Technology(TechnologyType.COMBUSTION));
        AllTechnologyTypes.add(new Technology(TechnologyType.RADIO));
        AllTechnologyTypes.add(new Technology(TechnologyType.TELEGRAPH));

    }
    public int getFood() {
        return food;
    }

    public int getGold() {
        return gold;
    }

    public int getScience() {
        return science;
    }

    public int getHappiness() {
        return happiness;
    }

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

    public boolean isThisGroundVisible(Ground ground) {
        for (Ground clearToSeeGround : clearToSeeGrounds) {
            if (clearToSeeGround.getNumber() == ground.getNumber()) return true;
        }
        return false;
    }

    public void addCityToThisGround(Ground ground){
        ///TODO : check UnMilitary Type is Settler
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

    }
    public boolean doWeHaveThisTechnology(TechnologyType technologyType){
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

}
