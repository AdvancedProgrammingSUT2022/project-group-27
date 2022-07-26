package model;

import controller.UserController;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CityRectangle extends Circle {
    private City city;
    public CityRectangle(City cityUnit){
        this.city = cityUnit;
        this.setLayoutX(cityUnit.getGround().getyLocation());
        this.setLayoutY(cityUnit.getGround().getxLocation());
        this.setRadius(40);
        if (Player.getPlayerByUser(UserController.getInstance().getUsername()).getCapitalGroundNumber() == cityUnit.getGround().getNumber())
            this.setFill(GlobalVariables.getImagePattern("/images/crown.png"));
        else this.setFill(GlobalVariables.getImagePattern("/images/city.png"));
        if (city.getHp() < 19.7)
            setEffect(new InnerShadow((int) ((20 - city.getHp()) * 8),1,1,Color.RED));
    }

}
