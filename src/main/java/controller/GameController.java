package controller;

public class GameController extends Controller {
    //singleton pattern
    private static GameController instance = null;
    private GameController() {}

    private static void setInstance(GameController instance) {
        GameController.instance = instance;
    }

    public static GameController getInstance() {
        if (GameController.instance == null) GameController.setInstance(new GameController());
        return GameController.instance;
    }
}
