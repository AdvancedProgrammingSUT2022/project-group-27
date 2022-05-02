package controller;

import model.Ground;
import model.Player;
import Enum.*;

public class ImprovementSettingController {
    public static boolean canWeAddThisImprovement(ImprovementType improvementType, Ground ground){
        Player player=Player.whichPlayerTurnIs();
        if (improvementType == ImprovementType.FARM){
            if (ground.getFeatureType() == FeatureType.FOREST){
                if (!player.doWeHaveThisTechnology(TechnologyType.MINING)){
                    return false;
                }

            }
            if (ground.getFeatureType() == FeatureType.JUNGLE){
                if (!player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)) {
                    return false;
                }
            }
            if (ground.getFeatureType() == FeatureType.MARSH){
                if (!player.doWeHaveThisTechnology(TechnologyType.MASONRY)) {
                    return false;
                }
            }
        }
        if (improvementType== ImprovementType.MINE){
            if (ground.getFeatureType() == FeatureType.JUNGLE){
                if (!player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)) {
                    return false;
                }
            }
            if (ground.getFeatureType() == FeatureType.MARSH){
                if (!player.doWeHaveThisTechnology(TechnologyType.MASONRY)) {
                    return false;
                }
            }
        }
        if (!player.doWeHaveThisTechnology(improvementType.getTechnologyTypes())) return false;
        for (int i=0;i<improvementType.getGroundTypes().size();i++){
            if (ground.getGroundType() == improvementType.getGroundTypes().get(i)) return true;
        }
        for (int i=0;i<improvementType.getGroundFeatureTypes().size();i++){
            if (ground.getFeatureType() == improvementType.getGroundFeatureTypes().get(i)) return true;
        }

        return false;
    }
}
