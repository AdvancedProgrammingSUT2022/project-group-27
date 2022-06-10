package viewControllers;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    @FXML
    public void initialize() {
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game.stage = stage;
        audio = Main.loadingAudio("gladiator.mp3");
        Parent root = Main.loadFXML("game");
        root.getStylesheets().add(Main.class.getResource("/css/game.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ancient Civilization");
        stage.show();
    }
}
