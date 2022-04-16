package controller;

import Enum.Message;

public class LoginController extends Controller{
    //singleton pattern
    private static LoginController instance = null;
    private LoginController() {}

    private static void setInstance(LoginController instance) {
        LoginController.instance = instance;
    }

    public static LoginController getInstance() {
        if (LoginController.instance == null) LoginController.setInstance(new LoginController());
        return LoginController.instance;
    }

    public Message createUser(String username, String password, String nickname) {
        Message message = Message.INVALID_COMMAND;
        //TODO...write function
        return message;
    }

    public Message loginUser(String username, String password) {
        Message message = Message.INVALID_COMMAND;
        //TODO...write function
        return message;
    }
}
