package model;

import java.util.ArrayList;

public class Player {
    public static ArrayList<Player> allPlayers = new ArrayList<>();
    public ArrayList<City> cities = new ArrayList<>();
    public ArrayList<Unit> units = new ArrayList<>();
    public ArrayList<Ground> clearToSeeGrounds = new ArrayList<>();
    public ArrayList<Ground> wasClearedToSeeGrounds = new ArrayList<>();

    public static int counterOfNextRound = 0;
    public User user;
    public boolean isAlive = true;

    public Player (User user){
        this.user=user;
        allPlayers.add(this);
    }

    public void handleClearToSeeGrounds1depth(ArrayList <Ground> clearToSeeGrounds) {
        GlobalVariables globalVariables = new GlobalVariables();
        ArrayList <Ground> newArray = new ArrayList<>();
        for (int i = 0; i < clearToSeeGrounds.size(); i++) {
            Ground ground = clearToSeeGrounds.get(i);
            /// TODO : check kardan Kooh o ina
            for (int x = ground.getxLocation() - globalVariables.arz6Zelie; x <= ground.getxLocation() + globalVariables.arz6Zelie; x++) {
                for (int y = ground.getyLocation() - globalVariables.tool6Zelie; y <= ground.getyLocation() + globalVariables.tool6Zelie; y++) {
                    if (x == 0 && y == 0) continue;

                    Ground newGround = Ground.getGroundByXY(x, y);
                    if (newGround == null) continue;

                    if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0
                            || newGround.getyLocation() == globalVariables.surfaceWidth - 1) continue;

                    int p1 = 0; //TODO change p1 name
                    for (Ground clearToSeeGround : clearToSeeGrounds) {
                        if (clearToSeeGround.equals(newGround)) {
                            p1 = 1;
                            break;
                        }
                    }

                    for (Ground value : newArray) {
                        if (value.equals(newGround)) {
                            p1 = 1;
                            break;
                        }
                    }

                    if (p1 > 0) continue;

                    newArray.add(newGround);
                }
            }
        }

        clearToSeeGrounds.addAll(newArray);
    }

    public void handleClearToSee(){
        this.clearToSeeGrounds=new ArrayList<>();
        for (Unit unit : this.units) {
            this.clearToSeeGrounds.add(unit.getGround());
        }

        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds); //for the second depth
    }

    public static void nextTurn(){
        counterOfNextRound++;
        while (!whichPlayerTurnIs().isAlive) counterOfNextRound++;
    }

    public static Player whichPlayerTurnIs(){
        return allPlayers.get(counterOfNextRound % allPlayers.size());
    }

}
