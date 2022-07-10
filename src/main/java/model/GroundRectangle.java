package model;

import controller.ProfileController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

import javax.swing.text.StyledEditorKit;

import Enum.FeatureType;
import javafx.scene.shape.Rectangle;

public class GroundRectangle extends Polygon {
    private Ground ground;
    private int xLocation,yLocation;

    public Ground getGround() {
        return ground;
    }

    public void setYLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public void setXLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public int getXLocation() {
        return xLocation;
    }

    public GroundRectangle (Ground ground, double x, double y){
        this.ground=ground;
        xLocation= (int) x;
        yLocation= (int) y;
        this.getPoints().addAll(new Double[]{
                Double.valueOf(y- GlobalVariables.tool),x,
                Double.valueOf(y-GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool),x,
                Double.valueOf(y+GlobalVariables.tool/2),x+GlobalVariables.arz,
                Double.valueOf(y-GlobalVariables.tool/2),x+GlobalVariables.arz,
        });

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStroke(Color.AQUA);
                System.out.println("khodesh :" +  ground.getNumber());
                for (Ground ground1 : ground.getAdjacentGrounds()){
                    System.out.println(ground1.getNumber());
                }
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStroke(Color.WHITE);
            }
        });
        this.update();

    }
    public void update(){
      //  Image image = new Image(ProfileController.class.getResource(ground.getGroundType().getColor()).toExternalForm());
        ImagePattern background = ground.getGroundType().getColor();
        this.setFill(background);
    }



}
