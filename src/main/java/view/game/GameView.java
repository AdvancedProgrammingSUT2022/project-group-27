package view.game;


import Enum.Message;
import controller.Game;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class GameView {
    //singleton pattern
    private static GameView instance = null;
    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    private GameView(ArrayList<User> playerUsers) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
        Random rand=new Random();
        System.out.println(playerUsers.size());
        for (int i=0;i<playerUsers.size();i++){
            int idStartGround=rand.nextInt(GlobalVariables.numberOfTiles)+1;
            while(!Ground.getGroundByNumber(idStartGround).isFreeOfUnit()){
                idStartGround=rand.nextInt(GlobalVariables.numberOfTiles)+1;
            }
            Ground ground=Ground.getGroundByNumber(idStartGround);
            Player player=new Player(playerUsers.get(i));
            UnMilitaryUnit unMilitaryUnit = new UnMilitaryUnit(ground,player);
            MilitaryUnit militaryUnit=new MilitaryUnit(ground,player);
            player.units.add(militaryUnit);
            player.units.add(unMilitaryUnit);
        }
    }

    private static void setInstance(GameView instance) {
        GameView.instance = instance;
    }

    public static GameView getInstance(ArrayList<User> playerUsers) {
        if (GameView.instance == null) GameView.setInstance(new GameView(playerUsers));
        instance.playerUsers = playerUsers;
        return GameView.instance;
    }

    public void run() {
        String input = Menu.getScanner().nextLine();
        Matcher matcher;
        String regex;

        if (input.matches("^show map$")) showMap(Player.WhichPlayerTurnIs());
        else if (input.matches("next turn")){
            Player.nextTurn();
            this.run();
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    private void showMap(Player player){
        GlobalVariables globalVariables = new GlobalVariables();
        String[][] showMap = new String[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++){
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++){
                Ground ground = Ground.pixelInWhichGround.get(Ground.PairToInt(i, j));
                if (Ground.getGroundByXY(i, j) != null){
                    Integer number = ground.number;
                    showMap[i][j] = number.toString();
                    while(showMap[i][j].length() < 3){
                        showMap[i][j] = '0' + showMap[i][j];
                    }
                }
                else if (!Ground.pixelInWhichGround.containsKey(Ground.PairToInt(i, j))) showMap[i][j]=GlobalVariables . ANSI_BLUE+"███" ;
                else if (!ground.checkIsGroundInPage()) showMap[i][j]=GlobalVariables.ANSI_BLACK + "███" ;
                else {
                    showMap[i][j] = GlobalVariables.ANSI_WHITE + "███";
                }
            }
        }
        player.handleClearToSee();
        for (int i=0;i<player.clearToSeeGrounds.size();i++){
            for (int j=0;j<player.clearToSeeGrounds.get(i).pixelsOfThisGround.size();j++){
                Pair pair=player.clearToSeeGrounds.get(i).pixelsOfThisGround.get(j);
                if (showMap[pair.firstInt][pair.secondInt].charAt(0)>='0' && showMap[pair.firstInt][pair.secondInt].charAt(0)<='9') continue;
                showMap[pair.firstInt][pair.secondInt]= GlobalVariables.ANSI_RED + "███";
            }
        }

        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                System.out.print(showMap[i][j]);
            }
            System.out.println("");
        }

        this.run();
    }
}

