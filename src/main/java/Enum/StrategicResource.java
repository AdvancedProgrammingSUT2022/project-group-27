package Enum;

import java.util.ArrayList;
import java.util.List;

public enum StrategicResource {
    COAL(0, 1, 0, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.HILL, GroundType.GRASS_PLOT)), null,
            ImprovementType.MINE, TechnologyType.SCIENTIFIC_THEORY),
    HORSE(0, 1, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.GRASS_PLOT)), null,
            ImprovementType.PASTURE, TechnologyType.ANIMAL_HUSBANDRY),
    IRON(0, 1, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.HILL, GroundType.GRASS_PLOT, GroundType.SNOW)), null, ImprovementType.MINE, TechnologyType.IRON_WORKING);

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private final ImprovementType improvement;
    private final TechnologyType technology;

    StrategicResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes, ImprovementType improvement,
                      TechnologyType technology) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.improvement = improvement;
        this.technology = technology;
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

    public TechnologyType getTechnology() {
        return technology;
    }
}
