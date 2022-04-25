package controller;

import view.Menu;

public class MainController extends Controller {
    //singleton pattern
    private static MainController instance = null;

    private MainController() {
    }

    private static void setInstance(MainController instance) {
        MainController.instance = instance;
    }

    public static MainController getInstance() {
        if (MainController.instance == null) MainController.setInstance(new MainController());
        return MainController.instance;
    }
}
