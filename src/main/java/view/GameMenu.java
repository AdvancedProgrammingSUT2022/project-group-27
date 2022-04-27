package view;

import controller.Controller;
import controller.Game;
import controller.GameController;
import Enum.Message;
import model.GlobalVariables;
import model.User;
import view.game.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu extends Menu {
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
            matcher = controller.findMatcherFromString(input, regex);
            if (matcher != null) enterMenu(matcher.group("menuName"));
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        } else if (input.matches((regex = "^play game (--player\\d+ (.+)){2,}$"))) {
            matcher = controller.findMatcherFromString(input, regex);
            if (matcher != null) startGame(matcher, input);
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        } else {
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
            case "Game_Menu":
                System.out.println(Message.CURRENT_MENU);
                break;
            default:
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }

    private void startGame(Matcher matcher, String input) {
        User[] playerUsers = new User[matcher.groupCount()];
        String username;
        int number;
        boolean isValid = true;

        Pattern pattern = Pattern.compile("--player(?<number>\\d+) (?<username>\\S+)");
        Matcher playerMatcher = pattern.matcher(input);

        while (playerMatcher.find() && isValid) {
            username = playerMatcher.group("username");
            number = Integer.parseInt(playerMatcher.group("number"));

            User user = User.findUser(username);
            if (number <= playerUsers.length && playerUsers[number - 1] == null && user != null)
                playerUsers[number - 1] = user;
            else if (number > playerUsers.length || playerUsers[number - 1] != null) {
                System.out.println(Message.INVALID_COMMAND);
                isValid = false;
            } else {
                System.out.println(Message.USER_DOESNT_EXIST);
                isValid = false;
            }
        }

        if (isValid) {
            GameView gameView = new GameView(new ArrayList<User>(Arrays.asList(playerUsers)));
            System.out.println(Message.START_GAME);
            gameView.run();
        }

        this.run();
    }
}
