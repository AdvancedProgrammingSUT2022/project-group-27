package Enum;

import java.util.ArrayList;
import java.util.List;

public enum ImprovementType {
    CAMP(0, 0, 0, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.FOREST)), new ArrayList<>(List.of(LuxuryResource.FUR, LuxuryResource.IVORY)),
            null, new ArrayList<>(List.of(BonusResource.GAZELLE)), 0),
    FARM(1, 0, 0, TechnologyType.AGRICULTURE, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), null, null,
            null, null, 0),
    LUMBER_MILL(0, 1, 0, TechnologyType.MASONRY, null, new ArrayList<>(List.of(FeatureType.FOREST)),
            null, null, null, 0),
    MINE(0, 1, 0, TechnologyType.MINING, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT, GroundType.TUNDRA, GroundType.SNOW, GroundType.HILL)), new ArrayList<>(List.of(FeatureType.FOREST,
            FeatureType.JUNGLE, FeatureType.MARSH)), new ArrayList<>(List.of(LuxuryResource.STONE, LuxuryResource.GOLD, LuxuryResource.SILVER)),
            new ArrayList<>(List.of(StrategicResource.COAL, StrategicResource.IRON)), null, 0),
    PASTURE(0, 0, 0, TechnologyType.ANIMAL_HUSBANDRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.GRASS_PLOT, GroundType.DESERT)), null, null,
            new ArrayList<>(List.of(StrategicResource.HORSE)), new ArrayList<>(List.of(BonusResource.COW, BonusResource.SHEEP)), 0),
    AGRICULTURE(0, 0, 0, TechnologyType.CALENDAR, new ArrayList<>(List.of(GroundType.DESERT,
            GroundType.PLAIN, GroundType.GRASS_PLOT)), new ArrayList<>(List.of(FeatureType.FOREST, FeatureType.JUNGLE, FeatureType.MARSH, FeatureType.WATERSHED)),
            new ArrayList<>(List.of(LuxuryResource.COTTON, LuxuryResource.COLOR, LuxuryResource.BEKHOOR, LuxuryResource.SILK, LuxuryResource.SUGAR)),
            null, new ArrayList<>(List.of(BonusResource.BANANA)), 0),
    MINE_OF_STONE(0, 0, 0, TechnologyType.MASONRY, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.HILL, GroundType.DESERT, GroundType.GRASS_PLOT)), null, new ArrayList<>(List.of(LuxuryResource.MARBLE)),
            null, null, 0),
    TRADING_POST(0, 0, 1, TechnologyType.TRAPPING, new ArrayList<>(List.of(GroundType.TUNDRA, GroundType.PLAIN, GroundType.DESERT,
            GroundType.GRASS_PLOT)), null, null, null, null, 0),
    FACTORY(0, 2, 0, TechnologyType.ENGINEERING, new ArrayList<>(List.of(GroundType.TUNDRA,
            GroundType.PLAIN, GroundType.DESERT, GroundType.GRASS_PLOT, GroundType.SNOW)), null, null, null,
            null, 0);

    public final int food;
    public final int production;
    public final int gold;
    public final TechnologyType technologyTypes;
    public final ArrayList<GroundType> groundTypes;
    public final ArrayList<FeatureType> groundFeatureTypes;
    public final ArrayList<LuxuryResource> luxuryResources;
    public final ArrayList<StrategicResource> strategicResources;
    public final ArrayList<BonusResource> bonusResources;
    public int turn;

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
}
