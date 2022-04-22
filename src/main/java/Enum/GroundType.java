package Enum;

public enum GroundType {
    DESERT(0, 0, 0, -33, 1, false),
    GRASS_PLOT(2, 0, 0, -33, 1, false),
    HILL(0, 2, 0, 25, 2, false),
    MOUNTAIN(0, 0, 0, 25, 0, true),
    OCEAN(0, 0, 0, 25, 0, true),
    PLAIN(1, 1, 0, -33, 1, false),
    SNOW(0, 0, 0, -33, 1, false),
    TUNDRA(1, 0, 0, -33, 1, false);

    public final int food;
    public final int production;
    public final int gold;
    public final int combatCoefficient;
    public final int movementCost;
    public final boolean isBlock;

    GroundType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock) {
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
    }
}
