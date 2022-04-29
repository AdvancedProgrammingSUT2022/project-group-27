package Enum;

import java.util.ArrayList;
import java.util.List;

public enum BonusResource {
    BANANA(1, 0, 0, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST))),
    COW(1, 0, 0, new ArrayList<>(List.of(GroundType.GRASS_PLOT)), new ArrayList<>()),
    GAZELLE(1, 0, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.HILL)),
            new ArrayList<>(List.of(FeatureType.FOREST))),
    SHEEP(2, 0, 0, new ArrayList<>(List.of(GroundType.HILL, GroundType.GRASS_PLOT, GroundType.DESERT,
            GroundType.PLAIN)), new ArrayList<>()),
    WHEAT(1, 0, 0, new ArrayList<>(List.of(GroundType.PLAIN)), new ArrayList<>(List.of(FeatureType.WATERSHED)));

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;

    BonusResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes) {
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
