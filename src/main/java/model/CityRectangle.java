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
    public CityRectangle (City unit){
        this.setLayoutX(unit.getGround().getyLocation());
        this.setLayoutY(unit.getGround().getxLocation());
        this.setRadius(40);
        this.setFill(GlobalVariables.getImagePattern("/images/city.png"));
    }

}
