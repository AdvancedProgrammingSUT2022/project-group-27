package controller;

import model.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

import Enum.GroundType;
import Enum.FeatureType;
import Enum.MilitaryType;
import Enum.BonusResource;
import Enum.LuxuryResource;
import Enum.StrategicResource;

public class InitializeMap {
    private int seed=0;
    private ArrayList<User> playerUsers;

    public InitializeMap(ArrayList<User> playerUsers,int seed) {
        this.seed=seed;
        this.playerUsers = playerUsers;
    }

    public void run() {
        setGroundsType();
        setFirstGroundsForPlayers();
        setRivers();
        setGroundsAdjacent();
        setBonusType();
        setLuxuryType();
        setStrategicType();
    }

    private void setGroundsType() {
        Random random = new Random(seed);
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
            rand = random.nextInt(0, 300);
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
            } else {
                Ground.getGroundByNumber(i).setFeatureType(FeatureType.NOTHING);
            }
        }
    }

    private void setRivers() {
        Random random = new Random(seed);
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
        Random rand = new Random(seed);
        for (User playerUser : playerUsers) {
            int idStartGround = rand.nextInt(GlobalVariables.numberOfTiles) + 1;
            while (!Ground.getGroundByNumber(idStartGround).isFreeOfMilitaryUnit() || Ground.getGroundByNumber(idStartGround).getGroundType()==GroundType.OCEAN
            || Ground.getGroundByNumber(idStartGround).getGroundType()==GroundType.HILL || Ground.getGroundByNumber(idStartGround).getGroundType()==GroundType.MOUNTAIN) {
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

    private void setGroundsAdjacent() {
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            for (int j = i + 1; j <= GlobalVariables.numberOfTiles; j++) {
                if (Ground.AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(i), Ground.getGroundByNumber(j))) {
                    Ground.getGroundByNumber(i).addGroundToAdjacentGround(Ground.getGroundByNumber(j));
                    Ground.getGroundByNumber(j).addGroundToAdjacentGround(Ground.getGroundByNumber(i));
                }
            }
        }
    }

    private void setBonusType() {
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            Random random = new Random(seed);
            int rand = random.nextInt(0, 200);
            if (rand < 40) {
                if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.BANANA)) {
                    Ground.getGroundByNumber(i).addBonusResource(BonusResource.BANANA);
                }
            } else if (rand < 80) {
                if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.COW)) {
                    Ground.getGroundByNumber(i).addBonusResource(BonusResource.COW);
                }
            } else if (rand < 120) {
                if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.GAZELLE)) {
                    Ground.getGroundByNumber(i).addBonusResource(BonusResource.GAZELLE);
                }
            } else if (rand < 160) {
                if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.SHEEP)) {
                    Ground.getGroundByNumber(i).addBonusResource(BonusResource.SHEEP);
                }
            } else if (rand < 200) {
                if (Ground.getGroundByNumber(i).canWeAddThisBonusResourceToThisGround(BonusResource.WHEAT)) {
                    Ground.getGroundByNumber(i).addBonusResource(BonusResource.WHEAT);
                }
            }
            if (Ground.getGroundByNumber(i).getBonusResource().size() == 0)
                Ground.getGroundByNumber(i).addBonusResource(BonusResource.NOTHING);
        }
    }

    private void setLuxuryType() {
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            Random random = new Random(seed);
            int rand = random.nextInt(0, 220);
            if (rand < 20) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.SUGAR)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.SUGAR);
                }
            } else if (rand < 40) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.SILK)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.SILK);
                }
            } else if (rand < 60) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.SILVER)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.SILVER);
                }
            } else if (rand < 80) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.MARBLE)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.MARBLE);
                }
            } else if (rand < 100) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.IVORY)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.IVORY);
                }
            } else if (rand < 120) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.GOLD)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.GOLD);
                }
            } else if (rand < 140) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.FUR)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.FUR);
                }
            } else if (rand < 160) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.COTTON)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.COTTON);
                }
            } else if (rand < 180) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.COLOR)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.COLOR);
                }
            } else if (rand < 200) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.STONE)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.STONE);
                }
            } else if (rand < 220) {
                if (Ground.getGroundByNumber(i).canWeAddThisLuxuryResourceToThisGround(LuxuryResource.BEKHOOR)) {
                    Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.BEKHOOR);
                }
            }
            if (Ground.getGroundByNumber(i).getLuxuryResources().size() == 0)
                Ground.getGroundByNumber(i).addLuxuryResource(LuxuryResource.NOTHING);
        }
    }
    private void setStrategicType(){
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            Random random = new Random(seed);
            int rand = random.nextInt(0, 150);
            if (rand<50) {
                if (Ground.getGroundByNumber(i).canWeAddThisStrategicResourceToThisGround(StrategicResource.HORSE)) {
                    Ground.getGroundByNumber(i).addStrategicResource(StrategicResource.HORSE);
                }
            }
            else if (rand < 100) {
                if (Ground.getGroundByNumber(i).canWeAddThisStrategicResourceToThisGround(StrategicResource.IRON)) {
                    Ground.getGroundByNumber(i).addStrategicResource(StrategicResource.IRON);
                }
            }
            else if (rand < 150) {
                if (Ground.getGroundByNumber(i).canWeAddThisStrategicResourceToThisGround(StrategicResource.COAL)) {
                    Ground.getGroundByNumber(i).addStrategicResource(StrategicResource.COAL);
                }
            }
            else if (Ground.getGroundByNumber(i).getStrategicResources().size()==0){
                Ground.getGroundByNumber(i).addStrategicResource(StrategicResource.NOTHING);
            }

        }
    }
}
