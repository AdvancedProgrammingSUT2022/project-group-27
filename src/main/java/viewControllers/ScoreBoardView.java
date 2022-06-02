package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.User;

import java.awt.*;

public class ScoreBoardView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, Rectangle> profileImageColumn;

    @FXML
    private TableColumn<User, Integer> scoreColumn;

    @FXML
    private TableColumn<User, String> timeColumn;

    @FXML
    private TableColumn<User, String> lastLoginColumn;

    @FXML
    private TableView scoreBoard;

    @FXML
    public void initialize() {
        //profileImageColumn.setCellValueFactory(new PropertyValueFactory<>("profile"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfScoreGame"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginTime"));

        ObservableList<User> items = FXCollections.<User>observableArrayList();
        User.sort();
        items.addAll(User.getListOfUsers());
        scoreBoard.getItems().addAll(items);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScoreBoardView.stage = stage;
        audio = Main.loadingAudio("clasic.mp3");
        Parent root = Main.loadFXML("score-board");
        root.getStylesheets().add(Main.class.getResource("/css/scoreBoard.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }
}
