package model;

import java.util.ArrayList;
import Enum.MilitaryType;

public class City {
    private Player player;
    private String name;
    private int power;
    private int savedFood;
    private int production;
    private int science;
    private int gold;
    private int income;
    private int remainedTurnsToBuild;
    private MilitaryType buildingUnit;
    private Ground ground;


    private final ArrayList<Citizen> listOfCitizens = new ArrayList<>();
    private ArrayList<Ground> rangeOfCity=new ArrayList<>();

    public City(Ground ground, String name, Player player) {
        this.remainedTurnsToBuild = 0;
        this.name = name;
        this.ground = ground;
        this.rangeOfCity.add(ground);
        for (Ground rangeGround : ground.getAdjacentGrounds()) {
            this.rangeOfCity.add(rangeGround);
        }
        this.player=player;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }


    public Ground getGround() {
        return ground;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Citizen> getListOfCitizens() {
        return listOfCitizens;
    }

    public ArrayList<Ground> getRangeOfCity() {
        return rangeOfCity;
    }

    public int getSavedFood() {
        return savedFood;
    }

    public int getProduction() {
        return production;
    }

    public int getScience() {
        return science;
    }

    public int getGold() {
        return gold;
    }

    public int getIncome() {
        return income;
    }

    public MilitaryType getBuildingUnit() {
        return buildingUnit;
    }

    public void setBuildingUnit(MilitaryType buildingUnit) {
        this.buildingUnit = buildingUnit;
    }

    public int getRemainedTurnsToBuild() {
        return remainedTurnsToBuild;
    }

    public void setRemainedTurnsToBuild(int remainedTurnsToBuild) {
        this.remainedTurnsToBuild = remainedTurnsToBuild;
    }

    public void addGroundToRangeOfCity(Ground ground){
        this.rangeOfCity.add(ground);
        ground.setOwner(this.player);
    }

    public boolean isThisGroundInThisCityRange(Ground ground){
        for (Ground value : rangeOfCity) {
            if (ground.getNumber() == value.getNumber()) return true;
        }
        return false;
    }

    public ArrayList<Citizen> withoutWorkCitizens() {
        ArrayList<Citizen> listOfWithoutWork = new ArrayList<>();
        for (Citizen citizen: this.getListOfCitizens()) {
            if (!citizen.isHaveWork()) listOfWithoutWork.add(citizen);
        }

        return listOfWithoutWork;
    }

    public Citizen isAnyoneWorkOnGround(Ground ground) {
        for (Citizen citizen : this.listOfCitizens) {
            if (citizen.getGround().getNumber() == ground.getNumber()) return citizen;
        }

        return null;
    }

    public boolean isThisGroundNearThisCity(Ground ground) {
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            if (!Ground.AreTheseTwoGroundAdjacent(ground,Ground.getGroundByNumber(i))) continue;
            for (int j=1;j<=GlobalVariables.numberOfTiles;j++){
                if (!Ground.AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(j),Ground.getGroundByNumber(i))) continue;
                if (Ground.getGroundByNumber(j).isInRangeOfCity()) return true;
            }
        }
        return false;
    }

    public int howMuchFoodIsProduced() {
        //TODO write the body for it
        return 0;
    }
    public void updateCityGoldAndFoodAndOtherThings(){
        for (Ground ground : getRangeOfCity()){
            this.savedFood+=ground.getGroundType().getFood()+ground.getFeatureType().getFood();
            this.gold+=ground.getGroundType().getGold()+ground.getFeatureType().getGold();
            //TODO : other things
            for (int j=0;j<ground.getBonusResource().size();j++){
                this.savedFood+=ground.getBonusResource().get(j).getFood();
                this.gold+=ground.getBonusResource().get(j).getGold();
                //TODO : other things
            }
        }
    }
}
