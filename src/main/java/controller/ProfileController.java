package controller;

import Enum.Message;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.User;
import view.Menu;
import Enum.ProfileImages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

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

    public void settingProfile(Rectangle profile, User user) {
        String profileModel;

        if (user.getProfileImage() == null) profileModel = randomImage().toString();
        else profileModel = user.getProfileImage();

        Image image;
        if (user.getCurrentImage() == null)
            image = new Image(ProfileController.class.getResource(File.separator + "profile" + File.separator + profileModel).toExternalForm());
        else {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(user.getCurrentImage());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = new Image(fileInputStream);
        }
        ImagePattern profileImage = new ImagePattern(image);
        profile.setFill(profileImage);
        profile.setHeight(60);
        profile.setWidth(60);
        user.setProfileImage(profileModel);
    }

    public void settingAllImages(Rectangle[] images) {
        for (int i = 0; i < ProfileImages.values().length; i++) {
            images[i] = new Rectangle();
            Image image = new Image(ProfileController.class.getResource(File.separator + "profile" + File.separator + ProfileImages.values()[i].toString()).toExternalForm());
            ImagePattern profileImage = new ImagePattern(image);
            images[i].setFill(profileImage);
            images[i].setHeight(60);
            images[i].setWidth(60);
        }
    }

    private ProfileImages randomImage() {
        int max = ProfileImages.values().length;
        Random random = new Random();
        int randomNumber = random.nextInt(max);
        return ProfileImages.values()[randomNumber];
    }
}
