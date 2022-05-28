package viewControllers;

import Main.Main;
import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import view.Menu;

public class ProfileView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;
    private static final User user = Menu.getLoggedInUser();

    @FXML
    private Label username;

    @FXML
    private Rectangle profileImage;

    @FXML
    public void initialize() {
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });

        ProfileController.getInstance().settingProfile(profileImage, user);

        username.setText("current username: *" + user.getUsername() + "*, and score: *" + user.getScore() + "*");
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5);
        shadow.setColor(Color.WHITE);
        username.setEffect(shadow);
        username.setFont(new Font(14));
        //TODO complete it...
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProfileView.stage = stage;
        audio = Main.loadingAudio("Revenge Is A Dish Best Eaten.mp3");
        Parent root = Main.loadFXML("profile-view");
        root.getStylesheets().add(Main.class.getResource("/css/profile.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.show();
    }
}
