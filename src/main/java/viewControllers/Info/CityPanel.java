package viewControllers.Info;

import Main.Main;
import controller.BuildCityController;
import controller.CityMenuController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import viewControllers.GraphicOfGame;
import viewControllers.Menus;
import Enum.*;

import java.util.ArrayList;

public class CityPanel extends Menus {
    private static Stage stage;
    private static Player player;
    private CityMenuController controller;
    private BuildCityController buildController;
    private static GraphicOfGame game;

    @FXML
    private Button back;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        controller = new CityMenuController();
        buildController = new BuildCityController();

        Label firstText = new Label("list of cities you have:");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        //For Test
        if (player.getCities().size() == 0) {
            City city = new City(Ground.getGroundByNumber(15), "happy", player);
            player.getCities().add(city);
        }
        //
        addToList();
        back.setCursor(Cursor.HAND);
    }

    @Override
    public void stop() throws Exception {
        game.initializing();
        super.stop();
    }

    public static void setPlayer(Player player) {
        CityPanel.player = player;
    }

    public static void setGame(GraphicOfGame game) {
        CityPanel.game = game;
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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                game.initializing();
            }
        });
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

        MenuItem buyGround = new MenuItem("buy ground");
        buyGround.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                buyGround(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem buy = new MenuItem("let's buy");
        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                buyThing(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem fight = new MenuItem("fight to ground...");
        fight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                fightToGround(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem buildUnit = new MenuItem("build unit");
        buildUnit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                buildUnit(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem buildBuilding = new MenuItem("build building");
        buildBuilding.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                buildBuilding(city, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem changeConstruction = new MenuItem("change construction");
        changeConstruction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                changeConstruction(city, vBox);
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
        contextMenu.getItems().add(buyGround);
        contextMenu.getItems().add(buy);
        contextMenu.getItems().add(fight);
        contextMenu.getItems().add(buildUnit);
        contextMenu.getItems().add(buildBuilding);
        contextMenu.getItems().add(changeConstruction);
        return contextMenu;
    }

    private void changeConstruction(City city, VBox vBox) {
        ArrayList<String> construction = new ArrayList<>();
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (controller.canWeHaveThisUnitType(militaryType, city)) construction.add(militaryType.name());
        }

        for (BuildingsType buildingsType: BuildingsType.values()) {
            if (controller.canWeHaveThisBuildingType(buildingsType, city)) construction.add(buildingsType.name());
        }

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(construction));
        vBox.getChildren().add(comboBox);

        comboBox.setOnAction((event) -> {
            String selection = comboBox.getSelectionModel().getSelectedItem();
            Message message = buildController.changeConstruction(city, selection);
            showAlert(message);
        });
    }

    private void buildBuilding(City city, VBox vBox) {
        ArrayList<String> building = new ArrayList<>();
        for (BuildingsType buildingsType: BuildingsType.values()) {
            if (controller.canWeHaveThisBuildingType(buildingsType, city)) building.add(buildingsType.name());
        }

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(building));
        vBox.getChildren().add(comboBox);

        comboBox.setOnAction((event) -> {
            String selection = comboBox.getSelectionModel().getSelectedItem();
            Message message = buildController.buildBuilding(city, selection);
            showAlert(message);
        });
    }

    private void buildUnit(City city, VBox vBox) {
        ArrayList<String> unit = new ArrayList<>();
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (controller.canWeHaveThisUnitType(militaryType, city)) unit.add(militaryType.name());
        }

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(unit));
        vBox.getChildren().add(comboBox);

        comboBox.setOnAction((event) -> {
            String selection = comboBox.getSelectionModel().getSelectedItem();
            Message message = buildController.buildUnit(city, selection);
            showAlert(message);
        });
    }

    private void fightToGround(City city, VBox vBox) {
        TextField textField = new TextField();
        textField.setPromptText("enter ground number which you want to fight with");
        textField.setMaxWidth(300);
        vBox.getChildren().add(textField);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String text = textField.getText();
                    try {
                        int number = Integer.parseInt(text);
                        Ground ground = Ground.getGroundByNumber(number);
                        if (ground == null) showAlert(Message.INVALID_GROUND_NUMBER);
                        else {
                            city.combat(ground);
                            showAlert(Message.FIGHTING_START);
                        }
                    } catch (NumberFormatException e) {
                        textField.clear();
                        textField.setPromptText("invalid number format");
                        textField.setStyle("-fx-prompt-text-fill: red");
                    }
                }
            }
        });
    }

    private void buyThing(City city, VBox vBox) {
        ArrayList<String> production = new ArrayList<>();
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (controller.canWeHaveThisUnitType(militaryType, city)) production.add(militaryType.name());
        }

        for (BuildingsType buildingsType: BuildingsType.values()) {
            if (controller.canWeHaveThisBuildingType(buildingsType, city)) production.add(buildingsType.name());
        }

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(production));
        vBox.getChildren().add(comboBox);

        comboBox.setOnAction((event) -> {
            String selection = comboBox.getSelectionModel().getSelectedItem();
            Message message = controller.buyThings(city, selection);
            showAlert(message);
        });
    }

    private void buyGround(City city, VBox vBox) {
        TextField textField = new TextField();
        textField.setPromptText("enter ground number which you want to buy");
        textField.setMaxWidth(300);
        vBox.getChildren().add(textField);
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    String text = textField.getText();
                    try {
                        int number = Integer.parseInt(text);
                        Message message = controller.buyGround(city, number);
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
        Label food = new Label("How much food does this city store? " + city.getSavedFood());
        Label production = new Label("How much production does this city have? " + city.getProduction());
        Label gold = new Label("How much gold does this city have? " + city.getGold());
        Label science = new Label("How much science does this city have? " + city.getScience());

        Image foodImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/food.jpg").toExternalForm());
        ImageView foodIcon = new ImageView(foodImage);
        foodIcon.setFitWidth(50);
        foodIcon.setFitHeight(50);
        food.setGraphic(foodIcon);

        Image productionImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/production.jpeg").toExternalForm());
        ImageView productionIcon = new ImageView(productionImage);
        productionIcon.setFitWidth(50);
        productionIcon.setFitHeight(50);
        production.setGraphic(productionIcon);

        Image coin = new Image(GraphicOfGame.class.getResource("/iconsOfGame/coinIcon.png").toExternalForm());
        ImageView coinIcon = new ImageView(coin);
        coinIcon.setFitWidth(50);
        coinIcon.setFitHeight(50);
        gold.setGraphic(coinIcon);

        Image scienceImage = new Image(GraphicOfGame.class.getResource("/iconsOfGame/science.png.png").toExternalForm());
        ImageView scienceIcon = new ImageView(scienceImage);
        scienceIcon.setFitHeight(50);
        scienceIcon.setFitWidth(50);
        science.setGraphic(scienceIcon);

        vBox.getChildren().add(food);
        vBox.getChildren().add(production);
        vBox.getChildren().add(gold);
        vBox.getChildren().add(science);
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
        game.initializing();
    }
}
