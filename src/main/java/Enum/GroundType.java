package Enum;

import controller.ProfileController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;
import model.GroundRectangle;

import java.awt.*;

public enum GroundType {

   // new ImagePattern(new Image(ProfileController.class.getResource(ground.getGroundType().getColor()).toExternalForm()))
    DESERT(0, 0, 0, -33, 1, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.desert).toExternalForm()))),
    GRASS_PLOT(2, 0, 0, -33, 1, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.grass).toExternalForm()))),
    HILL(0, 2, 0, 25, 2, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.hill).toExternalForm()))),
    MOUNTAIN(0, 0, 0, 25, 0, true, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.mountain).toExternalForm()))),
    OCEAN(0, 0, 0, 25, 0, true, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.ocean).toExternalForm()))),
    PLAIN(1, 1, 0, -33, 1, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.plainImage).toExternalForm()))),
    SNOW(0, 0, 0, -33, 1, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.snow).toExternalForm()))),
    TUNDRA(1, 0, 0, -33, 1, false, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.tundra).toExternalForm())));

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;

    private final ImagePattern color;

    GroundType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock, ImagePattern color) {
        this.gold = gold;
        this.food = food;
        this.production = production;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
        this.color = color;
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

    public ImagePattern getColor() {
        return color;
    }
}
