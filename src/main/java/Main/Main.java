package Main;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.LoginMenu;
import viewControllers.LoginView;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    @FXML
    private Label author;

    public static void main(String[] args) throws IOException {
        launch();

        LoginMenu.getInstance().run(); //TODO change these things to graphic

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Database.writeOnDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    @FXML
    public void initialize() {
        audio.play();
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setColor(Color.WHITE);
        author.setFont(new Font(24));
        author.setStyle("-fx-text-fill: #4d370b");
        author.setEffect(shadow);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.readFromDatabase();
        audio = loadingAudio("foxTwentyCentry.mp4");
        settingNextSceneAfterAudio();
        Main.stage = stage;
        Parent root = loadFXML("welcome");
        root.getStylesheets().add(Main.class.getResource("/css/welcome.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static void settingNextSceneAfterAudio() {
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
