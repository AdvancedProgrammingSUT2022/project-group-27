package model;

import controller.Game;
import controller.InitializeMap;

import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.max;

public class GameModel {
    private static GameModel instance = null;

    public static GameModel getInstance() {
        if (instance == null) instance = new GameModel();
        return instance;
    }

    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    public void setting(ArrayList<String> users,Double seed) {
        ArrayList<User> playerUsers = new ArrayList<>();
        for (String user: users) {
            playerUsers.add(User.findUser(user));
        }

        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
        System.out.println("initialize");
        InitializeMap initializeMap = new InitializeMap(playerUsers, (int) Math.floor(seed));
     //   if (seed!=6) {
            initializeMap.run();
       // }
    }
}
