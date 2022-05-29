package viewControllers;

import Main.Main;
import Enum.Message;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;

public class LoginView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    private boolean isSignIn = false;

    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField nickname;

    @FXML
    private Label text;

    @FXML
    private Button signIn;

    @FXML
    private Button signUp;

    @FXML
    public void initialize() {
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.WHITE);
        text.setEffect(shadow);
        text.setFont(new Font(24));
        text.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
        text.getStyleClass().add("text");
        submit.setCursor(Cursor.HAND);

        signUp.setDisable(true);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginView.stage = stage;
        audio = Main.loadingAudio("openingTitles.mpeg");
        Parent root = Main.loadFXML("login-view");
        root.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login Menu");
        stage.show();
    }

    public void signInFields(MouseEvent mouseEvent) {
        isSignIn = true;
        signIn.setDisable(true);
        signUp.setDisable(false);
        nickname.setVisible(false);
    }

    public void signUpFields(MouseEvent mouseEvent) {
        isSignIn = false;
        signUp.setDisable(true);
        signIn.setDisable(false);
        nickname.setVisible(true);
    }

    public void submit(MouseEvent mouseEvent) throws Exception {
        if (username.getLength() == 0 || password.getLength() == 0 || (!isSignIn && nickname.getLength() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("empty field");
            alert.setContentText("sorry, but one of your necessary fields is empty");
            alert.show();
            return;
        }

        if (isSignIn) {
            Message message = LoginController.getInstance().loginUser(username.getText(), password.getText());
            if (message != Message.LOGIN_SUCCESSFUL) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("invalid field");
                alert.setContentText(message.toString());
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success sign in");
                alert.setContentText(message.toString());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    audio.stop();
                    MainMenuView mainMenuView = new MainMenuView();
                    mainMenuView.start(stage);
                }
            }
        } else {
            Message message = LoginController.getInstance().createUser(username.getText(), password.getText(), nickname.getText());
            if (message != Message.CREATE_USER_SUCCESSFUL) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("invalid field");
                alert.setContentText(message.toString());
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success sign up");
                alert.setContentText(message.toString());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    audio.stop();
                    MainMenuView mainMenuView = new MainMenuView();
                    mainMenuView.start(stage);
                }
            }
        }
    }
}
