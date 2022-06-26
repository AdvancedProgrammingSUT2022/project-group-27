package model;

import controller.ProfileController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import java.awt.*;

public class GroundRectangle extends Polygon {
    private Ground ground;
    public GroundRectangle (Ground ground,double x,double y){
        this.ground=ground;
        this.getPoints().addAll(new Double[]{
                Double.valueOf(y- GlobalVariables.tool),x,
                Double.valueOf(y-GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool),x,
                Double.valueOf(y+GlobalVariables.tool/2),x+GlobalVariables.arz,
                Double.valueOf(y-GlobalVariables.tool/2),x+GlobalVariables.arz,
        });
        this.update();
    }
    public void update(){
        Image image = new Image(ProfileController.class.getResource(ground.getGroundType().getColor()).toExternalForm());
        ImagePattern background = new ImagePattern(image);
        this.setFill(background);
    }


}
