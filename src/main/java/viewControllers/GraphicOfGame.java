package viewControllers;

import Main.Main;
import controller.InitializeMap;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import model.User;
import controller.Game;

import java.util.ArrayList;

public class GraphicOfGame extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    @FXML
    private Label player;

    @FXML
    private Label science;

    @FXML
    private Label gold;

    @FXML
    private Label happiness;

    @FXML
    private HBox statusBar;

    @FXML
    public void initialize() {
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });

        initializing();
    }

    public void setting(ArrayList<User> playerUsers,int seed) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
        InitializeMap initializeMap = new InitializeMap(playerUsers,seed);
        initializeMap.run();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GraphicOfGame.stage = stage;
        audio = Main.loadingAudio("gladiator.mp3");
        Parent root = Main.loadFXML("game");
        root.getStylesheets().add(Main.class.getResource("/css/game.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization");
        stage.show();
    }

    public void initializing() { //TODO it should run at every steps, every moves and ...
        science.setText("science: " + Player.whichPlayerTurnIs().getScience());
        gold.setText("gold: " + Player.whichPlayerTurnIs().getGold());
        happiness.setText("happiness: " + Player.whichPlayerTurnIs().getHappiness());
        player.setText(Player.whichPlayerTurnIs().getUser().getUsername());
    }
}
