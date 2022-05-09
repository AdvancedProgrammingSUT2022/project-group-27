package Enum;

import model.Improvement;

import java.util.ArrayList;
import java.util.List;

public enum BonusResource {
    BANANA(1, 0, 0, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST)), ImprovementType.AGRICULTURE),
    COW(1, 0, 0, new ArrayList<>(List.of(GroundType.GRASS_PLOT)), new ArrayList<>(), ImprovementType.PASTURE),
    GAZELLE(1, 0, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.HILL)),
            new ArrayList<>(List.of(FeatureType.FOREST)), ImprovementType.CAMP),
    SHEEP(2, 0, 0, new ArrayList<>(List.of(GroundType.HILL, GroundType.GRASS_PLOT, GroundType.DESERT,
            GroundType.PLAIN)), new ArrayList<>(), ImprovementType.PASTURE),
    WHEAT(1, 0, 0, new ArrayList<>(List.of(GroundType.PLAIN)), new ArrayList<>(List.of(FeatureType.WATERSHED)),
            ImprovementType.FARM),
    NOTHING(0,0,0,new ArrayList<>(),new ArrayList<>(), null);
    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private final ImprovementType improvementType;

    BonusResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes,
                  ImprovementType improvementType) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.improvementType = improvementType;
    }

    public int getFood() {
        return food;
    }

    public int getProduction() {
        return production;
    }

    public int getGold() {
        return gold;
    }

    public ArrayList<GroundType> getGroundTypes() {
        return groundTypes;
    }

    public ArrayList<FeatureType> getGroundFeatureTypes() {
        return groundFeatureTypes;
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }
}
