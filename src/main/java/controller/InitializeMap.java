package controller;

import model.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

import Enum.GroundType;
import Enum.FeatureType;
import Enum.MilitaryType;
import Enum.BonusResource;
public class InitializeMap {
    private ArrayList<User> playerUsers;

    public InitializeMap(ArrayList<User> playerUsers) {
        this.playerUsers = playerUsers;
    }
    public void run(){
        setFirstGroundsForPlayers();
        setRivers();
        setGroundsType();
        setGroundsAdjacent();
        setBonusType();
    }

    private void setGroundsType() {
        Random random = new Random();
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            int rand = random.nextInt(0, 200);
            if (rand < 70) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.DESERT);
            } else if (rand < 140) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.GRASS_PLOT);
            } else if (rand < 150) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.HILL);
            } else if (rand < 160) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.MOUNTAIN);
            } else if (rand < 170) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.OCEAN);
            } else if (rand < 180) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.SNOW);
            } else if (rand < 190) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.TUNDRA);
            } else if (rand < 200) {
                Ground.getGroundByNumber(i).setGroundType(GroundType.PLAIN);
            }
            rand = random.nextInt(0, 200);
            if (rand < 25) {
                Ground.getGroundByNumber(i).setFeatureType(FeatureType.FOREST);
            } else if (rand < 50) {
                Ground.getGroundByNumber(i).setFeatureType(FeatureType.ICE);
            } else if (rand < 75) {
                Ground.getGroundByNumber(i).setFeatureType(FeatureType.JUNGLE);
            } else if (rand < 100) {
                Ground.getGroundByNumber(i).setFeatureType(FeatureType.MARSH);
            } else if (rand < 150) {
                if (Ground.getGroundByNumber(i).getGroundType() == GroundType.DESERT) {
                    Ground.getGroundByNumber(i).setFeatureType(FeatureType.OASIS);
                } else Ground.getGroundByNumber(i).setFeatureType(FeatureType.FOREST);
            } else if (rand < 200) {
                if (Ground.getGroundByNumber(i).containRiver())
                    Ground.getGroundByNumber(i).setFeatureType(FeatureType.MARSH);
                else Ground.getGroundByNumber(i).setFeatureType(FeatureType.JUNGLE);
            }
        }
    }

    private void setRivers() {
        Random random = new Random();
        int numberOfRivers = random.nextInt(0, GlobalVariables.numberOfTiles / 2) + 5;
        for (int i = 0; i < numberOfRivers; i++) {
            int first = random.nextInt(0, GlobalVariables.numberOfTiles) + 1, second = random.nextInt(0, GlobalVariables.numberOfTiles) + 1;
            while (first == second || !River.couldWePutRiverBetweenTheseTwoGround(Ground.getGroundByNumber(first), Ground.getGroundByNumber(second))) {
                first = random.nextInt(0, GlobalVariables.numberOfTiles) + 1;
                second = random.nextInt(0, GlobalVariables.numberOfTiles) + 1;
            }
            River river = new River(Ground.getGroundByNumber(first), Ground.getGroundByNumber(second));
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
            SettlerUnit unMilitaryUnit = new SettlerUnit(ground, player);
            MeleeUnit militaryUnit = new MeleeUnit(ground, player, MilitaryType.WARRIOR);
            player.getUnits().add(militaryUnit);
            player.getUnits().add(unMilitaryUnit);
        }
    }
    private void setGroundsAdjacent(){
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            for (int j=i+1;j<=GlobalVariables.numberOfTiles;j++){
                if (Ground.AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(i),Ground.getGroundByNumber(j))){
                    Ground.getGroundByNumber(i).addGroundToAdjacentGround(Ground.getGroundByNumber(j));
                    Ground.getGroundByNumber(j).addGroundToAdjacentGround(Ground.getGroundByNumber(i));
                }
            }
        }
    }
    private void setBonusType(){
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            Random random = new Random();
           int rand=random.nextInt(0,200);
           if (rand<40){
               if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.BANANA)){
                   System.out.println("yes");
                   Ground.getGroundByNumber(i).addBonusResource(BonusResource.BANANA);
               }
           }
           else if (rand<80){
               if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.COW)){
                   System.out.println("yes");
                   Ground.getGroundByNumber(i).addBonusResource(BonusResource.COW);
               }
           }
           else if (rand<120){
               if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.GAZELLE)){
                   System.out.println("yes");
                   Ground.getGroundByNumber(i).addBonusResource(BonusResource.GAZELLE);
               }
           }
           else if (rand<160){
               if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.SHEEP)){
                   System.out.println("yes");
                   Ground.getGroundByNumber(i).addBonusResource(BonusResource.SHEEP);
               }
           }
           else if (rand<200){
               if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.WHEAT)){
                   System.out.println("yes");
                   Ground.getGroundByNumber(i).addBonusResource(BonusResource.WHEAT);
               }
           }
        }
    }
}
