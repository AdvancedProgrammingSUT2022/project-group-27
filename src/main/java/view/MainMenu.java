package view;

import controller.MainController;
import Enum.Message;

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
        String input = getScanner().nextLine();

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void showMenu() {
        System.out.println("Main Menu");
        this.run();
    }

    @Override
    protected void enterMenu(String newMenuName) {
        this.run();
    }

    private void LoggedOutUser() {
        Menu.setLoggedInUser(null);
    }
}
