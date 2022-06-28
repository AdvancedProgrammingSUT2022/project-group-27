package viewControllers.Info;

import Main.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.City;
import model.Player;
import model.Unit;
import viewControllers.Menus;

public class CityPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button back;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        Label firstText = new Label("list of technologies you have:");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        addToList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        CityPanel.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        CityPanel.stage = stage;
        Parent root = Main.loadFXML("city-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("City Panel");
        stage.show();
    }

    private ContextMenu buildContextMenu(City city) {
        final ContextMenu contextMenu = new ContextMenu();
        
        return contextMenu;
    }

    private void addToList() {
        for (City city: player.getCities()) {
            Label label = new Label("Name city: " + city.getName() + " City strength in combats: " + city.getCityStrength());
            settingHoverToEach(label);

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    buildContextMenu(city).show(label, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            });
            list.getChildren().add(label);
        }

        if (player.getCities().size() == 0) {
            Label text = new Label("-Sorry, but you haven't have any cities, yet");
            list.getChildren().add(text);
        }
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
