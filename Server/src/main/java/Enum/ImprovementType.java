package Enum;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementType {
    CAMP(0, 0, 0, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.FOREST)), 0),
    FARM(1, 0, 0, TechnologyType.AGRICULTURE, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), new ArrayList<>(), 0),
    LUMBER_MILL(0, 1, 0, TechnologyType.MASONRY, new ArrayList<>(),
            new ArrayList<>(List.of(FeatureType.FOREST)), 0),
    MINE(0, 1, 0, TechnologyType.MINING, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT, GroundType.TUNDRA, GroundType.SNOW, GroundType.HILL)),
            new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE, FeatureType.MARSH)), 0),
    PASTURE(0, 0, 0, TechnologyType.ANIMAL_HUSBANDRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.GRASS_PLOT, GroundType.DESERT)), new ArrayList<>(), 0),
    AGRICULTURE(0, 0, 0, TechnologyType.CALENDAR, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE,
            FeatureType.MARSH, FeatureType.WATERSHED)), 0),
    MINE_OF_STONE(0, 0, 0, TechnologyType.MASONRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.DESERT, GroundType.GRASS_PLOT)), new ArrayList<>(), 0),
    TRADING_POST(0, 0, 1, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN,
            GroundType.DESERT, GroundType.GRASS_PLOT)), new ArrayList<>(), 0),
    FACTORY(0, 2, 0, TechnologyType.ENGINEERING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT, GroundType.SNOW)), new ArrayList<>(), 0);

    private final int food;
    private final int production;
    private final int gold;
    private final TechnologyType technologyTypes;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private int turn;

    ImprovementType(int food, int production, int gold, TechnologyType technologyTypes, ArrayList<GroundType> groundTypes,
                    ArrayList<FeatureType> groundFeatureTypes, int turn) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.technologyTypes = technologyTypes;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.turn = turn; //TODO... set the correct turn for each one
    }

    public void setTurn(int turn) {
        this.turn = turn;
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

    public TechnologyType getTechnologyTypes() {
        return technologyTypes;
    }

    public ArrayList<GroundType> getGroundTypes() {
        return groundTypes;
    }

    public ArrayList<FeatureType> getGroundFeatureTypes() {
        return groundFeatureTypes;
    }

    public int getTurn() {
        return turn;
    }
}
