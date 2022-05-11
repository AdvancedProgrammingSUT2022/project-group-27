package Enum;

public enum FeatureType {
    NOTHING(0,0,0,0,1,false,0),
    WATERSHED(2, 0, 0, -33, 1, false,6),
    FOREST(1, 1, 0, 25, 2, false,4),
    ICE(0, 0, 0, 0, 0, true,6),
    JUNGLE(1, -1, 0, 25, 2, false,7),
    MARSH(-1, 0, 0, -33, 2, false,4),
    OASIS(3, 0, 1, -33, 1, false,6);

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;

    private final int turn;

    FeatureType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock,int turn) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
        this.turn=turn;
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

    public int getTurn() {
        return turn;
    }
}
