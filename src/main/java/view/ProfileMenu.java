package view;

import controller.Controller;
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
        Matcher matcher;
        String regex;

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else if (input.matches((regex = "^menu enter (?<menuName>\\S+)$"))) {
            matcher = Controller.findMatcherFromString(input, regex);
            if (matcher.find()) enterMenu(matcher.group("menuName"));
            else System.out.println(Message.INVALID_COMMAND);
        } else if (input.matches((regex = "^profile change --nickname (?<nickname>.+)$"))) {
            matcher = Controller.findMatcherFromString(input, regex);
            changeNickname(matcher);
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void enterMenu(String newMenuName) {
        switch (newMenuName) {
            case "Main_Menu":
                return;
            case "Game_Menu", "Login_Menu":
                System.out.println(Message.INVALID_NAVIGATION);
                break;
            case "Profile_Menu"  :
                System.out.println(Message.CURRENT_MENU);
                break;
            default :
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }

    @Override
    protected void showMenu() {
        System.out.println("Profile Menu");
        this.run();
    }

    public void changeNickname(Matcher matcher) {
        if (matcher.find()) {
            String nickname = matcher.group("nickname");
            Message message = ProfileController.getInstance().changeNickname(nickname);
            System.out.println(message);
        } else System.out.println(Message.INVALID_COMMAND);

        this.run();
    }

    public void changePassword(Matcher matcher) {
        //check matcher validation and get String and
        //ProfileController.getInstance().changePassword(..)
        //and sout message
        this.run();
    }
}
