package viewControllers.Info;

import Main.Main;
import controller.CityMenuController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import viewControllers.Menus;
import Enum.*;

import java.util.ArrayList;

public class CityPanel extends Menus {
    private static Stage stage;
    private static Player player;
    private CityMenuController controller;

    @FXML
    private Button back;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        controller = new CityMenuController();

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

        MenuItem lock = new MenuItem("lock a citizen to ground");
        lock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                lockCitizen(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem removeWork = new MenuItem("remove a citizen from work");
        removeWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                removeFromWork(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem outputOfCity = new MenuItem("output of city");
        outputOfCity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                showOutputOfCity(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem outputOfCivilization = new MenuItem("output of civilization");
        outputOfCivilization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                showOutputOfCivilization(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem withOutWork = new MenuItem("with out work citizens");
        withOutWork.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                showWithOutWork(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        contextMenu.getItems().add(strength);
        contextMenu.getItems().add(remains);
        contextMenu.getItems().add(lock);
        contextMenu.getItems().add(removeWork);
        contextMenu.getItems().add(outputOfCity);
        contextMenu.getItems().add(outputOfCivilization);
        contextMenu.getItems().add(withOutWork);
        return contextMenu;
    }

    private void showWithOutWork(City city, VBox vBox) {
        ArrayList<Citizen> listOfWithoutWork = city.withoutWorkCitizens();

        int index = 1;
        for (Citizen citizen: listOfWithoutWork) {
            vBox.getChildren().add(new Label(index + "- citizen id: " + citizen.getId()));
            index++;
        }
    }

    private void showOutputOfCivilization(City city, VBox vBox) {
        vBox.getChildren().add(new Label("Science per turn by this city: " + city.getScience()));
        vBox.getChildren().add(new Label("Gold of civilization produced by this city: " + city.getGold()));
        vBox.getChildren().add(new Label("The city production: " + city.getProduction()));
        vBox.getChildren().add(new Label("Happiness of civilization: " + player.getHappiness()));
        vBox.getChildren().add(new Label("Food produce per turn: " + city.getFoodPerTurn()));
        showStrategicResources(city, vBox);
    }

    private void showStrategicResources(City city, VBox vBox) {
        ArrayList<StrategicResource> listOfStrategicResource = new ArrayList<>();
        for (Ground ground : city.getRangeOfCity()) {
            listOfStrategicResource.addAll(ground.getStrategicResources());
        }

        vBox.getChildren().add(new Label("Strategic resources of city:"));
        int index = 1;
        for (StrategicResource strategicResource : listOfStrategicResource) {
            vBox.getChildren().add(new Label(index + ": " + strategicResource));
            index++;
        }
    }

    private void showOutputOfCity(City city, VBox vBox) {
        vBox.getChildren().add(new Label("How much food does this city store? " + city.getSavedFood()));
        vBox.getChildren().add(new Label("How much production does this city have? " + city.getProduction()));
        vBox.getChildren().add(new Label("How much gold does this city have? " + city.getGold()));
        vBox.getChildren().add(new Label("How much science does this city have? " + city.getScience()));
    }

    private void removeFromWork(City city, VBox vBox) {
        TextField textField = new TextField();
        textField.setPromptText("enter ground for removing a citizen from work");
        textField.setMaxWidth(300);
        vBox.getChildren().add(textField);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String text = textField.getText();
                    try {
                        int number = Integer.parseInt(text);
                        Message message = controller.removeFromWork(city, number);
                        showAlert(message);
                    } catch (NumberFormatException e) {
                        textField.clear();
                        textField.setPromptText("invalid number format");
                        textField.setStyle("-fx-prompt-text-fill: red");
                    }
                }
            }
        });
    }

    private void lockCitizen(City city, VBox vBox) {
        TextField textField = new TextField();
        textField.setPromptText("enter ground number for locking a citizen");
        textField.setMaxWidth(300);
        vBox.getChildren().add(textField);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String text = textField.getText();
                    try {
                        int number = Integer.parseInt(text);
                        Message message = controller.lockCitizenToGround(city, number);
                        showAlert(message);
                    } catch (NumberFormatException e) {
                        textField.clear();
                        textField.setPromptText("invalid number format");
                        textField.setStyle("-fx-prompt-text-fill: red");
                    }
                }
            }
        });
    }

    private void showRemainTimes(City city, VBox vBox) {
        vBox.getChildren().add(new Label("Technologies which remain:"));
        for (Technology technology: city.getPlayer().technologiesThatCanBeObtained()) {
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
