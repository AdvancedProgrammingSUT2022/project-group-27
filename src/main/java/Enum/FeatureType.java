package Enum;

public enum FeatureType {
    WATERSHED(2, 0, 0, -33, 1, false),
    FOREST(1, 1, 0, 25, 2, false),
    ICE(0, 0, 0, 0, 0, true),
    JUNGLE(1, -1, 0, 25, 2, false),
    MARSH(-1, 0, 0, -33, 2, false),
    OASIS(3, 0, 1, -33, 1, false),
    RIVER(0, 0, 1, 0, -1, false);

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;

    FeatureType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
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
}
