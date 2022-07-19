package model;

import javafx.event.EventHandler;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import viewControllers.Info.TechnologyTree;

import java.util.ArrayList;
import Enum.*;

public class TechnologyRectangle extends Circle {
    public static ArrayList<TechnologyRectangle> allTechnologyRectangle=new ArrayList<>();
    private int xLocation,yLocation;

    public int getyLocation() {
        return yLocation;
    }

    public int getxLocation() {
        return xLocation;
    }

    public void setyLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public void setxLocation(int xLocation) {
        this.xLocation = xLocation;
    }
    public Technology technology;
    public TechnologyRectangle (Technology technology,int xLocation,int yLocation){
        this.xLocation=xLocation;
        this.yLocation=yLocation;
        this.technology=technology;
        this.setLayoutX(xLocation);
        this.setLayoutY(yLocation);
        this.setRadius(50);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Player player=Player.whichPlayerTurnIs();
                if (player.doWeHaveThisTechnology(technology.getTechnologyType())) return;
                if (player.canWeAddThisTechnology(technology.getTechnologyType())){
                    player.setUnderConstructionTechnology(technology);
                    setEffect(new InnerShadow(100,1,1, Color.RED));
                }
                for (TechnologyRectangle technologyRectangle : allTechnologyRectangle){
                    if (technologyRectangle.technology.equals(technology)) continue;
                    if (player.canWeAddThisTechnology(technologyRectangle.technology.getTechnologyType())){
                        technologyRectangle.setEffect(new InnerShadow(100,1,1,Color.YELLOW));
                    }
                    if (player.doWeHaveThisTechnology(technologyRectangle.technology.getTechnologyType())){
                        technologyRectangle.setEffect(new InnerShadow(100,1,1,Color.GREEN));
                    }
                }
            }
        });
        allTechnologyRectangle.add(this);
    }
    public static TechnologyRectangle getTechnologyRectangle(TechnologyType technologyType){
        for (TechnologyRectangle technologyRectangle : allTechnologyRectangle){
            if (technologyRectangle.technology.getTechnologyType().equals(technologyType)){
                return technologyRectangle;
            }
        }
        return null;
    }
}
