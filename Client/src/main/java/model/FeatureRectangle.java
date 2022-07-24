package model;


import Enum.FeatureType;
import controller.UserController;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class FeatureRectangle extends Rectangle {
    private GroundRectangle groundRectangle;
    int xLocation;
    int yLocation;

    public GroundRectangle getGroundRectangle() {
        return groundRectangle;
    }
    public FeatureRectangle(GroundRectangle groundRectangle, int x, int y){
        this.groundRectangle=groundRectangle;
        this.setX(y-45);
        this.setY(x-80);
        this.setHeight(0);
        this.setWidth(0);
        System.out.println(groundRectangle.getXLocation()+ " feature " + groundRectangle.getYLocation());
        this.update();
    }
    public void update(){
        Ground ground=groundRectangle.getGround();
        if (Player.getAllPlayers().size()!=0) {
            Player player = Player.getPlayerByUser(UserController.getInstance().getUsername());
            player.handleClearToSee();
            if (!player.getWasClearedToSeeGrounds(ground)) {
                return;
            }
            if (ground.getFeatureType() != FeatureType.NOTHING) {
                this.setWidth(90);
                this.setHeight(50);
                ImagePattern featureBackGround = ground.getFeatureType().getPhoto();
                this.setFill(featureBackGround);
            } else {
                this.setWidth(0);
                this.setHeight(0);
            }
        }
    }
}
