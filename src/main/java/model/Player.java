package model;

import java.util.ArrayList;

public class Player {
    public static ArrayList<Player> allPlayers;
    public ArrayList<City> cities;
    public ArrayList<Ground> clearToSeeGrounds;
    public ArrayList<Ground> wasCleardToSeeGrounds;


    public void handleClearToSeeGrounds1depth(ArrayList <Ground> clearToSeeGrounds) {
        GlobalVariables globalVariables = new GlobalVariables();
        ArrayList <Ground> newArray=new ArrayList<>();
        for (int i = 0; i < clearToSeeGrounds.size(); i++) {
            Ground ground = clearToSeeGrounds.get(i);
            /// TODO : check kardan Kooh o ina
            for (int x = ground.getxLocation() - globalVariables.arz6Zelie; x <= ground.getxLocation() + globalVariables.arz6Zelie; x++) {
                for (int y = ground.getyLocation() - globalVariables.tool6Zelie; y <= ground.getyLocation() + globalVariables.tool6Zelie; y++) {
                    if (x == 0 && y == 0) continue;
                    Ground newGround = Ground.getGroundByXY(x, y);
                    if (newGround == null) continue;
                    if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0 || newGround.getyLocation() == globalVariables.surfaceWidth - 1) {
                        continue;
                    }
                    int p1=0;
                    for (int j=0;j<clearToSeeGrounds.size();j++){
                        if (clearToSeeGrounds.get(j).equals(newGround)) p1=1;
                    }
                    for (int j=0;j<newArray.size();j++){
                        if (newArray.get(j).equals(newGround)) p1=1;
                    }
                    if (p1>0) continue;
                    newArray.add(newGround);
                }
            }
        }
        for (int i=0;i<newArray.size();i++){
            clearToSeeGrounds.add(newArray.get(i));
        }
    }
    public void handleClearToSee(){
        this.clearToSeeGrounds=new ArrayList<>();
        for (int i=0;i<cities.size();i++){
            for (int j=0;j<cities.get(i).groundsInCity.size();j++){
                clearToSeeGrounds.add(cities.get(i).groundsInCity.get(j));
            }
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
    }

}
