package view;

import controller.ProfileController;
import Enum.Message;

import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    //singleton pattern
    private static ProfileMenu instance = null;
    private ProfileController controller;

    private ProfileMenu() {
        this.controller = ProfileController.getInstance();
    }

    private static void setInstance(ProfileMenu instance) {
        ProfileMenu.instance = instance;
    }

    public static ProfileMenu getInstance() {
        if (ProfileMenu.instance == null) ProfileMenu.setInstance(new ProfileMenu());
        return ProfileMenu.instance;
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
    protected void enterMenu(String newMenuName) {
        this.run();
    }

    @Override
    protected void showMenu() {
        System.out.println("Profile Menu");
        this.run();
    }

    public void changeNickname(Matcher matcher) {
        //check matcher validation and get String and
        //ProfileController.getInstance().changeNickname(...)
        //and sout message
        this.run();
    }

    public void changePassword(Matcher matcher) {
        //check matcher validation and get String and
        //ProfileController.getInstance().changePassword(..)
        //and sout message
        this.run();
    }
}
