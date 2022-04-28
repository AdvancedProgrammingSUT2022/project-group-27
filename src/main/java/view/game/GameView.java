package view.game;


import Enum.Message;
import controller.Game;
import controller.InitializeMap;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameView {
    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    public GameView(ArrayList<User> playerUsers) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
        InitializeMap initializeMap = new InitializeMap(playerUsers);
        initializeMap.run();
    }

    private GlobalVariables globalVariables = new GlobalVariables();

    public void run() {
        String input = Menu.getScanner().nextLine();
        Matcher matcher;
        String regex;

        if (input.matches("^show map$")) {
            ShowMap showMap = new ShowMap(Player.whichPlayerTurnIs());
            showMap.run();
            this.run();
        } else if (input.matches("^next turn$")) {
            Player.nextTurn();
            this.run();
        } else if (input.matches("^move unit .+$")) {
            matcher = controller.getInput("move unit",
                    new ArrayList<String>(List.of("((--origin)|(-o)) (?<origin>\\d+)", "((--destination)|(-d)) (?<destination>\\d+)",
                            "(?<type>(Military)|(UnMilitary))")), input);
            if (matcher != null) moveUnits(matcher);
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        } else if (input.matches("^city menu$")) {
            (new CityView()).cityMenus(Player.whichPlayerTurnIs());
            this.run();
        } else if (input.matches("create city in \\d+")){
            String[] s=input.split(" +");
            createCity(Integer.parseInt(s[3]));
            this.run();
        } else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    private void moveUnits(Matcher matcher) {
        int firstGroundNumber = Integer.parseInt(matcher.group("origin"));
        int secondGroundNumber = Integer.parseInt(matcher.group("destination"));
        String type = matcher.group("type");

        String answer = this.controller.moveUnits(firstGroundNumber, secondGroundNumber, type);
        System.out.println(answer);
        this.run();
    }
    private void createCity(int groundNumber){
        Player player=Player.whichPlayerTurnIs();
        player.addCityToThisGround(Ground.getGroundByNumber(groundNumber));
        System.out.println(player.getCities().size());

    }
}

