package controller;

import model.GlobalVariables;
import model.Ground;
import model.Pair;

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

    private void createMap(){
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
}
