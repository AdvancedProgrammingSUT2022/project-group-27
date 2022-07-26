package model;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CityRectangle extends Circle {
    private City city;
    public CityRectangle (City cityUnit){
        this.city = cityUnit;
        this.setLayoutX(cityUnit.getGround().getyLocation());
        this.setLayoutY(cityUnit.getGround().getxLocation());
        this.setRadius(40);
        if (Player.whichPlayerTurnIs().getCapital() != null && Player.whichPlayerTurnIs().getCapital().getGround().getNumber() == cityUnit.getGround().getNumber())
            this.setFill(GlobalVariables.getImagePattern("/images/crown.jfif"));
        else this.setFill(GlobalVariables.getImagePattern("/images/city.png"));
        if (city.getHp() < 19.7)
            setEffect(new InnerShadow((int) ((20 - city.getHp()) * 8),1,1,Color.RED));
    }

}
