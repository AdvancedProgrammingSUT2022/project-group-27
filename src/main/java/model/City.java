package model;

import java.util.ArrayList;

public class City {
    private Player player;
    private String name;
    private int power;
    private int savedFood;
    private int production;
    private int science;
    private int gold;
    private int income;
    private Ground ground;


    private final ArrayList<Citizen> listOfCitizens = new ArrayList<>();
    private ArrayList<Ground> rangeOfCity=new ArrayList<>();

    public City(Ground ground, String name, Player player){
        this.name = name;
        this.ground = ground;
        this.rangeOfCity.add(ground);
        for (Ground rangeGround : ground.getAdjacentGrounds()){
            this.rangeOfCity.add(rangeGround);
        }
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
    public void addGroundToRangeGround(Ground ground){
        //TODO : fix amount
        if (player.getGold()<10) return ;
        if (ground.isInRangeOfCity()) return ;
        this.rangeOfCity.add(ground);
    }
}
