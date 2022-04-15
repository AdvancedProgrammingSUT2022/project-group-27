package view;

import controller.LoginController;
import model.User;

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

    }

    @Override
    protected void showMenu() {

    }

    @Override
    protected void enterMenu(String newMenuName) {

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
