package controller;

import com.google.gson.Gson;
import model.Request;
import model.Response;
import model.User;
import Enum.Message;

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

    public Response handleChangePassword(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        String newPassword = (String) request.getData().get("newPassword");
        String oldPassword = (String) request.getData().get("oldPassword");

        Message message = changePassword(newPassword, oldPassword, user);
        if (message == Message.PASSWORD_CHANGED) response.setStatus(200);
        else response.setStatus(400);

        response.addData("result", message.toString());
        return response;
    }

    public Response handleChangeNickname(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        String nickname = (String) request.getData().get("nickname");

        Message message = changeNickname(nickname, user);
        if (message == Message.NICKNAME_CHANGED) response.setStatus(200);
        else response.setStatus(400);

        response.addData("result", message.toString());
        return response;
    }

    public Message changeNickname(String nickname, User user) {
        if (user.changeNickname(nickname)) return Message.NICKNAME_CHANGED;
        return Message.EXIST_NICKNAME;
    }

    public Message changePassword(String newPassword, String oldPassword, User user) {
        if (!user.isPasswordCorrect(oldPassword)) return Message.INVALID_PASSWORD;
        if (!user.changePassword(newPassword)) return Message.DUPLICSTED_PASSWORD;
        return Message.PASSWORD_CHANGED;
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
