package view;

import controller.Controller;
import controller.LoginController;
import model.User;
import Enum.Message;

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
            matcher = Controller.findMatcherFromString(input, regex);
            if (matcher.find()) enterMenu(matcher.group("menuName"));
            else System.out.println(Message.INVALID_COMMAND);
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
                System.out.println(Message.ENTER_MENU);
                MainMenu.getInstance().run();
                //System.out.println(Message.LOGIN_FIRST);
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
        //check matcher validation and get String and
        // this.controller.createUser();
        this.run();
    }

    private void loginUser(Matcher matcher) {
        //check matcher validation and get String and
        // this.controller.loginUser();
        // MainMenu.getInstance().run()
        this.run();
    }
}
