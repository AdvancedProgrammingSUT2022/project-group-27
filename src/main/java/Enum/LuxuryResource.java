package Enum;

import java.util.ArrayList;
import java.util.List;

public enum LuxuryResource {
    COTTON(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT)),
            new ArrayList<>()),
    COLOR(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE))),
    FUR(0, 0, 2, new ArrayList<>(List.of(GroundType.TUNDRA)), new ArrayList<>(List.of(FeatureType.FOREST))),
    STONE(0, 0, 3, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.GRASS_PLOT, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.JUNGLE))),
    GOLD(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT,
            GroundType.HILL)), new ArrayList<>()),
    BEKHOOR(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT)), new ArrayList<>()),
    IVORY(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN)), new ArrayList<>()),
    MARBLE(0, 0, 2, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT,
            GroundType.HILL, GroundType.TUNDRA)), new ArrayList<>()),
    SILK(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST))),
    SILVER(0, 0, 2, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.DESERT, GroundType.HILL)), new ArrayList<>()),
    SUGAR(0, 0, 2, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.WATERSHED, FeatureType.MARSH)));

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;

    LuxuryResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
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
}
