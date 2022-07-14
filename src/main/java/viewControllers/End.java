package viewControllers;

import Main.Main;
import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Player;

public class End extends Application {
    private static Player player;

    @FXML
    private VBox list;

    @FXML
    private Label text;

    @FXML
    private Rectangle profileImage;

    public static void setPlayer(Player player) {
        End.player = player;
    }

    @FXML
    public void initialize() {
        ProfileController.getInstance().settingProfile(profileImage, player.getUser());
        text.setText(player.getUser().getUsername() + "'s score is: " + player.getUser().getScore());
        for (Player player1: Player.getAllPlayers()) {
            list.getChildren().add(new Label(player1.getUser().getUsername() + "'s score is: " + player1.getUser().getScore()));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = Main.loadFXML("end");
        root.getStylesheets().add(Main.class.getResource("/css/end.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Winner page");
        stage.show();
    }
}
