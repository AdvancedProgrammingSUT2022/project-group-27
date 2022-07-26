package viewControllers.Info;

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
import model.Unit;
import viewControllers.Main;
import viewControllers.Menus;

public class MilitaryOverView extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private VBox list;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        Label firstText = new Label("List of all units: ");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        MilitaryOverView.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        MilitaryOverView.stage = stage;
        Parent root = Main.loadFXML("military-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Military Overview");
        stage.show();
    }

    public void setList() {
        for (Unit unit: player.getUnits()) {
            list.getChildren().add(new Label(unit.name()));
        }
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
