package viewControllers;

import Main.Main;
import controller.InitializeMap;
import controller.ProfileController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

import java.util.ArrayList;

public class GraphicOfGame extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    @FXML
    private Label technologyPanel;

    @FXML
    private Label unitPanel;

    @FXML
    private Label cityPanel;

    @FXML
    private Label diplomacyInformationPanel;

    @FXML
    private Label winPanel;

    @FXML
    private Label demographicPanel;

    @FXML
    private Label notificationPanel;

    @FXML
    private Label militaryPanel;

    @FXML
    private Label economicPanel;

    @FXML
    private Label tradingPanel;

    @FXML
    private Label diplomacyPanel;

    @FXML
    private Label technology;

    @FXML
    private HBox secondBar;

    @FXML
    private HBox bottom_hBox;

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

        statusBar.setPadding(new Insets(5, 10, 5, 10));
        secondBar.setPadding(new Insets(5, 10, 5, 10));
        bottom_hBox.setPadding(new Insets(10));
        technology.setPadding(new Insets(5));

        statusBar.setStyle("-fx-background-color: black; -fx-background-radius: 5");
        science.setStyle("-fx-text-fill: #00e1ff;");
        gold.setStyle("-fx-text-fill: gold;");
        happiness.setStyle("-fx-text-fill: pink;");
        technology.setStyle("-fx-background-color: #00c4ff; -fx-background-radius: 5;");

        setIcons();
        initializing();
        setTooltip(); //TODO set on Mouse Clicked for every panel :) ...
        setHover();

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
        if (Player.whichPlayerTurnIs().getUnderConstructionTechnology() != null) {
            technology.setText("technology: " + Player.whichPlayerTurnIs().getUnderConstructionTechnology().name() +
                    "turns: " + Player.whichPlayerTurnIs().getUnderConstructionTechnology().getTime());
        } else technology.setText("technology: nothing");

        setIconForPlayer();
        setHappinessImage();

        if (Game.getInstance().canWeGoNextTurn()) {
            nextTurn.setCursor(Cursor.HAND);
            nextTurn.setDisable(false);
        } else {
            nextTurn.setCursor(Cursor.DISAPPEAR);
            nextTurn.setDisable(true);
        }
    }

    private void setHover() {
        settingHoverToEach(technologyPanel);
        settingHoverToEach(unitPanel);
        settingHoverToEach(cityPanel);
        settingHoverToEach(diplomacyInformationPanel);
        settingHoverToEach(demographicPanel);
        settingHoverToEach(winPanel);
        settingHoverToEach(notificationPanel);
        settingHoverToEach(militaryPanel);
        settingHoverToEach(economicPanel);
        settingHoverToEach(tradingPanel);
        settingHoverToEach(diplomacyPanel);
    }

    private void settingHoverToEach(Label ourLabel) {
        ourLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(128,128,128,0.39); -fx-font-size: 10; -fx-text-fill: black;");
            }
        });
        ourLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 10; -fx-text-fill: black;");
            }
        });
    }

    private void setTechnologyMenu() {

    }

    private void setTooltip() {
        technology.setTooltip(new Tooltip("the technology which is on progress"));
        technology.getTooltip().setStyle("-fx-font-size: 14;");
        technologyPanel.setTooltip(new Tooltip("technology panel"));
        technologyPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        technologyPanel.getTooltip().setStyle("-fx-font-size: 14;");
        unitPanel.setTooltip(new Tooltip("unit panel"));
        unitPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        unitPanel.getTooltip().setStyle("-fx-font-size: 14;");
        cityPanel.setTooltip(new Tooltip("city panel"));
        cityPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        cityPanel.getTooltip().setStyle("-fx-font-size: 14;");
        diplomacyInformationPanel.setTooltip(new Tooltip("diplomacy information panel"));
        diplomacyInformationPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        diplomacyInformationPanel.getTooltip().setStyle("-fx-font-size: 14;");
        demographicPanel.setTooltip(new Tooltip("demographic panel"));
        demographicPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        demographicPanel.getTooltip().setStyle("-fx-font-size: 14;");
        winPanel.setTooltip(new Tooltip("win panel"));
        winPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        winPanel.getTooltip().setStyle("-fx-font-size: 14;");
        notificationPanel.setTooltip(new Tooltip("notification panel"));
        notificationPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        notificationPanel.getTooltip().setStyle("-fx-font-size: 14;");
        militaryPanel.setTooltip(new Tooltip("military view panel"));
        militaryPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        militaryPanel.getTooltip().setStyle("-fx-font-size: 14;");
        economicPanel.setTooltip(new Tooltip("economic view panel"));
        economicPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        economicPanel.getTooltip().setStyle("-fx-font-size: 14;");
        tradingPanel.setTooltip(new Tooltip("trading panel"));
        tradingPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        tradingPanel.getTooltip().setStyle("-fx-font-size: 14;");
        diplomacyPanel.setTooltip(new Tooltip("diplomacy panel"));
        diplomacyPanel.setStyle("-fx-text-fill: black; -fx-font-size: 10;");
        diplomacyPanel.getTooltip().setStyle("-fx-font-size: 14;");
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


        technologyPanel.setGraphic(setInfoImage());
        unitPanel.setGraphic(setInfoImage());
        cityPanel.setGraphic(setInfoImage());
        diplomacyInformationPanel.setGraphic(setInfoImage());
        demographicPanel.setGraphic(setInfoImage());
        winPanel.setGraphic(setInfoImage());
        notificationPanel.setGraphic(setInfoImage());
        militaryPanel.setGraphic(setInfoImage());
        economicPanel.setGraphic(setInfoImage());
        tradingPanel.setGraphic(setInfoImage());
        diplomacyPanel.setGraphic(setInfoImage());
    }

    private ImageView setInfoImage() {
        Image image = new Image(GraphicOfGame.class.getResource("/iconsOfGame/panel.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        return imageView;
    }

    private void setIconForPlayer() {
        Image playerImage = ProfileController.getInstance().getImage(Player.whichPlayerTurnIs().getUser()).getImage();
        ImageView playerIcon = new ImageView(playerImage);
        playerIcon.setFitWidth(20);
        playerIcon.setFitHeight(20);
        player.setGraphic(playerIcon);
    }

    private void setHappinessImage() {
        int countOfHappiness = Player.whichPlayerTurnIs().getHappiness();
        Image image;
        if (countOfHappiness > 0) image = new Image(GraphicOfGame.class.getResource("/iconsOfGame/Happy-citizens.jpg").toExternalForm());
        else image = new Image(GraphicOfGame.class.getResource("/iconsOfGame/unhappy-citizens.jpg").toExternalForm());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(320);
        imageView.setFitHeight(320);
        Tooltip tooltip = new Tooltip();
        tooltip.setGraphic(imageView);
        happiness.setTooltip(tooltip);
    }

    public void nextTurn(MouseEvent mouseEvent) {
        Player.nextTurn();
        initializing();
    }

    public void setting(MouseEvent mouseEvent) {
        //TODO...
    }
}
