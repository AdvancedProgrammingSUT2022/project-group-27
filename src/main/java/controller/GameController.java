package controller;

import Enum.Message;
import model.User;

import java.util.ArrayList;

public class GameController extends Controller {
    //singleton pattern
    private static GameController instance = null;

    private GameController() {
    }

    private static void setInstance(GameController instance) {
        GameController.instance = instance;
    }

    public static GameController getInstance() {
        if (GameController.instance == null) GameController.setInstance(new GameController());
        return GameController.instance;
    }

    public Message startGame(ArrayList<String> usernames) {
        for (String username : usernames) {
            if (!User.isUsernameExist(username)) return Message.USERNAME_NOT_EXIST;
        }

        return Message.START_GAME;
    }
}
