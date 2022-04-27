package model;

import java.util.ArrayList;

public class City {
    private Player player;
    private String name;
    private int cityStrength;
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

    public int getCityStrength() {
        return cityStrength;
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

    public void addGroundToRangeOfCity(Ground ground){
        this.rangeOfCity.add(ground);
    }

    public boolean isThisGroundInThisCityRange(Ground ground){
        for (Ground value : rangeOfCity) {
            if (ground.getNumber() == value.getNumber()) return true;
        }
        return false;
    }
}
