package viewControllers.Info;

import Main.Main;
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
import viewControllers.Menus;

public class DemographicPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button back;

    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {
        Label firstText = new Label("your demography:");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        vBox.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DemographicPanel.stage = stage;
        Parent root = Main.loadFXML("demographic-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Demographic Panel");
        stage.show();
    }

    public static void setPlayer(Player player) {
        DemographicPanel.player = player;
    }

    public void setList() {
        vBox.getChildren().add(new Label("how many city do you have? " + player.getCities().size()));
        vBox.getChildren().add(new Label("how many gold do you have? " + player.getGold()));
        vBox.getChildren().add(new Label("how many science do you have? " + player.getScience()));
        vBox.getChildren().add(new Label("how many food do you store? " + player.getFood()));
        vBox.getChildren().add(new Label("how many units do you have? " + player.getUnits().size()));
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
