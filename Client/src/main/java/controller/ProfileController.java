package controller;

import Enum.Message;
import Enum.ProfileImages;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Request;
import model.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class ProfileController {
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

    public String changeNickname(String nickname) {
        Request request = new Request();
        request.setHeader("changeNickname");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("nickname", nickname);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        return (String) response.getData().get("result");
    }

    public String changePassword(String newPassword, String oldPassword) {
        Request request = new Request();
        request.setHeader("changePassword");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("newPassword", newPassword);
        request.addData("oldPassword", oldPassword);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        return (String) response.getData().get("result");
    }

    public ImagePattern getImage(String user) {
        String profileModel = UserController.getInstance().getProfileImage(user);

        if (profileModel == null) profileModel = randomImage().toString();


        Image image;
        String currentImage = UserController.getInstance().getCurrentImage(user);
        if (currentImage == null) {
            image = new Image(ProfileController.class.getResource("/profile/" + profileModel).toExternalForm());
        }
        else {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(currentImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = new Image(fileInputStream);
        }

        return new ImagePattern(image);
    }

    public void settingProfile(Rectangle profile, String user) {
        String profileModel = UserController.getInstance().getProfileImage(user);
        File file = null;

        if (profileModel == null) profileModel = randomImage().toString();

        Image image;
        String currentImage = UserController.getInstance().getCurrentImage(user);
        if (currentImage == null) {
            image = new Image(ProfileController.class.getResource("/profile/" + profileModel).toExternalForm());
            file = new File("./src/main/resources/profile/" + profileModel);
        }
        else {
            FileInputStream fileInputStream = null;
            try {
                file = new File(currentImage);
                fileInputStream = new FileInputStream(currentImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = new Image(fileInputStream);
        }
        ImagePattern profileImage = new ImagePattern(image);
        profile.setFill(profileImage);
        profile.setHeight(60);
        profile.setWidth(60);
        UserController.getInstance().setProfileImage(profileModel);


        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        UserController.getInstance().setImage(bFile, user);
    }

    public void settingAllImages(Rectangle[] images) {
        for (int i = 0; i < ProfileImages.values().length; i++) {
            images[i] = new Rectangle();
            Image image = new Image(ProfileController.class.getResource("/profile/" + ProfileImages.values()[i].toString()).toExternalForm());
            ImagePattern profileImage = new ImagePattern(image);
            images[i].setFill(profileImage);
            images[i].setHeight(60);
            images[i].setWidth(60);
        }
    }

    public ProfileImages randomImage() {
        int max = ProfileImages.values().length;
        Random random = new Random();
        int randomNumber = random.nextInt(max);
        return ProfileImages.values()[randomNumber];
    }
}
