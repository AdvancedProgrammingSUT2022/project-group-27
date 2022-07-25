package model;

import Enum.*;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class City {
    private String name;
    private int savedFood;
    private MilitaryType buildingUnit = null;
    private Ground ground;
    private Productions construction; //in the future, we should write a class for constructions and get type here
    private boolean isPuppet = false;
    private double hp = 20;
    private boolean isMainCapital;

    private final ArrayList<Unit> listOfUnitsInCity = new ArrayList<>();
//    private final ArrayList<Citizen> listOfCitizens = new ArrayList<>();
    private final ArrayList<Ground> rangeOfCity = new ArrayList<>();
//    private final ArrayList<Building> buildings = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSavedFood() {
        return savedFood;
    }

    public void setSavedFood(int savedFood) {
        this.savedFood = savedFood;
    }


    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public void setConstruction(Productions construction) {
        this.construction = construction;
    }

    public boolean isPuppet() {
        return isPuppet;
    }

    public void setPuppet(boolean puppet) {
        isPuppet = puppet;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public boolean isMainCapital() {
        return isMainCapital;
    }

    public void setMainCapital(boolean mainCapital) {
        isMainCapital = mainCapital;
    }

    public ArrayList<Unit> getListOfUnitsInCity() {
        return listOfUnitsInCity;
    }

    public ArrayList<Ground> getRangeOfCity() {
        return rangeOfCity;
    }

    public int groundNumber() {
        return ground.getNumber();
    }

    public static City findCityByGround(Ground ground, Player player) {
        ArrayList<City> cities = player.getCities();
        for (City city: cities) {
            if (city.groundNumber() == ground.getNumber()) return city;
        }

        return null;
    }

    public Ground getGround() {
        return ground;
    }

    public double getHp() {
        Request request = new Request();
        request.setHeader("getHpCity");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        hp = (Double) response.getData().get("hp");
        return hp;
    }

    public String getConstruction() {
        Request request = new Request();
        request.setHeader("getConstruction");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return (String) response.getData().get("construction");
    }

    public Double getScience() {
        Double science = 0.0;
        Request request = new Request();
        request.setHeader("getCityScience");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) science = (Double) response.getData().get("science");
        return science;
    }

    public Double getFoodPerTurn() {
        Double food = 0.0;
        Request request = new Request();
        request.setHeader("getCityFoodPerTurn");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) food = (Double) response.getData().get("food");
        return food;
    }

    public Double getGold() {
        Double gold = 0.0;
        Request request = new Request();
        request.setHeader("getCityGold");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) gold = (Double) response.getData().get("gold");
        return gold;
    }

    public int getCityStrength() {
        Double strength = 0.0;
        Request request = new Request();
        request.setHeader("getCityStrength");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) strength = (Double) response.getData().get("strength");
        return (int) Math.ceil(strength);
    }

    public ArrayList<Citizen> getListOfCitizens() {
        ArrayList<Citizen> listOfCitizens = new ArrayList<>();
        Request request = new Request();
        request.setHeader("getListOfCitizens");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) listOfCitizens = (ArrayList<Citizen>) response.getData().get("listOfCitizens");
        return listOfCitizens;
    }

    public int getRemainedTurnsToBuild() {
        Double remainedTurns = 0.0;
        Request request = new Request();
        request.setHeader("getRemainedTurnsToBuild");
        request.addData("city", this);
        Response response = NetworkController.send(request);

        if (response != null) remainedTurns = (Double) response.getData().get("remainedTurnsToBuild");
        return (int) Math.ceil(remainedTurns);
    }

}
