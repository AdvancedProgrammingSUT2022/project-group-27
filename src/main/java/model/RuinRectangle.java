package model;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RuinRectangle extends Rectangle {
    private GroundRectangle groundRectangle;
    int xLocation;
    int yLocation;

    public GroundRectangle getGroundRectangle() {
        return groundRectangle;
    }
    public RuinRectangle (GroundRectangle groundRectangle ,int x ,int y){
        this.groundRectangle=groundRectangle;
        this.setX(y-45);
        this.setY(x-80);
        this.setHeight(0);
        this.setWidth(0);
        System.out.println(groundRectangle.getXLocation()+ " feature " + groundRectangle.getYLocation());
        //this.update();
    }
    /*public void update(){
        Ground ground=groundRectangle.getGround();
        Player player=Player.whichPlayerTurnIs();
        player.handleClearToSee();
        if (!player.getWasClearedToSeeGrounds().contains(ground)){
            return;
        }
        if (ground.getFeatureType()!= FeatureType.NOTHING) {
            this.setWidth(90);
            this.setHeight(50);
            ImagePattern featureBackGround = ground.getFeatureType().getPhoto();
            this.setFill(featureBackGround);
        }
        else{
            this.setWidth(0);
            this.setHeight(0);
        }
    }*/
}
