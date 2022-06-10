package viewControllers;

import Main.Main;
import controller.InitializeMap;
import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Player;
import model.User;
import controller.Game;

import java.awt.*;
import java.util.ArrayList;

public class GraphicOfGame extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();
    
    @FXML
    private Button setting;

    @FXML
    private Button nextTurn;

    @FXML
    private Label turn;

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

        statusBar.setStyle("-fx-background-color: black; -fx-background-radius: 5");
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        science.setStyle("-fx-text-fill: #00e1ff;");
        gold.setStyle("-fx-text-fill: gold;");
        happiness.setStyle("-fx-text-fill: pink;");
        setIcons();
        initializing();
        setting.setCursor(Cursor.HAND);
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

    private void initializing() { //TODO it should run at every steps, every moves and ...
        science.setText("science: " + Player.whichPlayerTurnIs().getScience());
        gold.setText("gold: " + Player.whichPlayerTurnIs().getGold());
        happiness.setText("happiness: " + Player.whichPlayerTurnIs().getHappiness());
        player.setText("player: " + Player.whichPlayerTurnIs().getUser().getUsername());
        turn.setText("year: " + Player.getYear());
        setIconForPlayer();

        if (Game.getInstance().canWeGoNextTurn()) {
            nextTurn.setCursor(Cursor.HAND);
            nextTurn.setDisable(false);
        } else {
            nextTurn.setCursor(Cursor.DISAPPEAR);
            nextTurn.setDisable(true);
        }
    }

    private void setIcons() {
        Image coin = new Image(GraphicOfGame.class.getResource("/iconsOfGame/coinIcon.png").toExternalForm());
        ImageView coinIcon = new ImageView(coin);
        coinIcon.setFitWidth(20);
        coinIcon.setFitHeight(20);
        gold.setGraphic(coinIcon);

        Image scienceImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/science.png.png").toExternalForm());
        ImageView scienceIcon = new ImageView(scienceImage);
        scienceIcon.setFitHeight(20);
        scienceIcon.setFitWidth(20);
        science.setGraphic(scienceIcon);

        Image happinessImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/happy.png").toExternalForm());
        ImageView happinessIcon = new ImageView(happinessImage);
        happinessIcon.setFitHeight(20);
        happinessIcon.setFitWidth(20);
        happiness.setGraphic(happinessIcon);

        Image yearImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/year-icon.jpg").toExternalForm());
        ImageView yearIcon = new ImageView(yearImage);
        yearIcon.setFitHeight(20);
        yearIcon.setFitWidth(20);
        turn.setGraphic(yearIcon);

        setIconForPlayer();
    }

    private void setIconForPlayer() {
        Image playerImage = ProfileController.getInstance().getImage(Player.whichPlayerTurnIs().getUser()).getImage();
        ImageView playerIcon = new ImageView(playerImage);
        playerIcon.setFitWidth(20);
        playerIcon.setFitHeight(20);
        player.setGraphic(playerIcon);
    }

    public void nextTurn(MouseEvent mouseEvent) {
        Player.nextTurn();
        initializing();
    }

    public void setting(MouseEvent mouseEvent) {
        //TODO...
    }
}
