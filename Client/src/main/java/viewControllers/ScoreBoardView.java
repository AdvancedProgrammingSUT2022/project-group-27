package viewControllers;

import controller.NetworkController;
import controller.UserController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.UserForScoreBoard;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ScoreBoardView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    public boolean isBack = false;

    public ScoreBoardView() {
        try {
            NetworkController.listenForUpdates(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button back;

    @FXML
    private TableColumn<UserForScoreBoard, String> usernameColumn;

    @FXML
    private TableColumn<UserForScoreBoard, byte[]> profileImageColumn;

    @FXML
    private TableColumn<UserForScoreBoard, Integer> scoreColumn;

    @FXML
    private TableColumn<UserForScoreBoard, String> timeColumn;

    @FXML
    private TableColumn<UserForScoreBoard, String> lastLoginColumn;

    @FXML
    private TableView scoreBoard;

    @FXML
    public void initialize() {
        profileImageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeOfScoreGame"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginTime"));
        profileImageColumn.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(byte[] item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image(new ByteArrayInputStream(item));
                    imageView.setImage(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    setGraphic(imageView);
                }
            }
        });

        usernameColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                TableRow<UserForScoreBoard> currentRow = getTableRow();
                if (!isEmpty()) {
                    if (UserController.getInstance().getUsername().equals(item))
                        currentRow.setStyle("-fx-background-color: rgba(0,168,0,0.55)");
                    else {
                        if (currentRow.getIndex() % 2 == 0)
                            currentRow.setStyle("-fx-background-color: #1dbbdd11;-fx-background-insets: 0, 0 0 1 0;-fx-padding: 0.0em;");
                        else currentRow.setStyle("-fx-background-color: #93f9b911;-fx-background-insets: 0, 0 0 1 0;-fx-padding: 0.0em;");
                    }
                    setText(item);
                }
            }
        });

        ObservableList<UserForScoreBoard> items = FXCollections.<UserForScoreBoard>observableArrayList();
        UserForScoreBoard.sort();
        items.addAll(UserForScoreBoard.getListOfUsers());
        scoreBoard.getItems().addAll(items);

        back.setCursor(Cursor.HAND);
    }

    private WritableImage getImageFromBytes(byte[] imgBytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imgBytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initializing() {
        if (scoreBoard == null) return;

        ObservableList<UserForScoreBoard> items = FXCollections.<UserForScoreBoard>observableArrayList();
        UserForScoreBoard.sort();
        items.addAll(UserForScoreBoard.getListOfUsers());
        scoreBoard.getItems().clear();
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
        stage.setTitle("Ancient Civilization/ Scoreboard Menu");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        this.isBack = true;
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }
}
