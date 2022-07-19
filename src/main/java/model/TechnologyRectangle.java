package model;

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
    Technology technology;
    public TechnologyRectangle (Technology technology,int xLocation,int yLocation){
        this.xLocation=xLocation;
        this.yLocation=yLocation;
        this.technology=technology;
        this.setLayoutX(xLocation);
        this.setLayoutY(yLocation);
        this.setRadius(50);
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
