package controller;

import model.Request;
import model.Response;

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

    private void setLastLoginTime(String time) {
        Request request = new Request();
        request.setHeader("lastTime");
        request.addData("token", userLoggedIn);
        request.addData("time", time);
        Response response = NetworkController.send(request);
    }
}
