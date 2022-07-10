package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Collections;


public class RiverRectangle extends Polygon {
    private Ground firstGround,secondGround;
    public RiverRectangle(Ground groundRectangle1,Ground groundRectangle2){
        firstGround=groundRectangle1;
        secondGround=groundRectangle2;
        int x1=groundRectangle1.getxLocation();
        int y1=groundRectangle1.getyLocation();
        int x2=groundRectangle2.getxLocation();
        int y2=groundRectangle2.getyLocation();
        if (x1>x2) {
            int z=x2;
            x2=x1;
            x1=z;
        }
        if (y1>y2){
            int z=y2;
            y2=y1;
            y1=z;
        }
        if (y1!=y2) {
            this.getPoints().addAll(new Double[]{
                    Double.valueOf(y1) + 7, Double.valueOf(x1) + 7,
                    Double.valueOf(y1) + 7, Double.valueOf(x2) - 7,
                    Double.valueOf(y2) - 7, Double.valueOf(x2) - 7,
                    Double.valueOf(y2) - 7, Double.valueOf(x1) + 7,
            });
        }
        else{
            this.getPoints().addAll(new Double[]{
                    Double.valueOf(y1-GlobalVariables.tool/2), Double.valueOf(x1+ GlobalVariables.arz),
                    Double.valueOf(y2-GlobalVariables.tool/2), Double.valueOf(x2- GlobalVariables.arz),
                    Double.valueOf(y2+GlobalVariables.tool/2), Double.valueOf(x2- GlobalVariables.arz),
                    Double.valueOf(y1+GlobalVariables.tool/2), Double.valueOf(x1+ GlobalVariables.arz),
            });
        }
        this.setFill(Color.AQUA);
    }

}
