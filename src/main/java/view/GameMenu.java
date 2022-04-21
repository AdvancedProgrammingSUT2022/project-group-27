package view;

import controller.Controller;
import controller.GameController;
import Enum.Message;
import model.GlobalVariables;
import model.Ground;
import model.Pair;

import java.util.HashMap;
import java.util.regex.Matcher;

import static java.lang.Math.sqrt;

public class GameMenu extends Menu{
    //singleton pattern
    private static GameMenu instance = null;
    private GameController controller;

    private GameMenu() {
        this.controller = GameController.getInstance();
    }

    private static void setInstance(GameMenu instance) {
        GameMenu.instance = instance;
    }

    public static GameMenu getInstance() {
        if (GameMenu.instance == null) GameMenu.setInstance(new GameMenu());
        return GameMenu.instance;
    }

    public void createMap(){
        //TODO.. change it to bfs method
        GlobalVariables globalVariables = new GlobalVariables();
        int counter = 0;
        int numberOfCity = 1;
        for (int i = 0; i < globalVariables.surfaceHeight; i += globalVariables.arz6Zelie){
            counter++;
            int starter = 0;
            if (counter % 2 == 0) starter += globalVariables.tool6Zelie / 2;
            for (int j = starter; j < globalVariables.surfaceWidth; j += globalVariables.tool6Zelie){
                Ground ground = new Ground(i, j, numberOfCity++);
                if (!ground.checkIsGroundInPage()){
                    ground.number=0;
                    numberOfCity--;
                }

                Ground.allGround.add(ground);
            }
        }
        for (int i = 0; i < globalVariables.surfaceHeight; i++){
            for (int j = 0; j < globalVariables.surfaceWidth; j++){
                double bestForMatchPixels = 1e9;
                int checkPixelType = 0;
                Pair idOfBestMatch = new Pair(0,0);
                for (int a = 0; a < globalVariables.surfaceHeight; a++){
                    for (int b = 0; b < globalVariables.surfaceWidth; b++){
                        Ground ground = Ground.getGroundByXY(a,b);
                        if (ground == null);
                        else if (globalVariables.isEqual(bestForMatchPixels, globalVariables.distanceOfTwoPoints(i, j, a, b)) == 1){
                            checkPixelType=1;
                        }
                        else if (bestForMatchPixels > globalVariables.distanceOfTwoPoints(i, j, a, b)){
                            bestForMatchPixels = globalVariables.distanceOfTwoPoints(i, j, a, b);
                            checkPixelType = 0;
                            idOfBestMatch= new Pair(a,b);
                        }
                    }
                }

                if (checkPixelType == 0){
                    Ground ground = Ground.getGroundByXY(idOfBestMatch.firstInt, idOfBestMatch.secondInt);
                    if (ground == null){
                        System.out.println("Didn't find the ground!!!!");
                    } else {
                        Pair pixel = new Pair(i, j);
                        ground.pixelsOfThisGround.add(pixel);
                        Ground.pixelInWhichGround.put(Ground.PairToInt(pixel.firstInt, pixel.secondInt), ground);
                    }
                }
            }
        }
    }

    public void showMap(){
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

    }

    @Override
    public void run() {
        String input = getScanner().nextLine();
        Matcher matcher;
        String regex;

        if (input.matches("^menu show-current$")) showMenu();
        else if (input.matches("^menu exit$")) exitMenu();
        else if (input.matches((regex = "^menu enter (?<menuName>\\S+)$"))) {
            matcher = Controller.findMatcherFromString(input, regex);
            if (matcher.find()) enterMenu(matcher.group("menuName"));
            else System.out.println(Message.INVALID_COMMAND);
        }
        else if (input.equals("create map")){
            createMap();
            this.run();
        }
        else if (input.equals("show map")){
            showMap();
            this.run();
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    @Override
    protected void showMenu() {
        System.out.println("Game Menu");
        this.run();
    }

    @Override
    protected void enterMenu(String newMenuName) {
        switch (newMenuName) {
            case "Main_Menu":
                return;
            case "Profile_Menu", "Login_Menu":
                System.out.println(Message.INVALID_NAVIGATION);
                break;
            case "Game_Menu" :
                System.out.println(Message.CURRENT_MENU);
                break;
            default :
                System.out.println(Message.INVALID_MENU_NAME);
                break;
        }

        this.run();
    }
}
