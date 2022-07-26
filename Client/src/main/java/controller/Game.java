package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Ground;
import model.Request;
import model.Response;
import model.Unit;
import Enum.Message;

public class Game {
    //singleton pattern
    private static Game instance = null;

    private Game() {

    }

    private static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public static Game getInstance() {
        if (Game.instance == null) Game.setInstance(new Game());
        return Game.instance;
    }

    public void moveUnits(int firstGroundNumber, int secondGroundNumber, String type) {
        Request request = new Request();
        request.setHeader("moveUnits");
        request.addData("firstGroundNumber", firstGroundNumber);
        request.addData("secondGroundNumber", secondGroundNumber);
        request.addData("type", type);
        Response response = NetworkController.send(request);
    }

    public void clearLand(Ground ground) {
        Request request = new Request();
        request.setHeader("clearLand");
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
    }

    public void buildRoad(Ground ground) {
        Request request = new Request();
        request.setHeader("buildRoad");
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
    }

    public void buildRailway(Ground ground) {
        Request request = new Request();
        request.setHeader("buildRailway");
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
    }

    public void freePlundering(Unit worker) {
        Request request = new Request();
        request.setHeader("freePlundering");
        request.addData("groundNumber", worker.getGround().getNumber());
        request.addData("token", worker.getPlayer().getUser());
        Response response = NetworkController.send(request);
    }

    public boolean isFinished() {
        Request request = new Request();
        request.setHeader("isFinished");
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public Message established(Unit unit) {
        Request request = new Request();
        request.setHeader("established");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("message"), new TypeToken<Message>(){}.getType());
    }

    public Message rangedFight(Unit unit, int number) {
        Request request = new Request();
        request.setHeader("rangedFight");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        request.addData("number", number);
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("message"), new TypeToken<Message>(){}.getType());
    }

    public Message meleeFight(Unit unit, int number) {
        Request request = new Request();
        request.setHeader("meleeFight");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        request.addData("number", number);
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("message"), new TypeToken<Message>(){}.getType());
    }

    public Message readyToRangedFight(Unit unit) {
        Request request = new Request();
        request.setHeader("readyToRangedFight");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("message"), new TypeToken<Message>(){}.getType());
    }

    public Message plundering(Unit unit) {
        Request request = new Request();
        request.setHeader("plundering");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("message"), new TypeToken<Message>(){}.getType());
    }

    public String removeOneOrder(Unit unit) {
        Request request = new Request();
        request.setHeader("removeOneOrder");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        Response response = NetworkController.send(request);
        return (String) response.getData().get("message");
    }

    public void deleteUnit(Unit unit) {
        Request request = new Request();
        request.setHeader("deleteUnit");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("militaryType", unit.getMilitaryType());
        request.addData("groundNumber", unit.getGround().getNumber());
        Response response = NetworkController.send(request);
    }
}
