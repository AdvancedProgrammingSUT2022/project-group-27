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
                player.getCities().get(i).setGold(player.getCities().get(i).getGold()+Integer.parseInt(s[2]));
            }
            this.run();
        }
        else if (input.matches("put improvement in \\d+")){
            String[] s=input.split(" +");
            Player player=Player.whichPlayerTurnIs();
            Ground ground=Ground.getGroundByNumber(Integer.parseInt(s[3]));
            boolean workerExist=false;
            for (int i=0;i<player.getUnits().size();i++){
                if (player.getUnits().get(i).getGround().getNumber()==Integer.parseInt(s[3]) && player.getUnits().get(i) instanceof Worker){
                    workerExist=true;
                }
            }
            if (!workerExist){
                System.out.println("404 not worker");
                this.run();
            }
            ArrayList <ImprovementType> list=ground.listOfImprovementTypes();
            for (int i=0;i<list.size();i++){
                System.out.println(i+1+"- : " + list.get(i));
            }
            String secondInput=Menu.getScanner().nextLine();
            if (secondInput.matches("\\d+")){
                int intInput=Integer.parseInt(secondInput);
                if (intInput>=1 && intInput<=list.size()){
                    intInput--;
                    ground.setImprovementTypeInProgress(list.get(intInput));
                    for (int i=0;i<player.getUnits().size();i++){
                        if (player.getUnits().get(i).getGround().getNumber()==Integer.parseInt(s[3]) && player.getUnits().get(i) instanceof Worker){
                            ((Worker) player.getUnits().get(i)).setWorking(true);
                        }
                    }
                    this.run();
                }
            }
            if (secondInput.equals("end")) this.run();
            System.out.println(Message.INVALID_COMMAND);
            this.run();

        }
        else if (input.matches("technology menu")){
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
    private void createWorker(int groundNumber){
        Player player=Player.whichPlayerTurnIs();
        for (int i=0;i<player.getCities().size();i++) {
            if (player.getCities().get(i).getGround().getNumber()==groundNumber)
                UnitController.buildUnit(player.getCities().get(i),MilitaryType.WORKER);
        }
    }
}

