package model;

import Enum.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;

public class Unit implements Productions{
    protected MilitaryType militaryType;
    protected Ground ground;
    protected String playerName;
    protected Ground destination = null;
    protected double mp = 10;
    protected double hp = 10;
    protected int turnsFortified = 0;
    protected UnitStatus status = UnitStatus.AWAKE;
    protected int turnRemainedToCompleted;
    protected int combatStrength;
    protected int rangedCombatStrength;

    public MilitaryType getMilitaryType() {
        return militaryType;
    }

    public UnitStatus getStatus() {
        Request request = new Request();
        request.setHeader("getStatus");
        //request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", militaryType);
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        status = new Gson().fromJson((String) response.getData().get("status"), new TypeToken<UnitStatus>(){}.getType());
        return status;
    }

    public Ground getGround() {
        return ground;
    }

    public boolean isMilitary() {
        return !name().equals(MilitaryType.SETTLER.name()) && !name().equals(MilitaryType.WORKER.name());
    }

    @Override
    public String name() {
        return this.getMilitaryType().name();
    }

    @Override
    public String toString() {
        return "Unit type: " + this.militaryType + " status: " + this.status + " ground number: " + this.ground.getNumber();
    }

    @Override
    public int getTurnRemainedToComplete() {
        Request request = new Request();
        request.setHeader("getTurnRemainedToComplete");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", militaryType);
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        turnRemainedToCompleted = (int) Math.floor((Double) response.getData().get("timeRemain"));
        return turnRemainedToCompleted;
    }

    public Player getPlayer() {
        return Player.getPlayerByUser(playerName);
    }

    public double getHp() {
        Request request = new Request();
        request.setHeader("getHp");
        //request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", militaryType);
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        hp = (Double) response.getData().get("hp");
        return hp;
    }

    public double getMp() {
        Request request = new Request();
        request.setHeader("getMp");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", militaryType);
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        mp = (Double) response.getData().get("mp");
        return mp;
    }

    public void buildCity(String name) {
        if (militaryType.name().equals(MilitaryType.SETTLER.name())) {
            Request request = new Request();
            request.setHeader("buildCity");
            request.addData("token", UserController.getInstance().getUserLoggedIn());
            request.addData("name", name);
            request.addData("groundNumber", ground.getNumber());
            Response response = NetworkController.send(request);
            City city = new City(name, ground);
            Player.getPlayerByUser(UserController.getInstance().getUsername()).addCity(city);
        }
    }

    public void setWorking(boolean working) {
        if (militaryType.name().equals(MilitaryType.WORKER.name())) {
            Request request = new Request();
            request.setHeader("setWorking");
            request.addData("token", UserController.getInstance().getUserLoggedIn());
            request.addData("working", working);
            request.addData("groundNumber", ground.getNumber());
            Response response = NetworkController.send(request);
        }
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
        Request request = new Request();
        request.setHeader("setStatus");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", militaryType);
        request.addData("groundNumber", ground.getNumber());
        request.addData("status", new Gson().toJson(status));
        Response response = NetworkController.send(request);
    }
}
