package controller;

import model.Ground;
import model.Request;
import model.Response;
import model.Unit;

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
        return ((String) response.getData().get("answer")).equals("true");
    }
}
