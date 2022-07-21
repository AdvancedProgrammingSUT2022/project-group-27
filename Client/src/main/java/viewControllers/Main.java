package viewControllers;

import controller.NetworkController;
import controller.UserController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    @FXML
    private Label author;

    public static void main(String[] args) {
        if (!NetworkController.connect()) {
            System.out.println("can not connect");
            return;
        }

        launch();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                UserController.getInstance().atEndWorks();
            }
        }));
    }

    @FXML
    public void initialize() {
        audio.play();
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.WHITE);
        author.setFont(new Font(24));
        author.getStylesheets().add(Main.class.getResource("/css/welcome.css").toExternalForm());
        author.getStyleClass().add("text");
        author.setEffect(shadow);
    }

    @Override
    public void start(Stage stage) throws Exception {
        audio = loadingAudio("foxTwentyCentry.mp4");
        Main.stage = stage;
        Parent root = loadFXML("welcome");
        root.getStylesheets().add(Main.class.getResource("/css/welcome.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        settingNextSceneAfterAudio(scene);
        stage.setScene(scene);
        stage.show();
    }

    private static void settingNextSceneAfterAudio(Scene scene) {
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.stop();
                LoginView loginView = new LoginView();
                try {
                    loginView.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    audio.stop();
                    LoginView loginView = new LoginView();
                    try {
                        loginView.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static MediaPlayer loadingAudio(String name) {
        Media audio = new Media(Main.class.getResource("/audio/" + name).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.setAutoPlay(true);
        return mediaPlayer;
    }

    public static Parent loadFXML(String name) throws IOException {
        URL address = new URL(Main.class.getResource("/" + name + ".fxml").toExternalForm());
        return FXMLLoader.load(address);
    }
}
