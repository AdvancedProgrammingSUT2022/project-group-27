package view.game;

import model.*;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

import Enum.FeatureType;
import Enum.GroundType;
import Enum.ImprovementType;
import Enum.BonusResource;
import Enum.LuxuryResource;
import Enum.StrategicResource;
import Enum.MilitaryType;
public class ShowMap {
    private GlobalVariables globalVariables = new GlobalVariables();
    private Player player;

    public ShowMap(Player player) {
        this.player = player;
    }

    public void run() {
        showMap(this.player);
    }

    private void showMap(Player player) {
        String[][] showMap = new String[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i = 0; i < globalVariables.surfaceHeight; i++) {
            for (int j = 0; j < globalVariables.surfaceWidth; j++) {
                showMap[i][j] = GlobalVariables.ANSI_BLACK + "█";
            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                Ground ground = Ground.getPixelInWhichGround().get(Ground.PairToInt(i, j));
                if (Ground.getGroundByXY(i, j) != null) {
                    Integer number = ground.getNumber();
                    String numberString = number.toString();
                    for (int k = -1; k < numberString.length() - 1; k++) {
                        showMap[i][j + k] = "" + numberString.charAt(k + 1);
                    }
                } else if (!Ground.getPixelInWhichGround().containsKey(Ground.PairToInt(i, j)))
                    showMap[i][j] = GlobalVariables.ANSI_BLACK + "█";
                else if (!ground.checkIsGroundInPage()) showMap[i][j] = GlobalVariables.ANSI_BLACK + "█";
                else {
                    showMap[i][j] = GlobalVariables.ANSI_WHITE + "█";
                }
            }
        }
        showMapAndHandlingCenters(player, showMap);
    }

