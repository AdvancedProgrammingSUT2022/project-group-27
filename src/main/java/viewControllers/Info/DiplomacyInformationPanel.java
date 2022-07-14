package viewControllers.Info;

import Main.Main;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;

public class DiplomacyInformationPanel extends Application {
    private static Stage stage;
    private static Player player;

    @FXML
    private VBox list;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        Label firstText = new Label("your score: " + player.countScore());
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        DiplomacyInformationPanel.player = player;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        Parent root = Main.loadFXML("diplomacy-information-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Diplomacy information panel");
        stage.show();
    }

    private void setList() {
        //TODO show list with peace or in war positions
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
