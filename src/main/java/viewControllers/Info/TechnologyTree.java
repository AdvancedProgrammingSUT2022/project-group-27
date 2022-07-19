package viewControllers.Info;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.GlobalVariables;
import model.Player;
import model.Technology;
import model.TechnologyRectangle;
import viewControllers.GraphicOfGame;
import Enum.*;

import java.util.Random;

public class TechnologyTree extends Application {
    private static Stage stage;
    private static int counter=0;
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
        counter++;
        TechnologyTree.stage = stage;
        //TODO don't forget game.initializing after selecting a technology
        Pane root = (Pane) Main.loadFXML("TechnologyTree");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Technology Menu" + counter);
        stage.show();
    }
    @FXML
    public void initialize(){
        int x=100,y=200;
        for (Technology technology : player.getAllTechnologyTypes()){
            TechnologyRectangle technologyRectangle =new TechnologyRectangle(technology,x,y);
            x+=150;
            if (player.canWeAddThisTechnology(technology.getTechnologyType())){
                technologyRectangle.setEffect(new InnerShadow(100,1,1,Color.YELLOW));
                System.out.println(technology.getTechnologyType());
            }
            if (technology.getTimeRemain()<=0){
                technologyRectangle.setEffect(new InnerShadow(100,1,1,Color.GREEN));
            }
        }
        Random random=new Random(2);
        for (TechnologyType technologyType : TechnologyType.values() ) {
            for (TechnologyType technologyTypeSecond : technologyType.getPrerequisites()){
                TechnologyRectangle firstTechnologyRectangle=TechnologyRectangle.getTechnologyRectangle(technologyType);
                TechnologyRectangle secondTechnologyRectangle=TechnologyRectangle.getTechnologyRectangle(technologyTypeSecond);
                int height=random.nextInt(300,1000);
                int X= random.nextInt(-40,41);
                int W= random.nextInt(-40,41);
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
        for (Technology technology : player.getAllTechnologyTypes()){
            TechnologyRectangle technologyRectangle=TechnologyRectangle.getTechnologyRectangle(technology.getTechnologyType());
            technologyRectangle.setFill(technologyRectangle.technology.getTechnologyType().getImage());
            technologyRectangle.setStrokeWidth(10);
            technologyRectangle.setStroke(Color.WHITE);
            gamePane.getChildren().add(technologyRectangle);
        }

    }
}
