package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import Enum.*;

import javafx.stage.Stage;

public class GroundRectangleForCreateMap extends Polygon {
    private Ground ground;
    private int xLocation,yLocation;

    public Ground getGround() {
        return ground;
    }

    public void setYLocation(int yLocation) {
        this.yLocation = yLocation;
    }

    public void setXLocation(int xLocation) {
        this.xLocation = xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public int getXLocation() {
        return xLocation;
    }

    public GroundRectangleForCreateMap (Ground ground, double x, double y){
        this.ground=ground;
        xLocation= (int) x;
        yLocation= (int) y;
        this.getPoints().addAll(new Double[]{
                Double.valueOf(y- GlobalVariables.tool),x,
                Double.valueOf(y-GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool/2),x-GlobalVariables.arz,
                Double.valueOf(y+GlobalVariables.tool),x,
                Double.valueOf(y+GlobalVariables.tool/2),x+GlobalVariables.arz,
                Double.valueOf(y-GlobalVariables.tool/2),x+GlobalVariables.arz,
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStroke(Color.AQUA);
                System.out.println("khodesh :" +  ground.getNumber());
                for (Ground ground1 : ground.getAdjacentGrounds()){
                    System.out.println(ground1.getNumber());
                }
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setStroke(Color.WHITE);
            }
        });

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                final Stage preStage = new Stage();
                ListView<String> listView = new ListView<>();
                Scene scene = new Scene(listView);
                setInformation(listView);
                preStage.setScene(scene);
                preStage.show();
            }
        });
        this.update();
    }

    public void update(){
        //  Image image = new Image(ProfileController.class.getResource(ground.getGroundType().getColor()).toExternalForm());
        if (ground.getGroundType()!=null) {
            ImagePattern background = ground.getGroundType().getColor();
            this.setFill(background);
        }
        if (Player.getAllPlayers().size()!=0) {
            Player player = Player.whichPlayerTurnIs();
            player.handleClearToSee();
            player.handleVisitedGrounds();
            if (!player.getWasClearedToSeeGrounds().contains(ground)) {
                this.setFill(FogOfWar.FOG.getColor());
            }
        }
    }

    private void setInformation(ListView<String> listView) {
        listView.setPrefHeight(250);
        listView.setPrefWidth(100);
        ObservableList<String> items = FXCollections.observableArrayList (
                "DESERT", "GRASS_PLOT", "HILL", "MOUNTAIN","OCEAN","PLAIN","SNOW","TUNDRA");
        listView.setItems(items);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String s=listView.getSelectionModel().getSelectedItem();
                if (s.equals("DESERT")){
                    ground.setGroundType(GroundType.DESERT);
                    update();
                }
                if (s.equals("GRASS_PLOT")){
                    ground.setGroundType(GroundType.GRASS_PLOT);
                    update();
                }
                if (s.equals("HILL")){
                    ground.setGroundType(GroundType.HILL);
                    update();
                }
                if (s.equals("MOUNTAIN")){
                    ground.setGroundType(GroundType.MOUNTAIN);
                    update();
                }
                if (s.equals("OCEAN")){
                    ground.setGroundType(GroundType.OCEAN);
                    update();
                }
                if (s.equals("PLAIN")){
                    ground.setGroundType(GroundType.PLAIN);
                    update();
                }
                if (s.equals("SNOW")){
                    ground.setGroundType(GroundType.SNOW);
                    update();
                }
                if (s.equals("TUNDRA")){
                    ground.setGroundType(GroundType.TUNDRA);
                    update();
                }
            }
        });
    }
}
