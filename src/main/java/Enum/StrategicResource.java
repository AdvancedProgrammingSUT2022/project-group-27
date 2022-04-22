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

    public final int food;
    public final int production;
    public final int gold;
    public final ArrayList<GroundType> groundTypes;
    public final ArrayList<FeatureType> groundFeatureTypes;
    public final ImprovementType improvement;
    public final TechnologyType technology;

    StrategicResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, ArrayList<FeatureType> groundFeatureTypes,ImprovementType improvement,
                      TechnologyType technology) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.improvement = improvement;
        this.technology = technology;
    }
}
