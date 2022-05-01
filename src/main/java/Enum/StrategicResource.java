package Enum;

import java.util.ArrayList;
import java.util.List;

public enum StrategicResource {
    COAL(0, 1, 0, new ArrayList<>(List.of(GroundType.PLAIN, GroundType.HILL, GroundType.GRASS_PLOT)),
            TechnologyType.SCIENTIFIC_THEORY, ImprovementType.MINE),
    HORSE(0, 1, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.GRASS_PLOT)),
            TechnologyType.ANIMAL_HUSBANDRY, ImprovementType.PASTURE),
    IRON(0, 1, 0, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.HILL, GroundType.GRASS_PLOT, GroundType.SNOW)), TechnologyType.IRON_WORKING, ImprovementType.MINE);

    private final int food;
    private final int production;
    private final int gold;
    private final ArrayList<GroundType> groundTypes;
    private final TechnologyType technology;
    private final ImprovementType improvementType;

    StrategicResource(int food, int production, int gold, ArrayList<GroundType> groundTypes, TechnologyType technology,
                      ImprovementType improvementType) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.groundTypes = groundTypes;
        this.technology = technology;
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

    public TechnologyType getTechnology() {
        return technology;
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    @Override
    public String toString() {
        String name;
        if (this == StrategicResource.HORSE) name = "Horse";
        else if (this == StrategicResource.IRON) name = "Iron";
        else name = "Coal";
        return name;
    }
}
