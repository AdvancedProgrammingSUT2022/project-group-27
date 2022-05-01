package Enum;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementType {
    CAMP(0, 0, 0, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.FOREST)),
            new ArrayList<>(List.of(LuxuryResource.FUR, LuxuryResource.IVORY)), new ArrayList<>(),
            new ArrayList<>(List.of(BonusResource.GAZELLE)), 0),
    FARM(1, 0, 0, TechnologyType.AGRICULTURE, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0),
    LUMBER_MILL(0, 1, 0, TechnologyType.MASONRY, new ArrayList<>(), new ArrayList<>(List.of(FeatureType.FOREST)),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0),
    MINE(0, 1, 0, TechnologyType.MINING, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT, GroundType.TUNDRA, GroundType.SNOW, GroundType.HILL)),
            new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE, FeatureType.MARSH)),
            new ArrayList<>(List.of(LuxuryResource.STONE, LuxuryResource.GOLD, LuxuryResource.SILVER)),
            new ArrayList<>(List.of(StrategicResource.COAL, StrategicResource.IRON)), new ArrayList<>(), 0),
    PASTURE(0, 0, 0, TechnologyType.ANIMAL_HUSBANDRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.GRASS_PLOT, GroundType.DESERT)), new ArrayList<>(), new ArrayList<>(),
            new ArrayList<>(List.of(StrategicResource.HORSE)), new ArrayList<>(List.of(BonusResource.COW, BonusResource.SHEEP)), 0),
    AGRICULTURE(0, 0, 0, TechnologyType.CALENDAR, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE,
            FeatureType.MARSH, FeatureType.WATERSHED)), new ArrayList<>(List.of(LuxuryResource.COTTON, LuxuryResource.COLOR,
            LuxuryResource.BEKHOOR, LuxuryResource.SILK, LuxuryResource.SUGAR)), new ArrayList<>(),
            new ArrayList<>(List.of(BonusResource.BANANA)), 0),
    MINE_OF_STONE(0, 0, 0, TechnologyType.MASONRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.DESERT, GroundType.GRASS_PLOT)), new ArrayList<>(),
            new ArrayList<>(List.of(LuxuryResource.MARBLE)), new ArrayList<>(), new ArrayList<>(), 0),
    TRADING_POST(0, 0, 1, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN,
            GroundType.DESERT, GroundType.GRASS_PLOT)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0),
    FACTORY(0, 2, 0, TechnologyType.ENGINEERING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT, GroundType.SNOW)), new ArrayList<>(), new ArrayList<>(),
            new ArrayList<>(), new ArrayList<>(), 0);

    private final int food;
    private final int production;
    private final int gold;
    private final TechnologyType technologyTypes;
    private final ArrayList<GroundType> groundTypes;
    private final ArrayList<FeatureType> groundFeatureTypes;
    private final ArrayList<LuxuryResource> luxuryResources;
    private final ArrayList<StrategicResource> strategicResources;
    private final ArrayList<BonusResource> bonusResources;
    private int turn;

    ImprovementType(int food, int production, int gold, TechnologyType technologyTypes, ArrayList<GroundType> groundTypes,
                    ArrayList<FeatureType> groundFeatureTypes, ArrayList<LuxuryResource> luxuryResources,
                    ArrayList<StrategicResource> strategicResources, ArrayList<BonusResource> bonusResources, int turn) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.technologyTypes = technologyTypes;
        this.groundTypes = groundTypes;
        this.groundFeatureTypes = groundFeatureTypes;
        this.luxuryResources = luxuryResources;
        this.strategicResources = strategicResources;
        this.bonusResources = bonusResources;
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

    public ArrayList<LuxuryResource> getLuxuryResources() {
        return luxuryResources;
    }

    public ArrayList<StrategicResource> getStrategicResources() {
        return strategicResources;
    }

    public ArrayList<BonusResource> getBonusResources() {
        return bonusResources;
    }

    public int getTurn() {
        return turn;
    }
    public void decreaseTurn(int amount){
        turn-=amount;
    }
}
