package viewControllers;

import Main.Main;
import controller.Game;
import controller.InitializeMap;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import view.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Enum.Message;


public class CreateMap extends Application {

    private static Stage stage;
    public Pane gamePaneSecond;

    @Override
    public void start(Stage stage) throws Exception {
        CreateMap.stage=stage;
        Parent root = Main.loadFXML("CreateMap");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization/ Game Menu");
        stage.show();
    }

    @FXML
    public void initialize() {
        initializing();
    }
    public void initializing(){
        Game game=Game.getInstance();
        InitializeMap initializeMap=new InitializeMap(null,0);
        initializeMap.run();
        int cnt=0;
        int startingArz=GlobalVariables.arz+50;
        for (int i=gamePaneSecond.getChildren().size()-1;i>-1;i--){
            if (gamePaneSecond.getChildren().get(i) instanceof GroundRectangleForCreateMap || gamePaneSecond.getChildren().get(i) instanceof FeatureForCreateMap
                    || gamePaneSecond.getChildren().get(i) instanceof RiverForCreateMap ||
                    gamePaneSecond.getChildren().get(i) instanceof VisitedGround
                    || gamePaneSecond.getChildren().get(i) instanceof UnitRectangle ||
                    gamePaneSecond.getChildren().get(i) instanceof CityRectangle) gamePaneSecond.getChildren().remove(i);
        }

        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            for (int j=i+1;j<GlobalVariables.numberOfTiles;j++){
                if (River.couldWePutRiverBetweenTheseTwoGround(Ground.getGroundByNumber(i),Ground.getGroundByNumber(j))){
                    RiverForCreateMap river=new RiverForCreateMap(Ground.getGroundByNumber(i),Ground.getGroundByNumber(j));
                    gamePaneSecond.getChildren().add(river);
                }
            }
        }
        for (int i=1;i<=GlobalVariables.numberOfTilesInColumn;i++){
            int startingTool=GlobalVariables.tool+8;
            if (i%2==1){
                startingTool+=GlobalVariables.tool+GlobalVariables.tool/2+12;
            }
            for (int j=1;j<=GlobalVariables.numberOfTilesInRow;j++){
                GlobalVariables.numberOfTiles=cnt+1;
                cnt++;
                GroundRectangleForCreateMap groundRectangleForCreateMap=new GroundRectangleForCreateMap(Ground.getGroundByNumber(cnt),startingArz,startingTool);
                gamePaneSecond.getChildren().add(groundRectangleForCreateMap);
                gamePaneSecond.getChildren().add(new FeatureForCreateMap(groundRectangleForCreateMap,startingArz,startingTool));
                gamePaneSecond.getChildren().add(new BonusResourceForCreateMap(groundRectangleForCreateMap,startingArz,startingTool));
                gamePaneSecond.getChildren().add(new StrategicResourceForCreateMap(groundRectangleForCreateMap,startingArz,startingTool));
                gamePaneSecond.getChildren().add(new LuxuryResourceForCreateMap(groundRectangleForCreateMap,startingArz,startingTool));
               // gamePaneSecond.getChildren().add(new FeatureRectangle(groundRectangleForCreateMap,startingArz,startingTool));
                Ground ground=Ground.getGroundByNumber(cnt);
                ground.setxLocation(startingArz);
                ground.setyLocation(startingTool);
                System.out.println(Ground.getGroundByNumber(cnt).getxLocation());
                startingTool+=(GlobalVariables.tool+8)*3;
            }
            startingArz+=GlobalVariables.arz+8;
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        GameView.setSeed(6);
        GameView gameView = new GameView();
        gameView.start(stage);

    }
}