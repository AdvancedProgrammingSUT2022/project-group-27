package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Enum.*;

public class FeatureForCreateMap extends Rectangle {
    private GroundRectangleForCreateMap groundRectangle;
    int xLocation;
    int yLocation;

    public GroundRectangleForCreateMap getGroundRectangle() {
        return groundRectangle;
    }
    public FeatureForCreateMap (GroundRectangleForCreateMap groundRectangle,int x,int y){
        this.groundRectangle=groundRectangle;
        this.setX(y-45);
        this.setY(x-80);
        this.setHeight(0);
        this.setWidth(0);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                final Stage preStage = new Stage();
                ListView<FeatureType> listView = new ListView<>();
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

        this.setWidth(90);
        this.setHeight(50);
        ImagePattern featureBackGround = ground.getFeatureType().getPhoto();
            this.setFill(featureBackGround);

    }
    private void setInformation(ListView<FeatureType> listView) {
        listView.setPrefHeight(250);
        listView.setPrefWidth(100);
        ObservableList<FeatureType> items = FXCollections.observableArrayList (FeatureType.FOREST,FeatureType.ICE,FeatureType.JUNGLE,FeatureType.OASIS,FeatureType.WATERSHED);
        if (groundRectangle.getGround().getGroundType() == GroundType.DESERT) {
            items.add(FeatureType.OASIS);
        }
        if (groundRectangle.getGround().containRiver())
            items.add(FeatureType.MARSH);

        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FeatureType s=listView.getSelectionModel().getSelectedItem();
                groundRectangle.getGround().setFeatureType(s);
                update();
            }
        });
    }
}
