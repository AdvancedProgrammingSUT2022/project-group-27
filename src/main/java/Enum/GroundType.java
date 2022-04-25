package Enum;

public enum GroundType {
    DESERT(0, 0, 0, -33, 1, false,"\u001B[33m"),
    GRASS_PLOT(2, 0, 0, -33, 1, false,"\u001B[32m"),
    HILL(0, 2, 0, 25, 2, false,"\u001B[33m"),
    MOUNTAIN(0, 0, 0, 25, 0, true,"\u001B[33m"),
    OCEAN(0, 0, 0, 25, 0, true,"\u001B[36m"),
    PLAIN(1, 1, 0, -33, 1, false,"\u001B[36m"),
    SNOW(0, 0, 0, -33, 1, false,"\u001B[36m"),
    TUNDRA(1, 0, 0, -33, 1, false,"\u001B[36m");

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;

    private final String color;

    GroundType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock,String color) {
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
        this.color=color;
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

    public int getCombatCoefficient() {
        return combatCoefficient;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public String getColor() {
        return color;
    }
}
