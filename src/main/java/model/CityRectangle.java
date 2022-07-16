package model;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CityRectangle extends Circle {
    private City city;
    public CityRectangle (City cityUnit){
        this.setLayoutX(cityUnit.getGround().getyLocation());
        this.setLayoutY(cityUnit.getGround().getxLocation());
        this.setRadius(40);
        if (Player.whichPlayerTurnIs().getCapital().getGround().getNumber() == cityUnit.getGround().getNumber())
            this.setFill(GlobalVariables.getImagePattern("/images/crown-red.png"));
        else this.setFill(GlobalVariables.getImagePattern("/images/city.png"));
    }

}
