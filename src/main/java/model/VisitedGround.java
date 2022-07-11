package model;

import controller.ProfileController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import Enum.*;

import javax.swing.text.StyledEditorKit;

import Enum.FeatureType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class VisitedGround extends Polygon {
    public VisitedGround (Ground ground){
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
    }
}
