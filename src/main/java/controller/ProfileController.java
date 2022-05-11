package controller;

import Enum.Message;
import view.Menu;

public class ProfileController extends Controller {
    //singleton pattern
    private static ProfileController instance = null;

    private ProfileController() {
    }

    private static void setInstance(ProfileController instance) {
        ProfileController.instance = instance;
    }

    public static ProfileController getInstance() {
        if (ProfileController.instance == null) ProfileController.setInstance(new ProfileController());
        return ProfileController.instance;
    }

    public Message changeNickname(String nickname) {
        if (Menu.getLoggedInUser().changeNickname(nickname)) return Message.NICKNAME_CHANGED;
        return Message.EXIST_NICKNAME;
    }

    public Message changePassword(String newPassword, String oldPassword) {
        if (!Menu.getLoggedInUser().isPasswordCorrect(oldPassword)) return Message.INVALID_PASSWORD;
        if (!Menu.getLoggedInUser().changePassword(newPassword)) return Message.DUPLICSTED_PASSWORD;
        return Message.PASSWORD_CHANGED;
    }
}
