package controller;

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
}
