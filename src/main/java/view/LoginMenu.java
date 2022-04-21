package view;

import controller.LoginController;
import Enum.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    //singleton pattern
    private static LoginMenu instance = null;
    private LoginController controller;

    private LoginMenu() {
        this.controller = LoginController.getInstance();
    }

    private static void setInstance(LoginMenu instance) {
        LoginMenu.instance = instance;
    }

    public static LoginMenu getInstance() {
        if (LoginMenu.instance == null) LoginMenu.setInstance(new LoginMenu());
        return LoginMenu.instance;
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
            else System.out.println(Message.INVALID_COMMAND);
        } else if (input.matches("user create .+")) {
            matcher = controller.getInput("user create",
                    new ArrayList<String>(List.of("((--username)|(-u)) (?<username>\\S+)", "((--nickname)|(-n)) (?<nickname>.+)", "((--password)|(-p)) (?<password>\\S+)")), input);
            if (matcher != null) createUser(matcher);
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        } else if (input.matches("user login .+")) {
            matcher = controller.getInput("user login",
                    new ArrayList<String>(List.of("((--username)|(-u)) (?<username>\\S+)", "((--password)|(-p)) (?<password>\\S+)")), input);
            if (matcher != null) loginUser(matcher);
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void showMenu() {
        System.out.println("Login Menu");
        this.run();
    }

    @Override
    protected void enterMenu(String newMenuName) {
        switch (newMenuName) {
            case "Main_Menu": {
                System.out.println(Message.LOGIN_FIRST);
                break;
            }
            case "Game_Menu", "Profile_Menu" :
                System.out.println(Message.INVALID_NAVIGATION);
                break;
            case "Login_Menu" :
                System.out.println(Message.CURRENT_MENU);
                break;
            default :
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }

    @Override
    protected void exitMenu() {
        Menu.getScanner().close();
    }

    private void createUser(Matcher matcher) {
        String username = matcher.group("username");
        String nickname = matcher.group("nickname");
        String password = matcher.group("password");

        Message message = controller.createUser(username, password, nickname);
        System.out.println(message);
        this.run();
    }

    private void loginUser(Matcher matcher) {
        String username = matcher.group("username");
        String password = matcher.group("password");

        Message message = controller.loginUser(username, password);
        System.out.println(message);
        if (message == Message.LOGIN_SUCCESSFUL) MainMenu.getInstance().run();

        this.run();
    }
}
