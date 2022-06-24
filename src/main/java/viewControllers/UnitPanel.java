package viewControllers;

import Main.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;
import model.Unit;

public class UnitPanel extends Menus {
    private static Stage stage;
    private static Player player;
    private static GraphicOfGame game;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        Label firstText = new Label("list of technologies you have:");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        addToList();
    }

    public static void setPlayer(Player player) {
        UnitPanel.player = player;
    }

    public static void setGame(GraphicOfGame game) {
        UnitPanel.game = game;
    }

    @Override
    public void start(Stage stage) throws Exception {
        UnitPanel.stage = stage;
        Parent root = Main.loadFXML("unit-menu");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Unit Menu");
        stage.show();
    }

    private void addToList() {
        for (Unit unit: player.getUnits()) {
            Label label = new Label("-unit type: " + unit.getMilitaryType().name() + " ground number: " +
                    unit.getGround().getNumber() + " status: " + unit.getStatus());
            settingHoverToEach(label);

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                }
            });
            list.getChildren().add(label);
        }

        if (player.getUnits().size() == 0) {
            Label text = new Label("-Sorry, but you haven't have any units, yet");
            list.getChildren().add(text);
        }
    }
}
