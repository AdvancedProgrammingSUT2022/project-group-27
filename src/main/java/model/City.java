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

    public City(Ground ground, String name){
        this.name = name;
        this.ground=ground;
        this.rangeOfCity.add(ground);
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
    }

    public boolean isThisGroundInThisCityRange(Ground ground){
        for (Ground value : rangeOfCity) {
            if (ground.getNumber() == value.getNumber()) return true;
        }
        return false;
    }

    public boolean isThisGroundNearThisCity(Ground ground) {
        //TODO check if it's near return true
        return false;
    }

    public int howMuchFoodIsProduced() {
        //TODO write the body for it
        return 0;
    }
}
