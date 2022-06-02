package controller;

import Enum.Message;
import model.User;
import view.Menu;

public class LoginController extends Controller {
    //singleton pattern
    private static LoginController instance = null;

    private LoginController() {
    }

    private static void setInstance(LoginController instance) {
        LoginController.instance = instance;
    }

    public static LoginController getInstance() {
        if (LoginController.instance == null) LoginController.setInstance(new LoginController());
        return LoginController.instance;
    }

    public Message createUser(String username, String password, String nickname) {
        Message message;
        if (User.isUsernameExist(username)) message = Message.EXIST_USERNAME;
        else if (User.isNicknameExist(nickname)) message = Message.EXIST_NICKNAME;
        else {
            User user = new User(username, password, nickname);
            Menu.setLoggedInUser(user);
            user.setLastLoginTime("online");
            message = Message.CREATE_USER_SUCCESSFUL;
        }

        return message;
    }

    public Message loginUser(String username, String password) {
        Message message;
        User user = User.findUser(username, password);
        if (user == null) message = Message.USER_IS_INVALID;
        else {
            Menu.setLoggedInUser(user);
            user.setLastLoginTime("online");
            message = Message.LOGIN_SUCCESSFUL;
        }

        return message;
    }
}
