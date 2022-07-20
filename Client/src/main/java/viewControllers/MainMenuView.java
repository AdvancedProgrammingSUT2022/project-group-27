package viewControllers;

import Enum.Message;
import controller.ProfileController;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MainMenuView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    private static String user = UserController.getInstance().getUserLoggedIn();

    @FXML
    public Button chat;

    @FXML
    private Button scoreBoard;

    @FXML
    private Rectangle profileImage;

    @FXML
    private Label username;

    @FXML
    private Label text;

    @FXML
    private Button logOut;

    @FXML
    private Button profile;

    @FXML
    private Button game;

    @FXML
    public void initialize() {
        user = UserController.getInstance().getUserLoggedIn();
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });

        ProfileController.getInstance().settingProfile(profileImage, user);

        username.setText("current username: *" + UserController.getInstance().getUsername() + "*, and score: *" + UserController.getInstance().getScore() + "*");
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.WHITE);
        text.setEffect(shadow);
        text.setFont(new Font(24));
        text.getStylesheets().add(Main.class.getResource("/css/mainMenu.css").toExternalForm());
        text.getStyleClass().add("text");

        username.setEffect(shadow);
        username.setFont(new Font(14));
        username.setStyle("-fx-text-fill: black");

        logOut.setCursor(Cursor.HAND);
        profile.setCursor(Cursor.HAND);
        game.setCursor(Cursor.HAND);
        chat.setCursor(Cursor.HAND);
        scoreBoard.setCursor(Cursor.HAND);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView.stage = stage;
        audio = Main.loadingAudio("menu.m4a");
        Parent root = Main.loadFXML("main-menu-view");
        root.getStylesheets().add(Main.class.getResource("/css/mainMenu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization/ Main Menu");
        stage.show();
    }

    public void logOut(MouseEvent mouseEvent) throws Exception {
        UserController.getInstance().setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        UserController.getInstance().setUserLoggedIn(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success log out");
        alert.setContentText(Message.LOGOUT.toString());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            audio.stop();
            LoginView loginView = new LoginView();
            loginView.start(stage);
        }
    }

    public void profileMenu(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        //ProfileView profileView = new ProfileView();
        //profileView.start(stage);
    }

    public void gameMenu(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        //GameView gameView = new GameView();
        //gameView.start(stage);
    }

    public void scoreBoardMenu(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        //ScoreBoardView scoreBoardView = new ScoreBoardView();
        //scoreBoardView.start(stage);
    }

    public void chatRoomMenu(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        ChatRoomView chatRoomView = new ChatRoomView();
        chatRoomView.start(stage);
    }
}
