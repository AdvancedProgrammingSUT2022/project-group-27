package viewControllers.Info;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.Player;
import model.Technology;
import model.TechnologyRectangle;
import viewControllers.GraphicOfGame;
import Enum.*;

import java.util.Random;

public class TechnologyTree extends Application {
    private static Stage stage;
    private static Player player;
    private static GraphicOfGame game;
    public Pane gamePane;

    public static void setPlayer(Player player) {
        TechnologyTree.player = player;
    }

    public static void setGame(GraphicOfGame game) {
        TechnologyTree.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        TechnologyTree.stage = stage;
        //TODO don't forget game.initializing after selecting a technology
        Parent root = Main.loadFXML("TechnologyTree");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Technology Menu");
        stage.show();
    }
    @FXML
    public void initialize(){
        int x=100,y=200;
        for (Technology technology : player.getAllTechnologyTypes()){
            TechnologyRectangle technologyRectangle =new TechnologyRectangle(technology,x,y);
            x+=150;
            if (technology.getTimeRemain()<=0){
                technologyRectangle.setEffect(new Shadow(100,Color.GREEN));
            }
            gamePane.getChildren().add(technologyRectangle);
        }
        Random random=new Random(2);
        for (TechnologyType technologyType : TechnologyType.values() ) {
            for (TechnologyType technologyTypeSecond : technologyType.getPrerequisites()){
                TechnologyRectangle firstTechnologyRectangle=TechnologyRectangle.getTechnologyRectangle(technologyType);
                TechnologyRectangle secondTechnologyRectangle=TechnologyRectangle.getTechnologyRectangle(technologyTypeSecond);
                int height=random.nextInt(300,700);
                int X= random.nextInt(-20,21);
                int W= random.nextInt(-20,21);
                Line line1=new Line(X+firstTechnologyRectangle.getxLocation(),firstTechnologyRectangle.getyLocation(),X+firstTechnologyRectangle.getxLocation(),height);
                Line line2=new Line(W+secondTechnologyRectangle.getxLocation(),secondTechnologyRectangle.getyLocation(),W+secondTechnologyRectangle.getxLocation(),height);
                Line line3=new Line(X+firstTechnologyRectangle.getxLocation(),height,W+secondTechnologyRectangle.getxLocation(),height);
                line1.setStrokeWidth(5);
                double r=random.nextDouble(0,1);
                double g=random.nextDouble(0,1);
                double b=random.nextDouble(0,1);
                line1.setStroke(Color.color(r,g,b));
                line2.setStrokeWidth(5);
                line2.setStroke(Color.color(r,g,b));
                line3.setStrokeWidth(5);
                line3.setStroke(Color.color(r,g,b));
                gamePane.getChildren().add(line1);
                gamePane.getChildren().add(line2);
                gamePane.getChildren().add(line3);
            }
        }

    }
}