    private void showMapAndHandlingCenters(Player player, String[][] showMap) {
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                Ground ground = Ground.getPixelInWhichGround().get(Ground.PairToInt(i, j));
                if (Ground.getGroundByXY(i, j) != null) {
                    Integer number = ground.getNumber();
                    String numberString = number.toString();
                    while (numberString.length() < 3) numberString = "0" + numberString;
                    for (int k = -1; k < numberString.length() - 1; k++) {
                        showMap[i][j + k] = "" + numberString.charAt(k + 1);
                    }
                }
            }
        }

        player.handleClearToSee();
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            player.addGroundToVisitedGround(player.getClearToSeeGrounds().get(i));
        }
        for (int i = 0; i < player.getWasClearedToSeeGrounds().size(); i++) {
            for (int j = 0; j < player.getWasClearedToSeeGrounds().get(i).getPixelsOfThisGround().size(); j++) {
                Pair pair = player.getWasClearedToSeeGrounds().get(i).getPixelsOfThisGround().get(j);

                if (pair.getFirstInt() >= globalVariables.surfaceHeight ||
                        pair.getSecondInt() >= globalVariables.surfaceWidth)
                    continue;
                if (showMap[pair.getFirstInt()][pair.getSecondInt()].charAt(0) >= '0'
                        && showMap[pair.getFirstInt()][pair.getSecondInt()].charAt(0) <= '9')
                    continue;
                showMap[pair.getFirstInt()][pair.getSecondInt()] = GlobalVariables.ANSI_RED + "█";
            }
        }
        showMapVisibleTile(player, showMap);
    }

    private void showMapVisibleTile(Player player, String[][] showMap) {
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            for (int j = 0; j < player.getClearToSeeGrounds().get(i).getPixelsOfThisGround().size(); j++) {
                Pair pair = player.getClearToSeeGrounds().get(i).getPixelsOfThisGround().get(j);

                if (pair.getFirstInt() >= globalVariables.surfaceHeight
                        || pair.getSecondInt() >= globalVariables.surfaceWidth)
                    continue;
                if (showMap[pair.getFirstInt()][pair.getSecondInt()].charAt(0) >= '0'
                        && showMap[pair.getFirstInt()][pair.getSecondInt()].charAt(0) <= '9')
                    continue;
                showMap[pair.getFirstInt()][pair.getSecondInt()] = player.getClearToSeeGrounds().get(i).getGroundType().getColor() + "█";
            }
        }
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground = player.getClearToSeeGrounds().get(i);
            ArrayList<Unit> unitArrayList = player.getClearToSeeGrounds().get(i).unitsOfASpecificPlayerInThisGround(player);
            for (Unit unit : unitArrayList) {
                if (unit instanceof MilitaryUnit) {
                    showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_CYAN + "M";
                    if (unit.getMilitaryType()== MilitaryType.ANTITANKGUN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "G";
                    if (unit.getMilitaryType()== MilitaryType.ARCHER)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "A";
                    if (unit.getMilitaryType()== MilitaryType.WARRIOR)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "W";
                    if (unit.getMilitaryType() == MilitaryType.ARTILLERY)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "T";
                    if (unit.getMilitaryType()== MilitaryType.CANON)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "C";
                    if (unit.getMilitaryType()== MilitaryType.CATAPULT)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "P";
                    if (unit.getMilitaryType()== MilitaryType.CAVALRY)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "V";
                    if (unit.getMilitaryType()== MilitaryType.CHARIOTARCHER)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "O";
                    if (unit.getMilitaryType()== MilitaryType.CROSSBOWMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "B";
                    if (unit.getMilitaryType()== MilitaryType.HORSEMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "H";
                    if (unit.getMilitaryType()== MilitaryType.INFANTRY)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "I";
                    if (unit.getMilitaryType()== MilitaryType.KNIGHT)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "K";
                    if (unit.getMilitaryType()== MilitaryType.LANCER)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "L";
                    if (unit.getMilitaryType()== MilitaryType.LONGSWORDSMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "l";
                    if (unit.getMilitaryType()== MilitaryType.MUSKETMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "M";
                    if (unit.getMilitaryType()== MilitaryType.PANZER)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "Z";
                    if (unit.getMilitaryType()== MilitaryType.PIKEMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "p";
                    if (unit.getMilitaryType()== MilitaryType.RIFLEMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "R";
                    if (unit.getMilitaryType()== MilitaryType.SCOUT)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "s";
                    if (unit.getMilitaryType()== MilitaryType.SPEARMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "S";
                    if (unit.getMilitaryType()== MilitaryType.SWORDSMAN)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "W";
                    if (unit.getMilitaryType()== MilitaryType.TANK)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "T";
                    if (unit.getMilitaryType()== MilitaryType.TREBUCHET)
                        showMap[ground.getxLocation() + 2][ground.getyLocation() - 4] = GlobalVariables.ANSI_BLUE + "t";

                } else {
                    if (unit instanceof SettlerUnit) showMap[ground.getxLocation() + 2][ground.getyLocation() + 4] = GlobalVariables.ANSI_BLUE + "S";
                    else showMap[ground.getxLocation() + 2][ground.getyLocation() + 4] = GlobalVariables.ANSI_BLUE + "W";
                }

            }
            if (ground.getGroundType() == GroundType.DESERT) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_YELLOW + "D";
            }
            if (ground.getGroundType() == GroundType.GRASS_PLOT) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "G";
            }
            if (ground.getGroundType() == GroundType.HILL) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_YELLOW + "H";
            }
            if (ground.getGroundType() == GroundType.MOUNTAIN) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_YELLOW + "M";
            }
            if (ground.getGroundType() == GroundType.OCEAN) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_CYAN + "O";
            }
            if (ground.getGroundType() == GroundType.PLAIN) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_BLUE + "P";
            }
            if (ground.getGroundType() == GroundType.SNOW) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_BLUE + "S";
            }
            if (ground.getGroundType() == GroundType.TUNDRA) {
                showMap[ground.getxLocation() - 2][ground.getyLocation()] = GlobalVariables.ANSI_BLUE + "T";
            }
        }
        showMapNumberOfPlayer(player, showMap);
    }
    private void showMapNumberOfPlayer(Player player,String[][] showMap){
        for (int i=0;i<Player.getAllPlayers().size();i++){
            for (City city : Player.getAllPlayers().get(i).getCities()){
                for (Ground ground : city.getRangeOfCity()){
                    showMap[ground.getxLocation()-3][ground.getyLocation()-6]=GlobalVariables.ANSI_WHITE+Integer.toString(i+1);
                }
            }
            for (Unit unit : Player.getAllPlayers().get(i).getUnits()){
                Ground ground=unit.getGround();
                showMap[ground.getxLocation()-3][ground.getyLocation()-6]=GlobalVariables.ANSI_WHITE+Integer.toString(i+1);
            }
        }
        showMapFeature(player, showMap);
    }
    private void showMapFeature(Player player, String[][] showMap) {
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground = player.getClearToSeeGrounds().get(i);
            if (ground.getFeatureType() == FeatureType.FOREST) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "F";
            }
            if (ground.getFeatureType() == FeatureType.JUNGLE) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "J";
            }
            if (ground.getFeatureType() == FeatureType.ICE) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "I";
            }
            if (ground.getFeatureType() == FeatureType.OASIS) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_YELLOW + "O";
            }
            if (ground.getFeatureType() == FeatureType.WATERSHED) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_BLACK + "W";
            }
            if (ground.getFeatureType() == FeatureType.MARSH) {
                showMap[ground.getxLocation() - 3][ground.getyLocation()] = GlobalVariables.ANSI_CYAN + "M";
            }
        }
        showMapImprovement(player, showMap);
    }
    private void showMapImprovement(Player player, String[][] showMap){
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            Ground ground=Ground.getGroundByNumber(i);
            if (Ground.getGroundByNumber(i).getImprovementType()!=null){
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.MINE)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "M";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.AGRICULTURE)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "A";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.FARM)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "F";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.MINE_OF_STONE)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "S";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.CAMP)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "C";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.FACTORY)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "T";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.PASTURE)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "P";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.LUMBER_MILL)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "L";
                if (Ground.getGroundByNumber(i).getImprovementType()== ImprovementType.TRADING_POST)
                    showMap[ground.getxLocation() - 1][ground.getyLocation()] = GlobalVariables.ANSI_GREEN + "R";
            }
        }
        showMapRoadAndRailWay(player, showMap);
    }
    private void showMapRoadAndRailWay(Player player,String[][] showMap){
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground = Ground.getGroundByNumber(player.getClearToSeeGrounds().get(i).getNumber());
            if (ground.getRoad()!=null && ground.getRoad().getTurn()<=0){
                showMap[ground.getxLocation() +1 ][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "R";
            }
            if (ground.getRailWay()!=null && ground.getRailWay().getTurn()<=0){
                showMap[ground.getxLocation() +1 ][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "A";
            }
        }
        showMapBonusResource(player, showMap);
    }
    private void showMapBonusResource(Player player, String[][] showMap){
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground=Ground.getGroundByNumber(player.getClearToSeeGrounds().get(i).getNumber());
            for (BonusResource bonusResource:ground.getBonusResource()){
                if (bonusResource==BonusResource.BANANA)
                    showMap[ground.getxLocation()][ground.getyLocation()-5] = GlobalVariables.ANSI_GREEN + "B";
                if (bonusResource==BonusResource.GAZELLE)
                    showMap[ground.getxLocation()][ground.getyLocation()-5] = GlobalVariables.ANSI_GREEN + "G";
                if (bonusResource==BonusResource.SHEEP)
                    showMap[ground.getxLocation()][ground.getyLocation()-5] = GlobalVariables.ANSI_GREEN + "S";
                if (bonusResource==BonusResource.COW)
                    showMap[ground.getxLocation()][ground.getyLocation()-5] = GlobalVariables.ANSI_GREEN + "C";
                if (bonusResource==BonusResource.WHEAT)
                    showMap[ground.getxLocation()][ground.getyLocation()-5] = GlobalVariables.ANSI_GREEN + "W";
            }
        }
        showMapLuxuryResource(player, showMap);
    }
    private void showMapLuxuryResource(Player player, String[][] showMap){
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground=Ground.getGroundByNumber(player.getClearToSeeGrounds().get(i).getNumber());
            for (LuxuryResource luxuryResource:ground.getLuxuryResources()){
               // if (ground.getImprovementType()!=luxuryResource.getImprovementType()) continue;
                if (luxuryResource==LuxuryResource.BEKHOOR)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "B";
                if (luxuryResource==LuxuryResource.COLOR)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "C";
                if (luxuryResource==LuxuryResource.COTTON)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_RESET + "C";
                if (luxuryResource==LuxuryResource.FUR)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "F";
                if (luxuryResource==LuxuryResource.GOLD)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_YELLOW + "G";
                if (luxuryResource==LuxuryResource.IVORY)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "I";
                if (luxuryResource==LuxuryResource.MARBLE)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "M";
                if (luxuryResource==LuxuryResource.SILK)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "S";
                if (luxuryResource==LuxuryResource.SILVER)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_WHITE + "S";
                if (luxuryResource==LuxuryResource.STONE)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_RESET + "S";
                if (luxuryResource==LuxuryResource.SUGAR)
                    showMap[ground.getxLocation()][ground.getyLocation()+5] = GlobalVariables.ANSI_GREEN + "U";
            }
        }
        showMapStrategicResource(player, showMap);
    }
    private void showMapStrategicResource(Player player,String[][] showMap){
        for (int i = 0; i < player.getClearToSeeGrounds().size(); i++) {
            Ground ground=Ground.getGroundByNumber(player.getClearToSeeGrounds().get(i).getNumber());
            for (StrategicResource strategicResource:ground.getStrategicResources()){
                if (!ground.canWeSeeThisStrategicResource()) continue;
                if (strategicResource==StrategicResource.IRON){
                    showMap[ground.getxLocation()+2][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "I";
                }
                if (strategicResource==StrategicResource.HORSE){
                    showMap[ground.getxLocation()+2][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "H";
                }
                if (strategicResource==StrategicResource.COAL){
                    showMap[ground.getxLocation()+2][ground.getyLocation()] = GlobalVariables.ANSI_WHITE + "C";
                }
            }
        }
        showMapUnit(player, showMap);
    }
    private void showMapUnit(Player player, String[][] showMap) {
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            if (player.equals(Player.getAllPlayers().get(i))) continue;
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++) {
                Ground ground = Player.getAllPlayers().get(i).getUnits().get(j).getGround();
                if (player.isThisGroundVisible(ground)) {
                    if (Player.getAllPlayers().get(i).getUnits().get(j) instanceof MilitaryUnit) {
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.ANTITANKGUN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "G";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.ARCHER)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "A";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.WARRIOR)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "W";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.ARTILLERY)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "T";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.CANON)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "C";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.CATAPULT)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "P";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.CAVALRY)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "V";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.CHARIOTARCHER)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "O";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.CROSSBOWMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "B";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.HORSEMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "H";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.INFANTRY)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "I";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.KNIGHT)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "K";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.LANCER)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "L";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.LONGSWORDSMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "l";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.MUSKETMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "M";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.PANZER)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "Z";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.PIKEMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "p";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.RIFLEMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "R";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.SCOUT)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "s";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.SPEARMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "S";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.SWORDSMAN)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "W";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.TANK)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "T";
                        if (Player.getAllPlayers().get(i).getUnits().get(j).getMilitaryType()== MilitaryType.TREBUCHET)
                            showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "t";
                    } else{
                        if (Player.getAllPlayers().get(i).getUnits().get(j) instanceof SettlerUnit) showMap[ground.getxLocation() + 1][ground.getyLocation() + 1] = GlobalVariables.ANSI_RED + "S";
                        else showMap[ground.getxLocation() + 1][ground.getyLocation() + 1] = GlobalVariables.ANSI_RED + "W";
                    }
                }

            }
        }
        int[][] visit = new int[globalVariables.surfaceHeight][globalVariables.surfaceWidth];
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (showMap[i][j] == GlobalVariables.ANSI_BLACK + "█"
                        && showMap[i + 1][j + 1] == GlobalVariables.ANSI_BLACK + "█"
                        && showMap[i - 1][j - 1] == GlobalVariables.ANSI_BLACK + "█") {
                    visit[i][j] = 1;
                }
                if (showMap[i][j] == GlobalVariables.ANSI_BLACK + "█"
                        && showMap[i + 1][j - 1] == GlobalVariables.ANSI_BLACK + "█"
                        && showMap[i - 1][j + 1] == GlobalVariables.ANSI_BLACK + "█") {
                    visit[i][j] = 2;
                }

            }
        }
        showMapSurrounds(player, showMap, visit);
    }

    private void showMapSurrounds(Player player, String[][] showMap, int[][] visit) {
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (Ground.getPixelInWhichGround().containsKey(Ground.PairToInt(i, j))) continue;
                if (visit[i][j] == 1) {
                    showMap[i][j] = GlobalVariables.ANSI_RESET + "\\";
                } else if (visit[i][j] == 2) {
                    showMap[i][j] = GlobalVariables.ANSI_RESET + "/";
                } else if (showMap[i][j] == GlobalVariables.ANSI_BLACK + "█") {
                    showMap[i][j] = GlobalVariables.ANSI_RESET +  "-";
                }

            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (showMap[i - 1][j] == GlobalVariables.ANSI_BLACK + "█" && showMap[i + 1][j] == GlobalVariables.ANSI_BLACK + "█")
                    visit[i][j] = 5;
                if (showMap[i][j - 1] == GlobalVariables.ANSI_BLACK + "█" && showMap[i][j + 1] == GlobalVariables.ANSI_BLACK + "█")
                    visit[i][j] = 5;
            }
        }
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                if (visit[i][j] == 5) {
                    showMap[i][j] = GlobalVariables.ANSI_BLACK + "█";
                }
            }
        }
        showMapCityRange(showMap);
    }
    private void showMapCityRange(String[][] showMap){
        for (int I=0;I<Player.getAllPlayers().size();I++) {
            for (int i = 0; i < Player.getAllPlayers().get(I).getCities().size(); i++) {
                Player player=Player.getAllPlayers().get(I);
                System.out.println(i + " ground " + player.getCities().get(i).getGround().getNumber());
                for (int j = 0; j < player.getCities().get(i).getRangeOfCity().size(); j++) {
                    Ground ground = player.getCities().get(i).getRangeOfCity().get(j);
                    for (int X = ground.getxLocation() - globalVariables.tool6Zelie; X <= ground.getxLocation() + globalVariables.tool6Zelie; X++) {
                        for (int Y = ground.getyLocation() - globalVariables.arz6Zelie; Y <= ground.getyLocation() + globalVariables.arz6Zelie; Y++) {
                            if (X == 0 && Y == 0) continue;

                            Ground newGround = Ground.getGroundByXY(X, Y);
                            if (newGround == null) continue;

                            if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0
                                    || newGround.getyLocation() == globalVariables.surfaceWidth - 1) continue;
                            if (player.getCities().get(i).isThisGroundInThisCityRange(newGround)) continue;
                            if (!player.isThisGroundVisible(ground) && !player.isThisGroundVisible(newGround)) continue;
                            if (ground.getyLocation() == newGround.getyLocation()) {

                                for (int y = ground.getyLocation() - globalVariables.arz6Zelie / 3 - 1;
                                     y <= ground.getyLocation() + globalVariables.arz6Zelie / 3 + 1; y++) {
                                    showMap[(ground.getxLocation() + newGround.getxLocation()) / 2][y] = GlobalVariables.ANSI_RED + "█";
                                }
                            } else {
                                int smallerX = min(ground.getxLocation(), newGround.getxLocation());
                                int biggerX = max(ground.getxLocation(), newGround.getxLocation());
                                int smallerY = min(ground.getyLocation(), newGround.getyLocation());
                                int biggerY = max(ground.getyLocation(), newGround.getyLocation());
                                for (int x = smallerX; x <= biggerX; x++) {
                                    for (int y = smallerY; y <= biggerY; y++) {
                                        if (globalVariables.isEqual(globalVariables.distanceOfTwoPoints(x, y, ground.getxLocation(), ground.getyLocation())
                                                , globalVariables.distanceOfTwoPoints(x, y, newGround.getxLocation(), newGround.getyLocation())) == 1) {
                                            showMap[x][y] = GlobalVariables.ANSI_RED + "█";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Player player=Player.whichPlayerTurnIs();
        showMapRiver(player,showMap);
    }
    private void showMapRiver(Player player, String[][] showMap) {
        for (int i = 0; i < River.getAllRivers().size(); i++) {
            Ground firstGround = River.getAllRivers().get(i).getFirstGround(), secondGround = River.getAllRivers().get(i).getSecondGround();
            if (!player.isThisGroundVisible(firstGround) && !player.isThisGroundVisible(secondGround)) continue;
            if (firstGround.getyLocation() == secondGround.getyLocation()) {

                for (int y = firstGround.getyLocation() - globalVariables.arz6Zelie / 3 - 1;
                     y <= firstGround.getyLocation() + globalVariables.arz6Zelie / 3 + 1; y++) {
                    showMap[(firstGround.getxLocation() + secondGround.getxLocation()) / 2][y] = GlobalVariables.ANSI_BLUE + "█";
                }
            } else {
                int smallerX = min(firstGround.getxLocation(), secondGround.getxLocation());
                int biggerX = max(firstGround.getxLocation(), secondGround.getxLocation());
                int smallerY = min(firstGround.getyLocation(), secondGround.getyLocation());
                int biggerY = max(firstGround.getyLocation(), secondGround.getyLocation());
                for (int x = smallerX; x <= biggerX; x++) {
                    for (int y = smallerY; y <= biggerY; y++) {
                        if (globalVariables.isEqual(globalVariables.distanceOfTwoPoints(x, y, firstGround.getxLocation(), firstGround.getyLocation())
                                , globalVariables.distanceOfTwoPoints(x, y, secondGround.getxLocation(), secondGround.getyLocation())) == 1) {
                            showMap[x][y] = GlobalVariables.ANSI_BLUE + "█";
                        }
                    }
                }
            }

        }

        printMap(showMap, globalVariables);
    }


    private void printMap(String[][] showMap, GlobalVariables globalVariables) {
        for (int i = 1; i < globalVariables.surfaceHeight - 1; i++) {
            for (int j = 1; j < globalVariables.surfaceWidth - 1; j++) {
                System.out.print(showMap[i][j]);
            }
            System.out.println("");
        }
    }

}
