package controller;

import model.*;

import java.util.ArrayList;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Game extends Controller {
    //singleton pattern
    private static Game instance = null;

    private Game() {
        this.createMap();
    }

    private static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public static Game getInstance() {
        if (Game.instance == null) Game.setInstance(new Game());
        return Game.instance;
    }

    public void nextTurn() {
        Player player = Player.whichPlayerTurnIs();
        player.setGold(player.getGold() + player.getGoldDifference());
        for (int i=0;i<player.getCities().size();i++){
            player.getCities().get(i).setRemainedTurnsToBuild(player.getCities().get(i).getRemainedTurnsToBuild()-1);
            if (player.getCities().get(i).getRemainedTurnsToBuild()==0){
                UnitController.spawnUnit(player.getCities().get(i));
            }


        }
        for (int i = 0; i < player.getUnits().size(); i++) {
            player.getUnits().get(i).putMp(10);
            player.getUnits().get(i).checkDestination();
        }

        player.setGold(0);
        player.setFood(0);
        /// TODO : otherthings
        for (int i=0;i<player.getCities().size();i++){
            player.getCities().get(i).updateCityGoldAndFoodAndOtherThings();
            player.setGold(player.getGold() + player.getCities().get(i).getGold());
            player.setFood(player.getFood() + player.getFood());
            player.setScience(player.getScience() + 3);
            //TODO : 1 science for each Citizen
        }
        for (int i=0;i<player.getAllTechnologyTypes().size();i++){
            if (player.getUnderConstructionTechnology()==null) continue;
            if (player.getUnderConstructionTechnology() == player.getAllTechnologyTypes().get(i).getTechnologyType()){
                player.getAllTechnologyTypes().get(i).decreaseTimeRemain(1);
                if (player.getAllTechnologyTypes().get(i).getTimeRemain()==0){
                    player.addTechnology(player.getAllTechnologyTypes().get(i).getTechnologyType());
                }
            }
        }
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){
                if (((Worker) player.getUnits().get(i)).getIsWorking()){
                    Ground ground=player.getUnits().get(i).getGround();
                    if (ground.getImprovementTypeInProgress()!=null){
                        ground.getImprovementTypeInProgress().decreaseTurn(1);
                        if (ground.getImprovementTypeInProgress().getTurn()==0){
                            ground.putImprovementTypeInThisGround();
                        }
                    }
                }
            }
        }
    }

    private void createMap() {
        //TODO.. change it to bfs method
        GlobalVariables globalVariables = new GlobalVariables();
        int counter = 0;
        int numberOfCity = 1;
        for (int i = 0; i < globalVariables.surfaceWidth; i += globalVariables.arz6Zelie) {
            counter++;
            int starter = 0;
            if (counter % 2 == 0) starter += globalVariables.tool6Zelie / 2;
            for (int j = starter; j < globalVariables.surfaceHeight; j += globalVariables.tool6Zelie) {
                Ground ground = new Ground(j, i, numberOfCity++);
                //TODO
                if (!ground.checkIsGroundInPage()) {
                    ground.setNumber(0);
                    numberOfCity--;
                }

                Ground.getAllGround().add(ground);
            }
        }
        for (int i = 0; i < globalVariables.surfaceHeight; i++) {
            for (int j = 0; j < globalVariables.surfaceWidth; j++) {
                double bestForMatchPixels = 1e9;
                int checkPixelType = 0;
                Pair idOfBestMatch = new Pair(0, 0);
                for (int a = max(i - globalVariables.tool6Zelie, 0); a < min(i + globalVariables.tool6Zelie + 1, globalVariables.surfaceHeight); a++) {
                    for (int b = max(0, j - globalVariables.arz6Zelie); b < min(j + globalVariables.arz6Zelie + 1, globalVariables.surfaceWidth); b++) {
                        Ground ground = Ground.getGroundByXY(a, b);
                        if (ground == null) ;
                        else if (globalVariables.isEqual(bestForMatchPixels, globalVariables.distanceOfTwoPoints(i, j, a, b)) == 1) {
                            checkPixelType = 1;
                        } else if (bestForMatchPixels > globalVariables.distanceOfTwoPoints(i, j, a, b)) {
                            bestForMatchPixels = globalVariables.distanceOfTwoPoints(i, j, a, b);
                            checkPixelType = 0;
                            idOfBestMatch = new Pair(a, b);
                        }
                    }
                }

                if (checkPixelType == 0) {
                    Ground ground = Ground.getGroundByXY(idOfBestMatch.getFirstInt(), idOfBestMatch.getSecondInt());
                    if (ground != null) {
                        Pair pixel = new Pair(i, j);
                        ground.getPixelsOfThisGround().add(pixel);
                        Ground.getPixelInWhichGround().put(Ground.PairToInt(pixel.getFirstInt(), pixel.getSecondInt()), ground);
                    }
                }
            }
        }
    }

    public String moveUnits(int firstGroundNumber, int secondGroundNumber, String type) {
        Player player = Player.whichPlayerTurnIs();
        if (Ground.getGroundByNumber(firstGroundNumber).getImprovementTypeInProgress()!=null)
            Ground.getGroundByNumber(firstGroundNumber).setImprovementTypeInProgress(null);
        ArrayList<Unit> unitArrayList = Ground.getGroundByNumber(firstGroundNumber).unitsOfASpecificPlayerInThisGround(player);
        /// TODO : type of unit
        boolean exit = false;
        if (Ground.getGroundByNumber(secondGroundNumber).getGroundType().isBlock()) exit=true;
        for (Unit unit : Ground.getGroundByNumber(secondGroundNumber).unitsInThisGround()) {
            if (unit.getGround().getNumber() == secondGroundNumber && ((unit instanceof MilitaryUnit && type.equals("Military"))
                    || (unit instanceof UnMilitaryUnit && type.equals("UnMilitary")))) {
                exit = true;
                break;
            }
        }
        if (exit) {
            return "unit can not go to that ground:(";
        }
        for (Unit unit : unitArrayList) {
            if ((unit instanceof MilitaryUnit && type.equals("Military")) || (unit instanceof UnMilitaryUnit && type.equals("UnMilitary"))) {
                unit.setDestination(Ground.getGroundByNumber(secondGroundNumber));
                if (unit instanceof Worker) ((Worker) unit).setWorking(false);
            }
            unit.checkDestination();
        }
        return "unit moved successfully";
    }
    public void deleteUnit(Unit unit) {

    }
}
