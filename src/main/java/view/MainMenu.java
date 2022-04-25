package view;

import controller.Controller;
import controller.MainController;
import Enum.Message;

import java.util.regex.Matcher;

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
        Matcher matcher;
        String regex;

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else if (input.matches((regex = "^menu enter (?<menuName>\\S+)$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            if (matcher != null) enterMenu(matcher.group("menuName"));
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        } else if (input.matches("^user logout$")) loggedOutUser();
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
        switch (newMenuName) {
            case "Game_Menu": {
                System.out.println(Message.ENTER_MENU);
                GameMenu.getInstance().run();
                break;
            }
            case "Profile_Menu": {
                System.out.println(Message.ENTER_MENU);
                ProfileMenu.getInstance().run();
                break;
            }
            case "Login_Menu":
                return;
            case "Main_Menu":
                System.out.println(Message.CURRENT_MENU);
                break;
            default:
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }

    private void loggedOutUser() {
        Menu.setLoggedInUser(null);
        System.out.println(Message.LOGOUT);
    }
}
