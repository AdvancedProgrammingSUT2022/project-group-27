package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Enum.*;

import java.util.ArrayList;


public class StrategicResourceForCreateMap extends Rectangle {
    private GroundRectangleForCreateMap groundRectangle;
    int xLocation;
    int yLocation;
    public Label label=new Label();
    public GroundRectangleForCreateMap getGroundRectangle() {
        return groundRectangle;
    }
    public StrategicResourceForCreateMap (GroundRectangleForCreateMap groundRectangle,int x,int y){
        this.groundRectangle=groundRectangle;
        this.setX(y);
        this.setY(x);
        this.setHeight(40);
        this.setWidth(40);
        this.setFill(Color.WHITE);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                final Stage preStage = new Stage();
                ListView<StrategicResource> listView = new ListView<>();
                Scene scene = new Scene(listView);
                setInformation(listView);
                preStage.setScene(scene);
                preStage.show();
            }
        });
        this.update();
    }
    public void update(){
        Ground ground=groundRectangle.getGround();
        this.setWidth(40);
        this.setHeight(40);
        double x=0;
        if (groundRectangle.getGround().getStrategicResources().size()!=0){
            x=String.valueOf(groundRectangle.getGround().getStrategicResources().get(0)).length();
            x*=String.valueOf(groundRectangle.getGround().getStrategicResources().get(0)).charAt(0);
            x*=String.valueOf(groundRectangle.getGround().getStrategicResources().get(0)).charAt(1);
        }
        x%=253;
        x=x*x%253;
        double y=x*x*x%253;
        double z=y*x*y%253;
        this.setFill(Color.color(x/256,y/256,z/256));
    }
    private void setInformation(ListView<StrategicResource> listView) {
        listView.setPrefHeight(250);
        listView.setPrefWidth(100);
        ObservableList<StrategicResource> items = FXCollections.observableArrayList ();
        for (StrategicResource strategicResourceForCreateMap : StrategicResource.values()){
            if (groundRectangle.getGround().canWeAddThisStrategicResourceToThisGround(strategicResourceForCreateMap)){
                items.add(strategicResourceForCreateMap);
            }
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                StrategicResource s=listView.getSelectionModel().getSelectedItem();
                ArrayList <StrategicResource> b=new ArrayList<>();
                b.add(s);
                groundRectangle.getGround().setStrategicResources(b);
                update();
            }
        });
    }
}
