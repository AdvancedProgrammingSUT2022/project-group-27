package view.game;

import model.*;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

import Enum.FeatureType;
import Enum.GroundType;

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
                } else showMap[ground.getxLocation() + 2][ground.getyLocation() + 4] = GlobalVariables.ANSI_CYAN + "U";
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
        showMapUnit(player, showMap);
    }

    private void showMapUnit(Player player, String[][] showMap) {
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            if (player.equals(Player.getAllPlayers().get(i))) continue;
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++) {
                Ground ground = Player.getAllPlayers().get(i).getUnits().get(j).getGround();
                if (player.isThisGroundVisible(ground)) {
                    if (Player.getAllPlayers().get(i).getUnits().get(j) instanceof MilitaryUnit) {
                        showMap[ground.getxLocation() + 1][ground.getyLocation() - 1] = GlobalVariables.ANSI_RED + "M";
                    } else
                        showMap[ground.getxLocation() + 1][ground.getyLocation() + 1] = GlobalVariables.ANSI_RED + "U";
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
