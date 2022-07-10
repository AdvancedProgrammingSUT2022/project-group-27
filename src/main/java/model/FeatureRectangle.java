package model;


import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import Enum.FeatureType;
public class FeatureRectangle extends Rectangle {
    private GroundRectangle groundRectangle;

    public GroundRectangle getGroundRectangle() {
        return groundRectangle;
    }
    public FeatureRectangle (GroundRectangle groundRectangle){
        this.groundRectangle=groundRectangle;
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setHeight(1000);
        this.setWidth(1000);
    }
    public void update(){
        Ground ground=groundRectangle.getGround();
        if (ground.getFeatureType()!= FeatureType.NOTHING) {
            System.out.println("iugejirtughi");
            ImagePattern featureBackGround = ground.getFeatureType().getPhoto();
            this.setFill(featureBackGround);
        }
    }
}
