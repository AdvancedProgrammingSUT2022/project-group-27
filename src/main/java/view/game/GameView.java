package view.game;


import Enum.Message;
import controller.Game;
import model.GlobalVariables;
import model.Ground;
import model.User;
import view.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameView {
    //singleton pattern
    private static GameView instance = null;
    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    private GameView(ArrayList<User> playerUsers) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
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

        if (input.matches("^show map$")) showMap();
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    private void showMap(){
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
                else if (!Ground.pixelInWhichGround.containsKey(Ground.PairToInt(i, j))) showMap[i][j]=GlobalVariables . ANSI_RED+"███" ;
                else if (!ground.checkIsGroundInPage()) showMap[i][j]=GlobalVariables.ANSI_BLACK + "███" ;
                else showMap[i][j] = GlobalVariables.ANSI_BLUE + "███";
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

