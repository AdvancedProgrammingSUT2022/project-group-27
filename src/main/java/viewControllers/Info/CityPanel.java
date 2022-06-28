package viewControllers.Info;

import Main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.City;
import model.Ground;
import model.Player;
import model.Technology;
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

        //For Test
        City city = new City(Ground.getGroundByNumber(15), "happy", player);
        player.getCities().add(city);
        //
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
        MenuItem strength = new MenuItem("city Strength");
        strength.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                Scene scene = new Scene(new Label("city strength is: " + city.getCityStrength()));
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem remains = new MenuItem("how far it remains");
        remains.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                showRemainTimes(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        contextMenu.getItems().add(strength);
        contextMenu.getItems().add(remains);
        return contextMenu;
    }

    private void showRemainTimes(City city, VBox vBox) {
        vBox.getChildren().add(new Label("Technologies which remain:"));
        for (Technology technology: city.getPlayer().technologiesThatCanBeObtained()){
            if (city.getPlayer().getUnderConstructionTechnology() == null) {
                vBox.getChildren().add(new Label("nothing"));
                break;
            }
            if (technology.getTechnologyType().name().equals(city.getPlayer().getUnderConstructionTechnology().getTechnologyType().name()))
                vBox.getChildren().add(new Label(technology.getTechnologyType().name() + "remain time is: " +
                        technology.getTimeRemain()));
        }

        vBox.getChildren().add(new Label("Production which remains:"));
        if (city.getConstruction() != null) vBox.getChildren().add(new Label(city.getConstruction().name() +
                "remain time is: " + city.getRemainedTurnsToBuild()));
        if (city.getBuildingUnit() != null) vBox.getChildren().add(new Label(city.getBuildingUnit().name() + "remain time is: " +
                city.getRemainedTurnsToBuild()));
        if (city.getConstruction() == null && city.getBuildingUnit() == null) vBox.getChildren().add(new Label("nothing"));
    }

    private void addToList() {
        for (City city: player.getCities()) {
            Label label = new Label("Name city: " + city.getName() + " Ground number: " + city.getGround().getNumber());
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
