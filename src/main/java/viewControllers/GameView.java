package viewControllers;

import Main.Main;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GlobalVariables;
import model.Player;
import model.User;
import view.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Enum.Message;
public class GameView extends Application {
    private static int seed=1;
    private static Stage stage;
    private static MediaPlayer audio;
    public TextField arrayOfEnemies;
    public Button startGameButton;
    public Label errorOfStartGame;
    public TextField numberOfEnemies;
    public Label errorOfNumberOfEnemies;
    public ArrayList<String> arrayList=new ArrayList<>();
    public Button randomMap;
    public Button map1Clicked;
    public Button map2Clicked;
    public Button map3Clicked;
    public Button map4Clicked;

    @FXML
    private Button createMapButton;

    @FXML
    private Button resumeGameButton;

    @FXML
    private VBox box;

    @FXML
    private Button back;


    @Override
    public void start(Stage stage) throws Exception {
        GameView.stage = stage;
        audio = Main.loadingAudio("openingTitles.mpeg");
        Parent root = Main.loadFXML("GameView");
        root.getStylesheets().add(Main.class.getResource("/css/NumberOfEnemies.css").toExternalForm());
        root.getStyleClass().add("background");
        System.out.println("game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization/ Game Menu");
        stage.show();
    }
    @FXML
    public void initialize(){
        randomMap.setDisable(true);
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });
        back.setCursor(Cursor.HAND);
        createMapButton.setCursor(Cursor.HAND);
        resumeGameButton.setCursor(Cursor.HAND);
        Tooltip backToolTip = new Tooltip("back to main menu");
        //backToolTip.widthProperty().addListener((obs, b, b1) -> System.out.println(backToolTip.getWidth()));
        backToolTip.setTextAlignment(TextAlignment.CENTER);
        back.setTooltip(backToolTip);
        box.setPadding(new Insets(20));
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }

    public void startGame(MouseEvent mouseEvent) {

        String s[]=arrayOfEnemies.getText().split(",");
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        ArrayList<User> listOfPlayers=new ArrayList<>();
        for (String playerUsers:arrayList){
            listOfPlayers.add(User.findUser(playerUsers));
        }
        while(listOfPlayers.size()<Integer.parseInt(numberOfEnemies.getText())){
            boolean exist=false;
            for (User user: User.getListOfUsers()){
                if (!listOfPlayers.contains(user)){
                    listOfPlayers.add(user);
                    exist=true;
                    break;
                }
            }
            if (!exist) break;
        }
        if (seed==1) GlobalVariables.surfaceWidth+=(4-Integer.parseInt(numberOfEnemies.getText()))*16;
        if (seed==2) GlobalVariables.surfaceWidth+=GlobalVariables.surfaceWidth-3*16;
        if (seed==3) GlobalVariables.surfaceWidth+=GlobalVariables.surfaceWidth-16;
        if (seed==4) GlobalVariables.surfaceWidth+=GlobalVariables.surfaceWidth+16;
        if (seed==5) GlobalVariables.surfaceWidth+=GlobalVariables.surfaceWidth+3*16;
        if (seed==6) GlobalVariables.surfaceWidth=GlobalVariables.surfaceWidth-3*16;
        System.err.println(seed);
        /*view.game.GameView gameView = new view.game.GameView(new ArrayList<User>(listOfPlayers),seed);
        System.out.println(Message.START_GAME);
        gameView.run();*/
        GraphicOfGame game = new GraphicOfGame();
        Player.emptyList();
        game.setting(new ArrayList<User>(listOfPlayers),seed);
        GraphicOfGame.setInstance(game);
        audio.stop();
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void textFieldEditor(KeyEvent keyEvent) {
        arrayList.clear();
        String s[]=arrayOfEnemies.getText().split(",");
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        System.out.println(arrayList + " " + arrayOfEnemies.getText() + " " + arrayOfEnemies.getLength());
        if (!(User.doWeHaveAllOfTheseUsers(arrayList) && User.isThisListOfUsersUnique(arrayList) && arrayList.contains(Menu.getLoggedInUser().getUsername()))){
            arrayOfEnemies.setStyle("-fx-text-fill: red;");
            startGameButton.setDisable(true);
            errorOfStartGame.setTextFill(Color.RED);
            errorOfStartGame.setText("");
            if (!User.doWeHaveAllOfTheseUsers(arrayList)) errorOfStartGame.setText("one of the usernames is invalid.\n");
            if (!User.isThisListOfUsersUnique(arrayList)) errorOfStartGame.setText(errorOfStartGame.getText()+" "+"the list you entered is not unique.\n");
            if (!arrayList.contains(Menu.getLoggedInUser().getUsername()))
                errorOfStartGame.setText(errorOfStartGame.getText()+"I can not see your username in this list ;/");
        }
        else{
            arrayOfEnemies.setStyle("-fx-text-fill: green;");
            errorOfStartGame.setText("");
            if (numberOfEnemies.getText().matches("\\d+") &&  errorOfNumberOfEnemies.getText().equals("") && Integer.parseInt(numberOfEnemies.getText())>=arrayList.size()){
                startGameButton.setDisable(false);
            }
        }
    }

    public void numberOfEnemiesEditor(KeyEvent keyEvent) {
        errorOfNumberOfEnemies.setTextFill(Color.RED);
        if (numberOfEnemies.getText().matches("\\d+") && Integer.parseInt(numberOfEnemies.getText())< arrayList.size()){
            errorOfNumberOfEnemies.setText("number should be equal or bigger than the number of enemies you enter in the first text field");
        }
        else {
            if (!numberOfEnemies.getText().matches("\\d+")){
                errorOfNumberOfEnemies.setText("you should enter an integer");
                startGameButton.setDisable(true);
            }
            else {
                if (Integer.parseInt(numberOfEnemies.getText())<2){
                    errorOfNumberOfEnemies.setText("atleast 2 player should be in game");
                    startGameButton.setDisable(true);
                }
                else {
                    errorOfNumberOfEnemies.setText("");
                    if (errorOfStartGame.getText().equals("") && arrayOfEnemies.getLength() > 0) {
                        startGameButton.setDisable(false);
                    }
                }
            }
        }
    }

    public void randomMap(MouseEvent mouseEvent) {
        map1Clicked.setDisable(false);
        map2Clicked.setDisable(false);
        map3Clicked.setDisable(false);
        map4Clicked.setDisable(false);
        randomMap.setDisable(false);
        randomMap.setDisable(true);
        Random random=new Random();
        seed=random.nextInt(6,200);

    }

    public void map1Clicked(MouseEvent mouseEvent) {
        map1Clicked.setDisable(false);
        map2Clicked.setDisable(false);
        map3Clicked.setDisable(false);
        map4Clicked.setDisable(false);
        randomMap.setDisable(false);
        map1Clicked.setDisable(true);
        seed=2;
    }

    public void map2Clicked(MouseEvent mouseEvent) {
        map1Clicked.setDisable(false);
        map2Clicked.setDisable(false);
        map3Clicked.setDisable(false);
        map4Clicked.setDisable(false);
        randomMap.setDisable(false);
        map2Clicked.setDisable(true);
        seed=3;
    }

    public void map3Clicked(MouseEvent mouseEvent) {
        map1Clicked.setDisable(false);
        map2Clicked.setDisable(false);
        map3Clicked.setDisable(false);
        map4Clicked.setDisable(false);
        randomMap.setDisable(false);
        map3Clicked.setDisable(true);
        seed=4;
    }

    public void map4Clicked(MouseEvent mouseEvent) {
        map1Clicked.setDisable(false);
        map2Clicked.setDisable(false);
        map3Clicked.setDisable(false);
        map4Clicked.setDisable(false);
        randomMap.setDisable(false);
        map4Clicked.setDisable(true);
        seed=5;
    }

    public void createMapButton(MouseEvent mouseEvent) throws Exception {
        seed=6;
        CreateMap createMap= new CreateMap();
        createMap.start(stage);

    }
    public static void setSeed(int sed){
        seed=sed;
    }

    public void resumeGame(MouseEvent mouseEvent) throws IOException {
        if (Player.getAllPlayers().size() == 0) Database.readGameDatabase();

        if (Player.getAllPlayers().size() == 0) {
            resumeGameButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Stopped Game");
            alert.setContentText("you don't have any stopped game");
            alert.show();
            return;
        }

        GlobalVariables.surfaceWidth += (4 - Player.getAllPlayers().size()) * 16;
        GraphicOfGame game = new GraphicOfGame();
        ArrayList<User> listOfPlayers = new ArrayList<>();
        for (Player player: Player.getAllPlayers()) {
            listOfPlayers.add(player.getUser());
        }

        game.setting(listOfPlayers, seed);
        GraphicOfGame.setInstance(game);
        audio.stop();
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
