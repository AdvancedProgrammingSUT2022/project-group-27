package controller;

import model.Request;
import model.Response;
import Enum.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserController {
    private static UserController instance = null;
    private UserController() {}

    public static UserController getInstance() {
        if (instance == null) instance = new UserController();

        return instance;
    }

    private String userLoggedIn = null;

    public String getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(String userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public void atEndWorks() {
        if (userLoggedIn != null) {
            setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            setNullTheCurrentImages();
            userLoggedIn = null;
        }
    }

    private void setNullTheCurrentImages() {
        Request request = new Request();
        request.setHeader("nullCurrentImage");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
    }

    public String getUsername() {
        Request request = new Request();
        request.setHeader("getUsername");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("username");
    }

    public String getScore() {
        Request request = new Request();
        request.setHeader("getScore");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("score");
    }

    public void setLastLoginTime(String time) {
        Request request = new Request();
        request.setHeader("lastTime");
        request.addData("token", userLoggedIn);
        request.addData("time", time);
        Response response = NetworkController.send(request);
    }

    public String loginUser(String username, String password) {
        Request request = new Request();
        request.setHeader("loginUser");
        request.addData("username", username);
        request.addData("password", password);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        if (response.getStatus() == 200) userLoggedIn = (String) response.getData().get("token");
        return (String) response.getData().get("message");
    }

    public String createUser(String username, String password, String nickname) {
        Request request = new Request();
        request.setHeader("createUser");
        request.addData("username", username);
        request.addData("password", password);
        request.addData("nickname", nickname);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        if (response.getStatus() == 200) userLoggedIn = (String) response.getData().get("token");
        return (String) response.getData().get("message");
    }

    public String getProfileImage() {
        Request request = new Request();
        request.setHeader("profileImage");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("image");
    }

    public String getCurrentImage() {
        Request request = new Request();
        request.setHeader("currentImage");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("image");
    }

    public void setProfileImage(String profileImage) {
        Request request = new Request();
        request.setHeader("setProfileImage");
        request.addData("token", userLoggedIn);
        request.addData("image", profileImage);
        Response response = NetworkController.send(request);
    }

    public void setImage(byte[] image) {
        Request request = new Request();
        request.setHeader("setImage");
        request.addData("token", userLoggedIn);
        request.addData("image", image);
        Response response = NetworkController.send(request);
    }
}
