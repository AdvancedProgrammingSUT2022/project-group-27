package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class VisitedGround extends Polygon {
    public VisitedGround(Ground ground){
        double x=ground.getxLocation();
        double y=ground.getyLocation();
        this.getPoints().addAll(new Double[]{
                Double.valueOf(y- GlobalVariables.tool),x,
                Double.valueOf(y-GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool),x,
                Double.valueOf(y+GlobalVariables.tool/2),x+GlobalVariables.arz,
                Double.valueOf(y-GlobalVariables.tool/2),x+GlobalVariables.arz,
        });
        this.setFill(Color.color(1,1,1,0.8));
        this.setDisable(true);
    }
}
