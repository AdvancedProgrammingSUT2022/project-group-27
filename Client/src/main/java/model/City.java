package model;

import Enum.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class City {
    private String name;
    private double savedFood;
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


    public City(String name, Ground ground) {
        this.name = name;
        this.ground = ground;
    }

    public String getName() {
        return name;
    }

    public double getSavedFood() {
        Request request = new Request();
        request.setHeader("getSavedFood");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        savedFood = (Double) response.getData().get("save");
        return savedFood;
    }

    public ArrayList<Ground> getRangeOfCity() {
        Request request = new Request();
        request.setHeader("getRangeOfCity");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        ArrayList<Integer> list = new Gson().fromJson((String) response.getData().get("list"),  new TypeToken<ArrayList<Integer>>(){}.getType());
        rangeOfCity.clear();
        for (Integer integer: list) {
            rangeOfCity.add(Ground.getGroundByNumber(integer));
        }
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

    public static City findCityByGround(Ground ground) {
        ArrayList<Player> list = Player.getAllPlayers();
        for (Player player: list) {
            ArrayList<City> cities = player.getCities();
            for (City city : cities) {
                if (city.groundNumber() == ground.getNumber()) return city;
            }
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
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) science = (Double) response.getData().get("science");
        return science;
    }

    public Double getFoodPerTurn() {
        Double food = 0.0;
        Request request = new Request();
        request.setHeader("getCityFoodPerTurn");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) food = (Double) response.getData().get("food");
        return food;
    }

    public Double getGold() {
        Double gold = 0.0;
        Request request = new Request();
        request.setHeader("getCityGold");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) gold = (Double) response.getData().get("gold");
        return gold;
    }

    public int getCityStrength() {
        Double strength = 0.0;
        Request request = new Request();
        request.setHeader("getCityStrength");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) strength = (Double) response.getData().get("strength");
        return (int) Math.ceil(strength);
    }

    public ArrayList<Citizen> getListOfCitizens() {
        ArrayList<Citizen> listOfCitizens = new ArrayList<>();
        Request request = new Request();
        request.setHeader("getListOfCitizens");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) listOfCitizens = new Gson().fromJson((String) response.getData().get("listOfCitizens"),  new TypeToken<ArrayList<Citizen>>(){}.getType());
        return listOfCitizens;
    }

    public int getRemainedTurnsToBuild() {
        Double remainedTurns = 0.0;
        Request request = new Request();
        request.setHeader("getRemainedTurnsToBuild");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);

        if (response != null) remainedTurns = (Double) response.getData().get("remainedTurnsToBuild");
        return (int) Math.ceil(remainedTurns);
    }

    public double getProduction() {
        Request request = new Request();
        request.setHeader("getProduction");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return (Double) response.getData().get("production");
    }

    public Player getOwner() {
        Request request = new Request();
        request.setHeader("getOwner");
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return Player.getPlayerByUser((String) response.getData().get("user"));
    }

    public MilitaryType getBuildingUnit() {
        Request request = new Request();
        request.setHeader("getBuildingUnit");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("unit"), new TypeToken<MilitaryType>(){}.getType());
    }

    public ArrayList<Citizen> withoutWorkCitizens() {
        Request request = new Request();
        request.setHeader("withoutWorkCitizens");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Citizen>>(){}.getType());
    }

    public void combat(Ground ground) {
        Request request = new Request();
        request.setHeader("combat");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", this.ground.getNumber());
        request.addData("groundOther", ground.getNumber());
        Response response = NetworkController.send(request);
    }

    public ArrayList<BuildingsType> getBuildings() {
        Request request = new Request();
        request.setHeader("getBuildings");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<BuildingsType>>(){}.getType());
    }

    public void setPuppet(boolean b, Player player) {
        Request request = new Request();
        request.setHeader("setPuppet");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("player", player.getUser());
        request.addData("groundNumber", ground.getNumber());
        request.addData("isPuppet", b);
        Response response = NetworkController.send(request);
    }

    public void setPlayer(Player player) {
        Request request = new Request();
        request.setHeader("setPlayer");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("player", player.getUser());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
    }
}
