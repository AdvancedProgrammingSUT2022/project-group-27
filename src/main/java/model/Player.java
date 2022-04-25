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
            for (int x = ground.getxLocation() - globalVariables.tool6Zelie; x <= ground.getxLocation() + globalVariables.tool6Zelie; x++) {
                for (int y = ground.getyLocation() - globalVariables.arz6Zelie; y <= ground.getyLocation() + globalVariables.arz6Zelie; y++) {
                    if (x == 0 && y == 0) continue;

                    Ground newGround = Ground.getGroundByXY(x, y);
                    if (newGround == null) continue;

                    if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0
                            || newGround.getyLocation() == globalVariables.surfaceWidth - 1) continue;

                    boolean shouldWeAddThisGround = false;
                    for (Ground clearToSeeGround : clearToSeeGrounds) {
                        if (clearToSeeGround.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    for (Ground value : newArray) {
                        if (value.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    if (shouldWeAddThisGround) continue;

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
    public void addGroundToVisitedGround(Ground ground){
        boolean exist=false;
        for (int i=0;i<wasClearedToSeeGrounds.size();i++){
            if (wasClearedToSeeGrounds.get(i).number==ground.number) exist=true;
        }
        if (exist) return ;
        wasClearedToSeeGrounds.add(ground);
    }

    public void moveUnitFromThisPlayerGroundsToAnotherGround(Ground firstGround,Ground secondGround){
        ///TODO which Type Of Unit must be asked
        ///TODO error sentence
        for (int i=0;i<units.size();i++){
            if (units.get(i).ground.number==firstGround.number){
                units.get(i).moveUnitToAdjacentGround(secondGround);
            }
        }
    }
    public boolean isThisGroundVisible(Ground ground){
        for (int i=0;i<clearToSeeGrounds.size();i++){
            if (clearToSeeGrounds.get(i).number==ground.number) return true;
        }
        return false;
    }
}
