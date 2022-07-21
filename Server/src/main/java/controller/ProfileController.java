package controller;

import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    public void settingProfile(User user) {
        String profileModel = user.getProfileImage();
        File file;

        String currentImage = user.getCurrentImage();
        if (currentImage == null) {
            file = new File("./src/main/resources/profile/" + profileModel);
        }
        else {
            file = new File(currentImage);
        }


        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        user.setImage(bFile);
    }
}
