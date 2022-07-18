package viewControllers;

import Main.Main;
import controller.InitializeMap;
import controller.ProfileController;
import database.Database;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import controller.Game;
import viewControllers.Info.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;

import static java.lang.Math.max;

public class GraphicOfGame extends Application {
    private static GraphicOfGame instance = null;
    public static GraphicOfGame getInstance() {
        return instance;
    }

    public static void setInstance(GraphicOfGame game) {
        instance = game;
    }
    private static Stage stage;
    private static MediaPlayer audio;
    public  Pane gamePane;
    public static Pane gamePaneSecond;
    private Game controller;
    private ArrayList<User> playerUsers = new ArrayList<>();

    @FXML
    private Button stopButton;

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
        gamePaneSecond=gamePane;
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
        setTooltip();
        setHover();

        setting.setCursor(Cursor.HAND);
        stopButton.setCursor(Cursor.HAND);
    }

    public void setting(ArrayList<User> playerUsers,int seed) {
        this.controller = Game.getInstance();
        this.playerUsers = playerUsers;
        System.out.println("initialize");
        InitializeMap initializeMap = new InitializeMap(playerUsers, seed);
     //   if (seed!=6) {
            initializeMap.run();
       // }
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

    @FXML
    private void setCheatCode(KeyEvent keyEvent) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN);

        if (keyCombination.match(keyEvent)) {
            final Stage preStage = new Stage();
            TextField textField = new TextField();
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                        String command = textField.getText();
                        cheatCode(command);
                        preStage.close();
                    }
                }
            });
            Scene scene = new Scene(textField);
            preStage.setScene(scene);
            preStage.initOwner(stage);
            preStage.show();
        }
    }

    private void cheatCode(String input) {
        if (input.matches("^next turn ((--numberOfTurns)|(-n)) \\d+$")) {
            String[] s=input.split(" +");
            for (int i = 0; i < Integer.parseInt(s[3]); i++) {
                Player.nextTurn();
                initializing();
            }
        } else if (input.matches("^increase gold ((--numberOfGolds)|(-n)) \\d+$")){
            String[] s=input.split(" +");
            Player player=Player.whichPlayerTurnIs();
            player.increaseGold(Integer.parseInt(s[3]));
            initializing();
        } else if (input.matches("^increase happiness ((--numberOfHappiness)|(-n)) \\d+$")) {
            String[] s=input.split(" +");
            Player player=Player.whichPlayerTurnIs();
            player.increaseHappiness(Integer.parseInt(s[3]));
            initializing();
        } else if (input.matches("^increase score ((--numberOScore)|(-n)) \\d+$")) {
            String[] s=input.split(" +");
            Player player=Player.whichPlayerTurnIs();
            player.getUser().increaseScore(Integer.parseInt(s[3]));
            initializing();
        }
    }

    public void initializing() { //TODO it should run at every steps, every moves and ...
        science.setText("science: " + Player.whichPlayerTurnIs().getScience());
        gold.setText("gold: " + Player.whichPlayerTurnIs().getGold());
        happiness.setText("happiness: " + Player.whichPlayerTurnIs().getHappiness());
        player.setText("player: " + Player.whichPlayerTurnIs().getUser().getUsername());
        turn.setText("year: " + Player.getYear());
        if (Player.whichPlayerTurnIs().getUnderConstructionTechnology() != null) {
            technology.setText("technology: " + Player.whichPlayerTurnIs().getUnderConstructionTechnology().getTechnologyType().name() +
                    "\n turns: " + (Player.whichPlayerTurnIs().getUnderConstructionTechnology().getTimeRemain() +
                    Player.whichPlayerTurnIs().getScience() - 1) / max(1, Player.whichPlayerTurnIs().getScience()));
        } else technology.setText("technology: nothing");

        setIconForPlayer();
        setHappinessImage();

        if (Game.getInstance().canWeNextTurn()) {
            nextTurn.setCursor(Cursor.HAND);
            nextTurn.setDisable(false);
        } else {
            nextTurn.setCursor(Cursor.DISAPPEAR);
            nextTurn.setDisable(true);
        }
        Player player=Player.whichPlayerTurnIs();
        player.handleClearToSee();
        player.handleVisitedGrounds();
        setMenus();
        int cnt=0;
        int startingArz=GlobalVariables.arz+50;
        for (int i=gamePaneSecond.getChildren().size()-1;i>-1;i--){
            if (gamePaneSecond.getChildren().get(i) instanceof GroundRectangle || gamePaneSecond.getChildren().get(i) instanceof FeatureRectangle
                    || gamePaneSecond.getChildren().get(i) instanceof RiverRectangle ||
                    gamePaneSecond.getChildren().get(i) instanceof VisitedGround
                    || gamePaneSecond.getChildren().get(i) instanceof UnitRectangle ||
                    gamePaneSecond.getChildren().get(i) instanceof CityRectangle) gamePaneSecond.getChildren().remove(i);
        }

        for (int i=0;i<River.getAllRivers().size();i++){
            RiverRectangle riverRectangle=new RiverRectangle(River.getAllRivers().get(i).getFirstGround(),River.getAllRivers().get(i).getSecondGround());
            gamePaneSecond.getChildren().add(riverRectangle);
        }
        for (int i=1;i<=GlobalVariables.numberOfTilesInColumn;i++){
            int startingTool=GlobalVariables.tool+8;
            if (i%2==1){
                startingTool+=GlobalVariables.tool+GlobalVariables.tool/2+12;
            }
            for (int j=1;j<=GlobalVariables.numberOfTilesInRow;j++){
                GlobalVariables.numberOfTiles=cnt+1;
                cnt++;
                GroundRectangle groundRectangle=new GroundRectangle(Ground.getGroundByNumber(cnt),startingArz,startingTool);
                gamePaneSecond.getChildren().add(groundRectangle);
                gamePaneSecond.getChildren().add(new FeatureRectangle(groundRectangle,startingArz,startingTool));
                Ground ground=Ground.getGroundByNumber(cnt);
                ground.setxLocation(startingArz);
                ground.setyLocation(startingTool);
                if (ground.getHasRuin() && player.getClearToSeeGrounds().contains(ground))
                    gamePaneSecond.getChildren().add(new RuinRectangle(ground));
                System.out.println(Ground.getGroundByNumber(cnt).getxLocation());
                startingTool+=(GlobalVariables.tool+8)*3;
            }
            startingArz+=GlobalVariables.arz+8;
        }
        for (Player player1 : Player.getAllPlayers()) {
            for (City city : player1.getCities()) {
                if (player.getClearToSeeGrounds().contains(city.getGround())) {
                    CityRectangle cityRectangle = new CityRectangle(city);
                    gamePaneSecond.getChildren().add(cityRectangle);
                }
            }
        }

        for (Player user : Player.getAllPlayers()){
            for (Unit unit : user.getUnits()){
                if (player.getClearToSeeGrounds().contains(unit.getGround())) {
                    UnitRectangle unitRectangle = new UnitRectangle(unit);
                    gamePaneSecond.getChildren().add(unitRectangle);
                }
            }
        }
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            if (!player.getClearToSeeGrounds().contains(Ground.getGroundByNumber(i)) && player.getWasClearedToSeeGrounds().contains(Ground.getGroundByNumber(i))){

                VisitedGround visitedGround=new VisitedGround(Ground.getGroundByNumber(i));
                gamePaneSecond.getChildren().add(visitedGround);
                System.out.println("higuhurhgrthgirh" + player.getClearToSeeGrounds().size() + " " + player.getWasClearedToSeeGrounds().size());
            }
        }

        City capital = Player.whichPlayerTurnIs().getCapital();
        if (capital != null) {
            //TODO show palace on it
        }

        setTradeAlert();
        Player.whichPlayerTurnIs().checkDies();
        if (Game.getInstance().isFinished()) endOfGame();
    }

    public static void showMap(){
        Player player=Player.whichPlayerTurnIs();
        player.handleClearToSee();
        player.handleVisitedGrounds();
        int cnt=0;
        int startingArz=GlobalVariables.arz+50;
        for (int i=gamePaneSecond.getChildren().size()-1;i>-1;i--){
            if (gamePaneSecond.getChildren().get(i) instanceof GroundRectangle || gamePaneSecond.getChildren().get(i) instanceof FeatureRectangle
                    || gamePaneSecond.getChildren().get(i) instanceof RiverRectangle ||
                    gamePaneSecond.getChildren().get(i) instanceof VisitedGround
                    || gamePaneSecond.getChildren().get(i) instanceof UnitRectangle
                    || gamePaneSecond.getChildren().get(i) instanceof CityRectangle) gamePaneSecond.getChildren().remove(i);
        }

        for (int i=0;i<River.getAllRivers().size();i++){
            RiverRectangle riverRectangle=new RiverRectangle(River.getAllRivers().get(i).getFirstGround(),River.getAllRivers().get(i).getSecondGround());
            gamePaneSecond.getChildren().add(riverRectangle);
        }
        for (int i=1;i<=GlobalVariables.numberOfTilesInColumn;i++){
            int startingTool=GlobalVariables.tool+8;
            if (i%2==1){
                startingTool+=GlobalVariables.tool+GlobalVariables.tool/2+12;
            }
            for (int j=1;j<=GlobalVariables.numberOfTilesInRow;j++){
                GlobalVariables.numberOfTiles=cnt+1;
                cnt++;
                GroundRectangle groundRectangle=new GroundRectangle(Ground.getGroundByNumber(cnt),startingArz,startingTool);
                gamePaneSecond.getChildren().add(groundRectangle);
                gamePaneSecond.getChildren().add(new FeatureRectangle(groundRectangle,startingArz,startingTool));
                Ground ground=Ground.getGroundByNumber(cnt);
                ground.setxLocation(startingArz);
                ground.setyLocation(startingTool);
                if (ground.getHasRuin() && player.getClearToSeeGrounds().contains(ground))
                    gamePaneSecond.getChildren().add(new RuinRectangle(ground));
                System.out.println(Ground.getGroundByNumber(cnt).getxLocation());
                startingTool+=(GlobalVariables.tool+8)*3;
            }
            startingArz+=GlobalVariables.arz+8;
        }
        for (Player player1 : Player.getAllPlayers()) {
            for (City city : player1.getCities()) {
                if (player.getClearToSeeGrounds().contains(city.getGround())) {
                    CityRectangle cityRectangle = new CityRectangle(city);
                    gamePaneSecond.getChildren().add(cityRectangle);
                }
            }
        }
        for (Player user : Player.getAllPlayers()){
            for (Unit unit : user.getUnits()){
                if (player.getClearToSeeGrounds().contains(unit.getGround())) {
                    UnitRectangle unitRectangle = new UnitRectangle(unit);
                    gamePaneSecond.getChildren().add(unitRectangle);
                }
            }
        }
        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            if (!player.getClearToSeeGrounds().contains(Ground.getGroundByNumber(i)) && player.getWasClearedToSeeGrounds().contains(Ground.getGroundByNumber(i))){

                VisitedGround visitedGround=new VisitedGround(Ground.getGroundByNumber(i));
                gamePaneSecond.getChildren().add(visitedGround);
                System.out.println("higuhurhgrthgirh" + player.getClearToSeeGrounds().size() + " " + player.getWasClearedToSeeGrounds().size());
            }
        }

        City capital = Player.whichPlayerTurnIs().getCapital();
        if (capital != null) {
            //TODO show palace on it
        }

        GraphicOfGame.getInstance().setTradeAlert();
        Player.whichPlayerTurnIs().checkDies();
        if (Game.getInstance().isFinished()) GraphicOfGame.getInstance().endOfGame();
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

    private void setMenus() {
        setTechnologyMenu();
        setUnitMenu();
        setCityPanel();
        setDemographicPanel();
        setNotificationPanel();
        setMilitaryPanel();
        setEconomicPanel();
        setWinPage();
        setDiplomacyPanel();
        setDiplomacyInformationPanel();
        setTradingPanel();
    }

    private void setTradingPanel() {
        tradingPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TradingInformationPanel tradingInformationPanel = new TradingInformationPanel();
                TradingInformationPanel.setPlayer(Player.whichPlayerTurnIs());
                try {
                    tradingInformationPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDiplomacyInformationPanel() {
        diplomacyInformationPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DiplomacyInformationPanel diplomacyInformationPanel = new DiplomacyInformationPanel();
                DiplomacyInformationPanel.setPlayer(Player.whichPlayerTurnIs());
                try {
                    diplomacyInformationPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setWinPage() {
        winPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                WinPage winPage = new WinPage();
                WinPage.setPlayer(Player.whichPlayerTurnIs());
                try {
                    winPage.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDiplomacyPanel() {
        diplomacyPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DiplomacyPanel diplomacyPanel = new DiplomacyPanel();
                DiplomacyPanel.setPlayer(Player.whichPlayerTurnIs());
                try {
                    diplomacyPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setEconomicPanel() {
        economicPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                EconomicOverview economicOverview = new EconomicOverview();
                EconomicOverview.setPlayer(Player.whichPlayerTurnIs());
                try {
                    economicOverview.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setMilitaryPanel() {
        militaryPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MilitaryOverView militaryOverView = new MilitaryOverView();
                MilitaryOverView.setPlayer(Player.whichPlayerTurnIs());
                try {
                    militaryOverView.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setNotificationPanel() {
        notificationPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                NotificationPanel notificationPanel = new NotificationPanel();
                NotificationPanel.setPlayer(Player.whichPlayerTurnIs());
                try {
                    notificationPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDemographicPanel() {
        demographicPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DemographicPanel demographicPanel = new DemographicPanel();
                DemographicPanel.setPlayer(Player.whichPlayerTurnIs());
                try {
                    demographicPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTechnologyMenu() {
        technologyPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TechnologyMenu technologyMenu = new TechnologyMenu();
                TechnologyMenu.setPlayer(Player.whichPlayerTurnIs());
                TechnologyMenu.setGame(GraphicOfGame.this);
                try {
                    technologyMenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUnitMenu() {
        unitPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                UnitPanel unitPanel = new UnitPanel();
                UnitPanel.setPlayer(Player.whichPlayerTurnIs());
                UnitPanel.setGame(GraphicOfGame.this);
                try {
                    unitPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCityPanel() {
        cityPanel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CityPanel cityPanel = new CityPanel();
                CityPanel.setPlayer(Player.whichPlayerTurnIs());
                CityPanel.setGame(GraphicOfGame.this);
                try {
                    cityPanel.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void setTradeAlert() {
        boolean shouldChange = false;
        for (Trade trade: Trade.userReceiverTrades(Player.whichPlayerTurnIs().getUser())) {
            if (!trade.isAccepted() && !trade.isDeny()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Trade");
                alert.setContentText(trade.toString());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    trade.accept();
                    shouldChange = true;
                } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    trade.deny();
                }
            }
        }

        if (shouldChange) initializing();
    }

    public void nextTurn(MouseEvent mouseEvent) {
        Player.nextTurn();
        initializing();
    }

    public void setting(MouseEvent mouseEvent) {
        Stage preStage = new Stage();
        Label first = new Label("For enabling auto save fill the fields and then click submit button,\n if you don't want to have just click on refuse");
        TextField turns = new TextField("enter number of turns");
        TextField number = new TextField("enter number of files");
        Button refuse = new Button("refuse");
        Button submit = new Button("submit");

        refuse.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GlobalVariables.numberOfAutoSave = 0;
                GlobalVariables.numberOfFilesOfAutoSave = 0;
                preStage.close();
            }
        });

        submit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GlobalVariables.numberOfAutoSave = Integer.parseInt(turns.getText());
                GlobalVariables.numberOfFilesOfAutoSave = Integer.parseInt(number.getText());
                preStage.close();
            }
        });

        VBox vBox = new VBox(first, turns, number, refuse, submit);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        preStage.setScene(scene);
        preStage.initOwner(stage);
        preStage.show();
    }

    private void endOfGame() {
        Player player = Player.playerOfAliveAndHaveCapitalPlayer();
        if (player == null) return;

        for (Player player1: Player.getAllPlayers()) {
            player1.setScoreAndTimeAtEnd();
        }

        Stage preStage = new Stage();
        preStage.initOwner(stage);
        End end = new End();
        End.setPlayer(player);
        try {
            end.start(preStage);
            MainMenuView mainMenuView = new MainMenuView();
            mainMenuView.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopGame(MouseEvent mouseEvent) throws Exception {
        for (City city: Player.whichPlayerTurnIs().getCities()) {
            if (city.getConstruction() != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("can not stop here");
                alert.setContentText("you have production so we can't stop this game.");
                alert.show();
                return;
            }
        }
        Database.writeGameDatabase();
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }
}
