package controller;

import model.Request;
import model.Response;

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
}
