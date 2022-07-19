package model;

import controller.Game;
import controller.ProfileController;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
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
import view.game.GameView;
import viewControllers.GraphicOfGame;

import java.awt.*;

public class GroundRectangle extends Polygon {
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

    public GroundRectangle (Ground ground, double x, double y){
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
                setEffect(new InnerShadow(100,1,1,Color.YELLOW));

            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (GlobalVariables.unitRectangle==null) {
                    if (Player.getAllPlayers().size()!=0) {
                        if (Player.whichPlayerTurnIs().getWasClearedToSeeGrounds().contains(ground)) {
                            final Stage preStage = new Stage();
                            VBox vBox = new VBox();
                            Scene scene = new Scene(vBox);
                            setInformation(vBox);
                            preStage.setScene(scene);
                            preStage.show();
                        }
                    }
                }
                else{
                    String s;
                    if (GlobalVariables.unitRectangle.getUnit() instanceof MilitaryUnit) s="Military";
                    else s="UnMilitary";
                    Game.getInstance().moveUnits(GlobalVariables.unitRectangle.getUnit().getGround().getNumber(), ground.getNumber(), s);
                    GlobalVariables.unitRectangle=null;
                    GraphicOfGame.showMap();
                }

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
            //System.out.println("krutjhgiruthiruhtih" + player.getWasClearedToSeeGrounds().size());
            if (!player.getWasClearedToSeeGrounds().contains(ground)) {
                this.setFill(FogOfWar.FOG.getColor());
            }
        }
    }

    private void setInformation(VBox vBox) {
        vBox.getChildren().add(new Label("ground number: " + ground.getNumber()));
        for (StrategicResource strategicResource: ground.getStrategicResources()) {
            if (strategicResource.name().equals("NOTHING")) break;
            vBox.getChildren().add(new Label(strategicResource.name() + " the change that it has on productions is: " +
                    strategicResource.getProduction() + "on food: " + strategicResource.getFood() + "on gold: " + strategicResource.getGold()));
        }

        for (LuxuryResource luxuryResource: ground.getLuxuryResources()) {
            if (luxuryResource.name().equals("NOTHING")) break;
            vBox.getChildren().add(new Label(luxuryResource.name() + " the change that it has on productions is: " +
                    luxuryResource.getProduction() + "on food: " + luxuryResource.getFood() + "on gold: " + luxuryResource.getGold()));
        }

        for (BonusResource bonusResource: ground.getBonusResource()) {
            if (bonusResource.name().equals("NOTHING")) break;
            vBox.getChildren().add(new Label(bonusResource.name() + " the change that it has on productions is: " +
                    bonusResource.getProduction() + "on food: " + bonusResource.getFood() + "on gold: " + bonusResource.getGold()));
        }

        vBox.getChildren().add(new Label(ground.getGroundType().name() + " the change that it has on gold: " +
                ground.getGroundType().getGold() + "on food: " + ground.getGroundType().getFood() + "on production: " +
                ground.getGroundType().getProduction()));

        if (!ground.getFeatureType().name().equals("NOTHING")) vBox.getChildren().add(new Label(ground.getFeatureType().name() + " the change that it has on gold: " +
                ground.getFeatureType().getGold() + "on food: " + ground.getFeatureType().getFood() + "on production: " +
                ground.getFeatureType().getProduction()));
    }
}
