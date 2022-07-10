package controller;

import model.*;

import java.util.ArrayList;
import Enum.LuxuryResource;
import Enum.StrategicResource;
import Enum.UnitStatus;
import static java.lang.Math.min;
import static java.lang.Math.max;
import Enum.FeatureType;

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

    public boolean isFinished() {
        return Player.numberOfAliveAndHaveCapitalPlayer() <= 1 || Player.getYear() >= 2050;
    }

    private boolean isLimitationOkInCities(Player player) {
        for (City city: player.getCities()) {
            if (city.getListOfUnitsInCity().size() > 2) return false;
            else if (city.getListOfUnitsInCity().size() == 0) continue;

            Unit unit = city.getListOfUnitsInCity().get(0);
            if (!Unit.addingUnitFromArrayOfCityToCity(player, city, unit)) return false;

            if (city.getListOfUnitsInCity().size() <= 1) continue;

            unit = city.getListOfUnitsInCity().get(1);
            if (!Unit.addingUnitFromArrayOfCityToCity(player, city, unit)) return false;
        }

        return true;
    }

    private boolean decreaseTurnOfConstruction(Player player) {
        for (City city: player.getCities()) {
            if (city.getRemainedTurnsToBuild() == 0) return false;

            city.setRemainedTurnsToBuild(city.getRemainedTurnsToBuild() - 1);
            if (city.getRemainedTurnsToBuild() == 0) {
                city.finishedConstructed();
                UnitController.spawnUnit(city);
                return false;
            }
        }
        return true;
    }

    private void unitActionsInNextTurn(Player player) {
        for (Unit unit: player.getUnits()) {
            unit.putMp(10);
            unit.checkDestination();
            if (unit instanceof MilitaryUnit militaryUnit) {
                if (militaryUnit.getStatus().equals(UnitStatus.IMPROVING))
                    militaryUnit.setTurnsFortified(militaryUnit.getTurnsFortified() + 1);
                else militaryUnit.setTurnsFortified(0);
            }

            if (unit.getStatus().equals(UnitStatus.HEALTH_IMPROVING) && unit.getHp() < 10) {
                unit.setHp(unit.getHp() + 1);
                if (unit.getHp() == 10)
                    new Notification("your unit health completed", Player.getCounterOfNextRound(), player);
            }
        }
    }

    public boolean canWeNextTurn() {
        Player player = Player.whichPlayerTurnIs();
        if (!player.isAlive()) return true;
        return isLimitationOkInCities(player) && decreaseTurnOfConstruction(player);
    }

    public boolean canWeGoNextTurn(){
        Player player = Player.whichPlayerTurnIs();
        if (!player.isAlive()) return true;
        if (!isLimitationOkInCities(player)) {
            new Notification("One of your cities doesn't obey the rule of one unit of each type limitation.",
                    Player.getCounterOfNextRound(), player);
            return false;
        }

        if (!decreaseTurnOfConstruction(player)) {
            new Notification("Your construction is finished. So, you should build a new one.",
                    Player.getCounterOfNextRound(), player);
            return false;
        }
        return true;
    }

    public void nextTurn() {
        Player player=Player.whichPlayerTurnIs();
        player.setGold(player.getGold() + player.getGoldDifference());
        if (player.getGold() < 0)
            player.setGold(0);

        unitActionsInNextTurn(player);

        /// TODO : otherthings
        for (int i=0;i<player.getCities().size();i++){
            City city = player.getCities().get(i);
            if (city.getHp() < 20) city.setHp(city.getHp() + 1);
            city.setSavedFood(city.getSavedFood() + city.getFoodPerTurn());
            if (city.getSavedFood() > city.getListOfCitizens().size() * 10) {
                city.setSavedFood(city.getSavedFood() - city.getListOfCitizens().size() * 10);
                city.increasingCitizens();
            }
            for (Ground ground : city.getRangeOfCity()) {
                if (ground.canWeUseThisLuxuryResource()) {
                    LuxuryResource luxuryResource = ground.getLuxuryResources().get(0);
                    boolean reachedBefore = false;
                    for (LuxuryResource reachedLuxuryResource : player.getAllLuxuryResources()) {
                        if (reachedLuxuryResource.equals(luxuryResource)) {
                            reachedBefore = true;
                        }
                    }
                    if (!reachedBefore)
                        player.getAllLuxuryResources().add(luxuryResource);

                }
                if (ground.canWeUseThisStrategicResource()) {
                    StrategicResource strategicResource = ground.getStrategicResources().get(0);
                    boolean reachedBefore = false;
                    for (StrategicResource reachedStrategicResource : player.getAllStrategicResources()) {
                        if (reachedStrategicResource.equals(strategicResource)) {
                            reachedBefore = true;
                        }
                    }
                    if (!reachedBefore)
                        player.getAllStrategicResources().add(strategicResource);

                }
            }
            //TODO : 1 science for each Citizen
        }
        for (int i=0;i<player.getAllTechnologyTypes().size();i++){
            if (player.getUnderConstructionTechnology()==null) continue;
            if (player.getUnderConstructionTechnology().getTechnologyType() == player.getAllTechnologyTypes().get(i).getTechnologyType()){
                player.getAllTechnologyTypes().get(i).decreaseTimeRemain(max(1
                        ,min(player.getAllTechnologyTypes().get(i).getTimeRemain(),player.getScience())));
                if (player.getAllTechnologyTypes().get(i).getTimeRemain()==0){
                    player.addTechnology(player.getAllTechnologyTypes().get(i).getTechnologyType());
                    new Notification("Your research is finished.", Player.getCounterOfNextRound(), player);
                }
            }
        }
        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i) instanceof Worker){   
                if (((Worker) player.getUnits().get(i)).getIsWorking()){
                    Ground ground=player.getUnits().get(i).getGround();
                    if (ground.getImprovementTypeInProgress()!=null){
                        ground.getImprovementTypeInProgress().decreaseAmount(1);
                        if (ground.getImprovementTypeInProgress().getTurnRemained()==0){
                            ground.putImprovementTypeInThisGround();
                        }
                    }
                    if (ground.getPlunderingImprovementType()!=null){
                        ground.getPlunderingImprovementType().decreaseAmount(1);
                        if (ground.getPlunderingImprovementType().getTurnRemained()==0){
                            ground.setImprovementType(ground.getPlunderingImprovementType().getImprovementType());
                        }
                    }
                    if (ground.getRailWay()!=null) ground.getRailWay().decreaseTurn(1);
                    if (ground.getRoad()!=null) ground.getRoad().decreaseTurn(1);
                    if (ground.getCounterOfDestroyingFeature()!=0){
                        ground.increaseCounterOfDestroyingFeature(1);
                        if (ground.getCounterOfDestroyingFeature()==ground.getFeatureType().getTurn()){
                            ground.setFeatureType(FeatureType.NOTHING);
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
            GlobalVariables.numberOfTiles=numberOfCity-1;
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
        ArrayList<Unit> unitArrayList = Ground.getGroundByNumber(firstGroundNumber).unitsOfASpecificPlayerInThisGround(player);
        Unit unitInThisGround=null;
        for (Unit unit : unitArrayList){
            if (unit instanceof UnMilitaryUnit && type.equals("UnMilitary")) unitInThisGround=unit;
            if (unit instanceof MilitaryUnit && type.equals("Military")) unitInThisGround=unit;
        }
        if (unitInThisGround==null){
            return "there is no unit";
        }
        if (unitInThisGround instanceof Worker &&  Ground.getGroundByNumber(firstGroundNumber).getImprovementTypeInProgress()!=null) {
            Ground.getGroundByNumber(firstGroundNumber).setImprovementTypeInProgress(null);
        }
        boolean exit = false;
        if (Ground.getGroundByNumber(secondGroundNumber).getGroundType().isBlock()) exit=true;
        for (Unit unit : Ground.getGroundByNumber(secondGroundNumber).unitsInThisGround()) {
            if (unit.getGround().getNumber() == secondGroundNumber && ((unit instanceof MilitaryUnit && type.equals("Military"))
                    || (type.equals("UnMilitary")))) {
                exit = true;
                break;
            }
        }
        if (exit) {
            return "unit can not go to that ground:(";
        }
        boolean fail=false;
        for (Unit unit : unitArrayList) {
            if ((unit instanceof MilitaryUnit && type.equals("Military")) || (unit instanceof UnMilitaryUnit && type.equals("UnMilitary"))) {
                unit.setDestination(Ground.getGroundByNumber(secondGroundNumber));
                if (unit instanceof Worker) ((Worker) unit).setWorking(false);
            }
            unit.checkDestination();
        }
        if (unitArrayList.size()==0){
            fail=true;
        }
        if (fail) return "unit move failed successfully :)";
        return "unit moved successfully";
    }

    public void deleteUnit(Unit unit) {

    }
    public void clearLand(Ground ground){
        System.out.println(ground.getNumber());
        ground.setCounterOfDestroyingFeature(1);
    }
    public void buildRoad(Ground ground){
        ground.buildRoad();
    }
    public void buildRailway(Ground ground){
        ground.buildRailWay();
    }

}
