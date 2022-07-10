package model;


import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import Enum.FeatureType;
public class FeatureRectangle extends Rectangle {
    private GroundRectangle groundRectangle;
    int xLocation;
    int yLocation;

    public GroundRectangle getGroundRectangle() {
        return groundRectangle;
    }
    public FeatureRectangle (GroundRectangle groundRectangle,int x,int y){
        this.groundRectangle=groundRectangle;
        this.setX(y-45);
        this.setY(x-80);
        this.setHeight(50);
        this.setWidth(90);
        System.out.println(groundRectangle.getXLocation()+ " feature " + groundRectangle.getYLocation());
        this.update();
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
