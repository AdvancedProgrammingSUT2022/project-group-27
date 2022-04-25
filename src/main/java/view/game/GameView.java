package view.game;


import Enum.Message;
import controller.Game;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import static java.lang.Math.max;
import static java.lang.Math.min;
import Enum.GroundType;

public class GameView {
    //singleton pattern
    private static GameView instance = null;
    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    private GameView(ArrayList<User> playerUsers) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;

        setFirstGroundsForPlayers();
        setRivers();
        setGroundsType();
    }
    GlobalVariables globalVariables = new GlobalVariables();
    private void setGroundsType(){
        Random random= new Random();
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            int rand=random.nextInt(0,200);
            if (rand<70){
                Ground.getGroundByNumber(i).setGroundType(GroundType.DESERT);
            }
            else if (rand<140){
                Ground.getGroundByNumber(i).setGroundType(GroundType.GRASS_PLOT);
            }
            else if (rand<150){
                Ground.getGroundByNumber(i).setGroundType(GroundType.HILL);
            }
            else if (rand<160){
                Ground.getGroundByNumber(i).setGroundType(GroundType.MOUNTAIN);
            }
            else if (rand<170){
                Ground.getGroundByNumber(i).setGroundType(GroundType.OCEAN);
            }
            else if (rand<180){
                Ground.getGroundByNumber(i).setGroundType(GroundType.SNOW);
            }
            else if (rand<190){
                Ground.getGroundByNumber(i).setGroundType(GroundType.TUNDRA);
            }
            else if (rand<200){
                Ground.getGroundByNumber(i).setGroundType(GroundType.PLAIN);
            }

        }
    }
    private void setRivers(){
        Random random= new Random();
        int numberOfRivers=random.nextInt(0,GlobalVariables.numberOfTiles/2)+5;
        for (int i=0;i<numberOfRivers;i++){
            int first=random.nextInt(0,GlobalVariables.numberOfTiles)+1,second=random.nextInt(0,GlobalVariables.numberOfTiles)+1;
            while(first==second || !River.couldWePutRiverBetweenTheseTwoGround(Ground.getGroundByNumber(first), Ground.getGroundByNumber(second))){
                first=random.nextInt(0,GlobalVariables.numberOfTiles)+1;
                second=random.nextInt(0,GlobalVariables.numberOfTiles)+1;
            }
            River river= new River(Ground.getGroundByNumber(first),Ground.getGroundByNumber(second));
        }
    }
    private void setFirstGroundsForPlayers() {
        Random rand = new Random();
        for (User playerUser : playerUsers) {
            int idStartGround = rand.nextInt(GlobalVariables.numberOfTiles) + 1;
            while (!Ground.getGroundByNumber(idStartGround).isFreeOfMilitaryUnit()) {
                idStartGround = rand.nextInt(GlobalVariables.numberOfTiles) + 1;
            }

            Ground ground = Ground.getGroundByNumber(idStartGround);
            Player player = new Player(playerUser);
            UnMilitaryUnit unMilitaryUnit = new UnMilitaryUnit(ground, player);
            MilitaryUnit militaryUnit = new MilitaryUnit(ground, player);
            player.getUnits().add(militaryUnit);
            player.getUnits().add(unMilitaryUnit);
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

        if (input.matches("^show map$")) {
            showMap(Player.whichPlayerTurnIs());
            this.run();
        }
        else if (input.matches("next turn")){
            Player.nextTurn();
            this.run();
        }
        else if (input.matches("^move unit .+$")){
            matcher = controller.getInput("move unit",
                    new ArrayList<String>(List.of("((--origin)|(-o)) (?<origin>\\d+)", "((--destination)|(-d)) (?<destination>\\d+)", "(?<type>(Military)|(UnMilitary))")), input);
            if (matcher != null) moveUnits(matcher);
            else {
                System.out.println(Message.INVALID_COMMAND);
                this.run();
            }
        }
        else {
            System.out.println(Message.INVALID_COMMAND);
            this.run();
        }
    }

    public void showMap(Player player){

        String[][] showMap = new String[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i = 0; i < globalVariables.surfaceHeight ; i++) {
            for (int j = 0; j < globalVariables.surfaceWidth ; j++) {
                showMap[i][j]=GlobalVariables.ANSI_BLACK + "█";
            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++){
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++){
                Ground ground = Ground.getPixelInWhichGround().get(Ground.PairToInt(i, j));
                if (Ground.getGroundByXY(i, j) != null){
                    Integer number = ground.getNumber();
                    String numberString=number.toString();
                    for (int k=-1;k<numberString.length()-1;k++){
                        showMap[i][j+k]=""+numberString.charAt(k+1);
                    }
                } else if (!Ground.getPixelInWhichGround().containsKey(Ground.PairToInt(i, j))) showMap[i][j]=GlobalVariables . ANSI_BLACK+"█" ;
                else if (!ground.checkIsGroundInPage()) showMap[i][j]=GlobalVariables.ANSI_BLACK + "█" ;
                else {
                    showMap[i][j] = GlobalVariables.ANSI_WHITE + "█";
                }
            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                Ground ground = Ground.getPixelInWhichGround().get(Ground.PairToInt(i, j));
                if (Ground.getGroundByXY(i, j) != null) {
                    Integer number = ground.getNumber();
                    String numberString = number.toString();
                    while(numberString.length()<3) numberString="0"+numberString;
                    for (int k = -1; k < numberString.length() - 1; k++) {
                        showMap[i][j + k] = "" + numberString.charAt(k + 1);
                    }
                }
            }
        }

                    player.handleClearToSee();
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++){
            player.addGroundToVisitedGround(player.getClearToSeeGrounds().get(i));
        }
        for (int i = 0; i < player.getWasClearedToSeeGrounds().size(); i++){
            for (int j = 0; j < player.getWasClearedToSeeGrounds().get(i).getPixelsOfThisGround().size(); j++){
                Pair pair = player.getWasClearedToSeeGrounds().get(i).getPixelsOfThisGround().get(j);

                if (pair.firstInt>=globalVariables.surfaceHeight || pair.secondInt>=globalVariables.surfaceWidth) continue;
                if (showMap[pair.firstInt][pair.secondInt].charAt(0) >= '0' && showMap[pair.firstInt][pair.secondInt].charAt(0) <= '9') continue;
              //  System.out.println(i + " fne " + j + " " + player.wasClearedToSeeGrounds.get(i).number);
                showMap[pair.firstInt][pair.secondInt] = GlobalVariables.ANSI_RED + "█";
            }
        }
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++){
            for (int j = 0; j < player.getClearToSeeGrounds().get(i).getPixelsOfThisGround().size(); j++){
                Pair pair = player.getClearToSeeGrounds().get(i).getPixelsOfThisGround().get(j);

                if (pair.firstInt>=globalVariables.surfaceHeight || pair.secondInt>=globalVariables.surfaceWidth) continue;
                if (showMap[pair.firstInt][pair.secondInt].charAt(0) >= '0' && showMap[pair.firstInt][pair.secondInt].charAt(0) <= '9') continue;

                showMap[pair.firstInt][pair.secondInt] = player.getClearToSeeGrounds().get(i).getGroundType().getColor() + "█";
            }
        }
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++){
            Ground ground=player.getClearToSeeGrounds().get(i);
            ArrayList <Unit> unitArrayList=player.getClearToSeeGrounds().get(i).unitsOfASpecificPlayerInThisGround(player);
            for (Unit unit : unitArrayList){
                if (unit instanceof MilitaryUnit){
                    showMap[ground.getxLocation()+2][ground.getyLocation()-4]=GlobalVariables.ANSI_CYAN+"M";
                }
                else showMap[ground.getxLocation()+2][ground.getyLocation()+4]=GlobalVariables.ANSI_CYAN+"U";
            }
            if (ground.getGroundType()==GroundType.DESERT){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_YELLOW+"D";
            }
            if (ground.getGroundType()==GroundType.GRASS_PLOT){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_GREEN+"G";
            }
            if (ground.getGroundType()==GroundType.HILL){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_YELLOW+"H";
            }
            if (ground.getGroundType()==GroundType.MOUNTAIN){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_YELLOW+"M";
            }
            if (ground.getGroundType()==GroundType.OCEAN){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_CYAN+"O";
            }
            if (ground.getGroundType()==GroundType.PLAIN){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_BLUE+"P";
            }
            if (ground.getGroundType()==GroundType.SNOW){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_BLUE+"S";
            }
            if (ground.getGroundType()==GroundType.TUNDRA){
                showMap[ground.getxLocation()-2][ground.getyLocation()]=GlobalVariables.ANSI_BLUE+"T";
            }
        }
        for (int i=0;i<Player.getAllPlayers().size();i++){
            ///TODO: is the below if correct?
            if (player.equals(Player.getAllPlayers().get(i))) continue;
            for (int j=0;j<Player.getAllPlayers().get(i).getUnits().size();j++){
                Ground ground=Player.getAllPlayers().get(i).getUnits().get(j).getGround();
                if (player.isThisGroundVisible(ground)){
                    if (Player.getAllPlayers().get(i).getUnits().get(j) instanceof MilitaryUnit){
                        showMap[ground.getxLocation()+1][ground.getyLocation()-1]=GlobalVariables.ANSI_RED+"M";
                    }
                    else showMap[ground.getxLocation()+1][ground.getyLocation()+1]=GlobalVariables.ANSI_RED+"U";
                }

            }
        }
        int[][] visit = new int[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (showMap[i][j]==GlobalVariables.ANSI_BLACK+"█" && showMap[i+1][j+1]==GlobalVariables.ANSI_BLACK+"█" && showMap[i-1][j-1]==GlobalVariables.ANSI_BLACK+"█"){
                    //showMap[i][j]=GlobalVariables.ANSI_BLUE+'\\';
                    visit[i][j]=1;
                }
                if (showMap[i][j]==GlobalVariables.ANSI_BLACK+"█" && showMap[i+1][j-1]==GlobalVariables.ANSI_BLACK+"█" && showMap[i-1][j+1]==GlobalVariables.ANSI_BLACK+"█"){
                    //showMap[i][j]=GlobalVariables.ANSI_BLUE+'\\';
                    visit[i][j]=2;
                }

            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (Ground.getPixelInWhichGround().containsKey(Ground.PairToInt(i,j))) continue;
                if (visit[i][j]==1){
                    showMap[i][j]=GlobalVariables.ANSI_BLUE+'\\';
                }
                else if (visit[i][j]==2){
                    showMap[i][j]=GlobalVariables.ANSI_BLUE+'/';
                }
                else if (showMap[i][j]==GlobalVariables.ANSI_BLACK+"█"){
                    showMap[i][j]=GlobalVariables.ANSI_BLUE+'-';
                }

            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (showMap[i-1][j]==GlobalVariables.ANSI_BLACK+"█" && showMap[i+1][j]==GlobalVariables.ANSI_BLACK+"█") visit[i][j]=5;
                if (showMap[i][j-1]==GlobalVariables.ANSI_BLACK+"█" && showMap[i][j+1]==GlobalVariables.ANSI_BLACK+"█") visit[i][j]=5;
            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (visit[i][j]==5){
                    showMap[i][j]=GlobalVariables.ANSI_BLACK+"█";
                }
            }
        }
        for (int i = 0; i < River.getAllRivers().size(); i++){
            Ground firstGround = River.getAllRivers().get(i).getFirstGround(), secondGround = River.getAllRivers().get(i).getSecondGround();

            if (firstGround.getyLocation()==secondGround.getyLocation()){
                //System.out.println(firstGround.number + " " + secondGround.number);
                for (int y=firstGround.getyLocation()-globalVariables.arz6Zelie/3-1;y<=firstGround.getyLocation()+ globalVariables.arz6Zelie/3+1;y++){
                    showMap[(firstGround.getxLocation()+secondGround.getxLocation())/2][y]=GlobalVariables.ANSI_BLUE+"█";
                }
            }
            else{
                int smallerX=min(firstGround.getxLocation(),secondGround.getxLocation());
                int biggerX=max(firstGround.getxLocation(),secondGround.getxLocation());
                int smallerY=min(firstGround.getyLocation(),secondGround.getyLocation());
                int biggerY=max(firstGround.getyLocation(),secondGround.getyLocation());
                for (int x=smallerX;x<=biggerX;x++){
                    for (int y=smallerY;y<=biggerY;y++){
                        if (globalVariables.isEqual(globalVariables.distanceOfTwoPoints(x,y,firstGround.getxLocation(),firstGround.getyLocation()),globalVariables.distanceOfTwoPoints(x,y,secondGround.getxLocation(),secondGround.getyLocation()))==1){
                            showMap[x][y]=GlobalVariables.ANSI_BLUE+"█";
                        }
                    }
                }
            }

        }
        printMap(showMap, globalVariables);
       // this.run();
    }

    private void printMap(String[][] showMap, GlobalVariables globalVariables) {
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                System.out.print(showMap[i][j]);
            }
            System.out.println("");
        }
    }

    private void moveUnits(Matcher matcher){
        int firstGroundNumber = Integer.parseInt(matcher.group("origin"));
        int secondGroundNumber = Integer.parseInt(matcher.group("destination"));
        String type = matcher.group("type");

        this.controller.moveUnits(firstGroundNumber,secondGroundNumber,type);
        this.run();
    }
}

