package view.game;


import Enum.Message;
import controller.Game;
import controller.InitializeMap;
import controller.UnitController;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import Enum.MilitaryType;
import Enum.TechnologyType;
import static java.lang.Math.max;
import static java.lang.Math.min;
import Enum.ImprovementType;
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
        } else if (input.matches("^next turn \\d+")) {
            String[] s=input.split(" +");
            for (int i=0;i<Integer.parseInt(s[2]);i++) Player.nextTurn();
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
        } else if (input.matches("^BuildCity menu$")) {
            (new BuildCityMenu()).buildCityMenu(Player.whichPlayerTurnIs());
            this.run();
        }else if (input.matches("create city in \\d+")){
            String[] s=input.split(" +");
            createCity(Integer.parseInt(s[3]));
            this.run();
        } else if (input.matches("create worker in \\d+")){
            String[] s=input.split(" +");
            createWorker(Integer.parseInt(s[3]));
            this.run();
        }
        else if (input.matches("increase gold \\d+")){
            String[] s=input.split(" +");
            Player player=Player.whichPlayerTurnIs();
            for (int i=0;i<player.getCities().size();i++){
                player.getCities().get(i).increaseGold(Integer.parseInt(s[2]));
            }
            this.run();
        }
        else if (input.matches((regex = "^put improvement in (?<groundNumber>\\d+)$"))){
            matcher = controller.findMatcherFromString(input, regex);
            this.improvementMenu(matcher);
        }
        else if (input.matches("^technology menu$")){
            Player player=Player.whichPlayerTurnIs();
            new TechnologyMenu(player);
            this.run();
        }else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    private void improvementMenu(Matcher matcher) {
        Player player=Player.whichPlayerTurnIs();
        int groundNumber = Integer.parseInt(matcher.group("groundNumber"));

        new ImprovementMenu(player, groundNumber);
        this.run();
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
    private void createWorker(int groundNumber){
        Player player=Player.whichPlayerTurnIs();
        for (int i=0;i<player.getCities().size();i++) {
            if (player.getCities().get(i).getGround().getNumber()==groundNumber)
                UnitController.buildUnit(player.getCities().get(i),MilitaryType.WORKER);
        }
    }
}

