package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Enum.*;

import java.util.ArrayList;


public class BonusResourceForCreateMap extends Rectangle {
    private GroundRectangleForCreateMap groundRectangle;
    int xLocation;
    int yLocation;
    public Label label=new Label();
    public GroundRectangleForCreateMap getGroundRectangle() {
        return groundRectangle;
    }
    public BonusResourceForCreateMap (GroundRectangleForCreateMap groundRectangle,int x,int y){
        this.groundRectangle=groundRectangle;
        this.setX(y-45);
        this.setY(x);
        this.setHeight(40);
        this.setWidth(40);
        this.setFill(Color.WHITE);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                final Stage preStage = new Stage();
                ListView<BonusResource> listView = new ListView<>();
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
        if (groundRectangle.getGround().getBonusResource().size()!=0){
            x=String.valueOf(groundRectangle.getGround().getBonusResource().get(0)).length();
            x*=String.valueOf(groundRectangle.getGround().getBonusResource().get(0)).charAt(0);
            x*=String.valueOf(groundRectangle.getGround().getBonusResource().get(0)).charAt(1);
        }
        x=x*x%253;
        double y=x*x*x%253;
        double z=y*x*y%253;
        this.setFill(Color.color(x/256,y/256,z/256));
    }
    private void setInformation(ListView<BonusResource> listView) {
        listView.setPrefHeight(250);
        listView.setPrefWidth(100);
        ObservableList<BonusResource> items = FXCollections.observableArrayList ();
        for (BonusResource bonusResource : BonusResource.values()){
            if (groundRectangle.getGround().canWeAddThisBonusResourceToThisGround(bonusResource)){
                items.add(bonusResource);
            }
        }
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BonusResource s=listView.getSelectionModel().getSelectedItem();
                ArrayList <BonusResource> b=new ArrayList<>();
                b.add(s);
                groundRectangle.getGround().setBonusResource(b);

                update();
            }
        });
    }
}
