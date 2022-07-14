package Enum;

import java.util.ArrayList;
import java.util.List;

public enum LuxuryResource {
    COTTON(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT)),
            new ArrayList<>(), ImprovementType.AGRICULTURE),
    COLOR(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE)),
            ImprovementType.AGRICULTURE),
    FUR(0, 0, 2, new ArrayList<>(List.of(GroundType.TUNDRA)), new ArrayList<>(List.of(FeatureType.FOREST)),
            ImprovementType.CAMP),
    STONE(0, 0, 3, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.GRASS_PLOT, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.JUNGLE)), ImprovementType.MINE),
    GOLD(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT,
            GroundType.HILL)), new ArrayList<>(), ImprovementType.MINE),
    BEKHOOR(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT)), new ArrayList<>(),
            ImprovementType.AGRICULTURE),
    IVORY(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN)), new ArrayList<>(), ImprovementType.CAMP),
    MARBLE(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT,
            GroundType.HILL, GroundType.TUNDRA)), new ArrayList<>(), ImprovementType.MINE_OF_STONE),
    SILK(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST)), ImprovementType.AGRICULTURE),
    SILVER(0, 0, 2, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.DESERT, GroundType.HILL)),
            new ArrayList<>(), ImprovementType.MINE),
    SUGAR(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.WATERSHED, FeatureType.MARSH)),
            ImprovementType.AGRICULTURE),
    NOTHING(0,0,0,new ArrayList<>(),new ArrayList<>(),null);

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private final ImprovementType improvementType;

    LuxuryResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes,
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

    @Override
    public String toString() {
        return name();
    }
}
