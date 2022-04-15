package view;

import controller.LoginController;
import controller.MainController;

public class MainMenu extends Menu {
    //singleton pattern
    private static MainMenu instance = null;
    private MainController controller;

    private MainMenu() {
        this.controller = MainController.getInstance();
    }

    private static void setInstance(MainMenu instance) {
        MainMenu.instance = instance;
    }

    public static MainMenu getInstance() {
        if (MainMenu.instance == null) MainMenu.setInstance(new MainMenu());
        return MainMenu.instance;
    }

    @Override
    public void run() {

    }

    @Override
    protected void showMenu() {

    }

    @Override
    protected void enterMenu(String newMenuName) {

    }

    private void LoggedOutUser() {
        Menu.setLoggedInUser(null);
    }
}
