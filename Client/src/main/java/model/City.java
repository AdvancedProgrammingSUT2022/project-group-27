package model;

import Enum.*;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;

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
}
