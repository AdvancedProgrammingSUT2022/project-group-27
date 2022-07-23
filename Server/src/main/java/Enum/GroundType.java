package Enum;

import controller.ProfileController;
import model.GlobalVariables;

import java.awt.*;

public enum GroundType {

   // new ImagePattern(new Image(ProfileController.class.getResource(ground.getGroundType().getColor()).toExternalForm()))
    DESERT(0, 0, 0, -33, 1, false),
    GRASS_PLOT(2, 0, 0, -33, 1, false),
    HILL(0, 2, 0, 25, 2, false),
    MOUNTAIN(0, 0, 0, 25, 0, true),
    OCEAN(0, 0, 0, 25, 0, true),
    PLAIN(1, 1, 0, -33, 1, false),
    SNOW(0, 0, 0, -33, 1, false),
    TUNDRA(1, 0, 0, -33, 1, false);

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;



    GroundType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock) {
        this.gold = gold;
        this.food = food;
        this.production = production;
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
