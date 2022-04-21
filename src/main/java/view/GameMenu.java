package view;

import controller.Controller;
import controller.GameController;
import Enum.Message;
import view.game.GameView;

import java.util.regex.Matcher;

public class GameMenu extends Menu{
    //singleton pattern
    private static GameMenu instance = null;
    private GameController controller;

    private GameMenu() {
        this.controller = GameController.getInstance();
    }

    private static void setInstance(GameMenu instance) {
        GameMenu.instance = instance;
    }

    public static GameMenu getInstance() {
        if (GameMenu.instance == null) GameMenu.setInstance(new GameMenu());
        return GameMenu.instance;
    }

    @Override
    public void run() {
        String input = getScanner().nextLine();
        Matcher matcher;
        String regex;

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else if (input.matches((regex = "^menu enter (?<menuName>\\S+)$"))) {
            matcher = Controller.findMatcherFromString(input, regex);
            if (matcher != null) enterMenu(matcher.group("menuName"));
            else System.out.println(Message.INVALID_COMMAND);
        } else if (input.matches("^start game$")) GameView.getInstance().run(); //ToDo change it to correct format
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void showMenu() {
        System.out.println("Game Menu");
        this.run();
    }

    @Override
    protected void enterMenu(String newMenuName) {
        switch (newMenuName) {
            case "Main_Menu":
                return;
            case "Profile_Menu", "Login_Menu":
                System.out.println(Message.INVALID_NAVIGATION);
                break;
            case "Game_Menu" :
                System.out.println(Message.CURRENT_MENU);
                break;
            default :
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }
}
