package Enum;

import controller.ProfileController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;

public enum FeatureType {
    NOTHING(0,0,0,0,1,false,0,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.desert).toExternalForm()))),
    WATERSHED(2, 0, 0, -33, 1, false,6,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.WATERSHED).toExternalForm()))),
    FOREST(1, 1, 0, 25, 2, false,4,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.FOREST).toExternalForm()))),
    ICE(0, 0, 0, 0, 0, true,6,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.ICE).toExternalForm()))),
    JUNGLE(1, -1, 0, 25, 2, false,7,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.JUNGLE).toExternalForm()))),
    MARSH(-1, 0, 0, -33, 2, false,4,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.MARSH).toExternalForm()))),
    OASIS(3, 0, 1, -33, 1, false,6,new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.OASIS).toExternalForm())));

    private final int food;
    private final int production;
    private final int gold;
    private final int combatCoefficient;
    private final int movementCost;
    private final boolean isBlock;

    private final int turn;
    private final ImagePattern photo;

    FeatureType(int food, int production, int gold, int combatCoefficient, int movementCost, boolean isBlock, int turn, ImagePattern photo) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatCoefficient = combatCoefficient;
        this.movementCost = movementCost;
        this.isBlock = isBlock;
        this.turn=turn;
        this.photo=photo;
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

    public ImagePattern getPhoto() {
        return photo;
    }
}
