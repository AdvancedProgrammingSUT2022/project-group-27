package Enum;

import model.GlobalVariables;

public enum BuildingsType {
    BARRACKS(80, 1, TechnologyType.BRONZE_WORKING),
    GRANARY(100, 1, TechnologyType.POTTERY),
    LIBRARY(80, 1, TechnologyType.WRITING),
    MONUMENT(60, 1, null),
    WALLS(100, 1, TechnologyType.MASONRY),
    WATER_MILL(120, 2, TechnologyType.THE_WHEEL),
    ARMORY(130, 3, TechnologyType.IRON_WORKING),
    BURIAL_TOMB(120, 0, TechnologyType.PHILOSOPHY),
    CIRCUS(150, 3, TechnologyType.HORSEBACK_RIDING),
    COLOSSEUM(150, 3, TechnologyType.CONSTRUCTION),
    COURTHOUSE(200, 5, TechnologyType.MATHEMATICS),
    STABLE(100, 1, TechnologyType.HORSEBACK_RIDING),
    TEMPLE(120, 2, TechnologyType.PHILOSOPHY),
    CASTLE(200, 3, TechnologyType.CHIVALRY),
    FORGE(150, 2, TechnologyType.METAL_CASTING),
    GARDEN(120, 2, TechnologyType.THEOLOGY),
    MARKET(120, 0, TechnologyType.CURRENCY),
    MINT(120, 0, TechnologyType.CURRENCY),
    MONASTERY(120, 2, TechnologyType.THEOLOGY),
    UNIVERSITY(200, 3, TechnologyType.EDUCATION),
    WORKSHOP(100, 2, TechnologyType.METAL_CASTING),
    BANK(220, 0, TechnologyType.BANKING),
    MILITARY_ACADEMY(350, 3, TechnologyType.MILITARY_SCIENCE),
    MUSEUM(350, 3, TechnologyType.ARCHAEOLOGY),
    OPERA_HOUSE(220, 3, TechnologyType.ACOUSTICS),
    PUBLIC_SCHOOL(350, 3, TechnologyType.SCIENTIFIC_THEORY),
    SATRAPS_COURT(220, 0, TechnologyType.BANKING),
    THEATER(300, 5, TechnologyType.PRINTING_PRESS),
    WINDMILL(180, 2, TechnologyType.ECONOMICS),
    ARSENAL(350, 3, TechnologyType.RAILROAD),
    BROADCAST_TOWER(600, 3, TechnologyType.RADIO),
    FACTORY(300, 3, TechnologyType.STEAM_POWER),
    HOSPITAL(400, 2, TechnologyType.BIOLOGY),
    MILITARY_BASE(450, 4, TechnologyType.TELEGRAPH),
    STOCK_EXCHANGE(650, 0, TechnologyType.ELECTRICITY);

    private final int cost;
    private final int maintenance;
    private final TechnologyType technologyType;

    BuildingsType(int cost, int maintenance, TechnologyType technologyType) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.technologyType = technologyType;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }
}
