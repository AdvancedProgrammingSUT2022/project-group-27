package view.game;


import Enum.Message;
import controller.Game;
import controller.InitializeMap;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import Enum.TechnologyType;
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
        } else if (input.matches("technology menu")){
            Player player=Player.whichPlayerTurnIs();
            System.out.println("lists of technologies:");
            for (int i=0;i<player.getTechnologyType().size();i++){
                System.out.println(player.getTechnologyType().get(i));
            }
            System.out.println("list of technologies that can be obtained:");
            for (int i=0;i<player.technologiesThatCanBeObtained().size();i++){
                System.out.println(i+1 + " - type: "+player.technologiesThatCanBeObtained().get(i).getTechnologyType()
                        +"  time remain: "+player.technologiesThatCanBeObtained().get(i).getTimeRemain());
            }
            String secondInput=Menu.getScanner().nextLine();
            if (secondInput.matches("\\d+")){
                int intInput=Integer.parseInt(secondInput);
                ArrayList <Technology> technologies=player.technologiesThatCanBeObtained();
                if (intInput>=1 && intInput<=technologies.size()){
                    intInput--;
                    player.setUnderConstructionTechnology(technologies.get(intInput).getTechnologyType());
                    this.run();
                }
            }
            if (secondInput.equals("end")) this.run();
            System.out.println(Message.INVALID_COMMAND);
            this.run();

        }else {
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

