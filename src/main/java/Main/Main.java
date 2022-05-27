package Main;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.LoginMenu;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    public static Stage getStage() {
        return stage;
    }

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
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.readFromDatabase();
        audio = loadingAudio("foxTwentyCentry.mp4");
        Main.stage = stage;
        Parent root = loadFXML("welcome");
        root.getStylesheets().add(Main.class.getResource("/css/welcome.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static MediaPlayer loadingAudio(String name) {
        Media audio = new Media(Main.class.getResource("/audio/" + name).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(audio);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        return mediaPlayer;
    }

    public static Parent loadFXML(String name) throws IOException {
        URL address = new URL(Main.class.getResource("/" + name + ".fxml").toExternalForm());
        return FXMLLoader.load(address);
    }
}
