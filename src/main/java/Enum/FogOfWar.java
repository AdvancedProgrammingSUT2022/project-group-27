package Enum;

import controller.ProfileController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;

public enum FogOfWar {
    FOG(new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.FOG).toExternalForm())));
    private final ImagePattern color;
    FogOfWar(ImagePattern color){
        this.color=color;
    }

    public ImagePattern getColor() {
        return color;
    }
}
