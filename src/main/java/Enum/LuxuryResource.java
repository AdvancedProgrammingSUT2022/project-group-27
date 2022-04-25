package Enum;

import java.util.ArrayList;
import java.util.List;

public enum LuxuryResource {
    COTTON(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT)), null,
            ImprovementType.AGRICULTURE),
    COLOR(0, 0, 2, null, new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE)), ImprovementType.AGRICULTURE),
    FUR(0, 0, 2, new ArrayList<>(List.of(GroundType.TUNDRA)), new ArrayList<>(List.of(FeatureType.FOREST)), ImprovementType.CAMP),
    STONE(0, 0, 3, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.GRASS_PLOT, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.JUNGLE)), ImprovementType.MINE),
    GOLD(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT, GroundType.HILL)),
            null, ImprovementType.MINE),
    BEKHOOR(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT)), null, ImprovementType.AGRICULTURE),
    IVORY(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN)), null, ImprovementType.CAMP),
    MARBLE(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT,
            GroundType.HILL, GroundType.TUNDRA)), null, ImprovementType.MINE_OF_STONE),
    SILK(0, 0, 2,  null, new ArrayList<>(List.of(FeatureType.FOREST)), ImprovementType.AGRICULTURE),
    SILVER(0, 0, 2,  new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.DESERT, GroundType.HILL)), null, ImprovementType.MINE),
    SUGAR(0, 0, 2, null, new ArrayList<>(List.of(FeatureType.WATERSHED, FeatureType.MARSH)), ImprovementType.AGRICULTURE);

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private final ImprovementType improvement;

    LuxuryResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes, ImprovementType improvement) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.improvement = improvement;
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

    public ImprovementType getImprovement() {
        return improvement;
    }
}
