package viewControllers.Info;

import Main.Main;
import controller.UnitController;
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
import model.Player;
import model.Unit;
import Enum.*;
import viewControllers.GraphicOfGame;
import viewControllers.Menus;

public class UnitPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    public Button back;

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
        UnitPanel.player = player;
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

    private void showAlert(Message message) {
        if (message != Message.SUCCESS_WORK) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText(message.toString());
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success");
            alert.setContentText(message.toString());
            alert.show();
        }
    }

    private ContextMenu buildContextMenu(Unit unit) {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem sleep = new MenuItem("sleep");
        sleep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unit.setStatus(UnitStatus.SLEEP);
            }
        });

        MenuItem ready = new MenuItem("ready");
        ready.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unit.setStatus(UnitStatus.READY);
            }
        });

        MenuItem improving = new MenuItem("improving");
        improving.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unit.setStatus(UnitStatus.IMPROVING);
            }
        });

        MenuItem improvingForHealth = new MenuItem("improving for health");
        improvingForHealth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unit.setStatus(UnitStatus.HEALTH_IMPROVING);
            }
        });

        MenuItem established = new MenuItem("established");
        established.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                Message message = UnitController.established(unit);
                showAlert(message);
            }
        });

        MenuItem rangedFight = new MenuItem("ranged fight");
        rangedFight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextField textField = new TextField();
                textField.setPromptText("enter ground number for ranged fight");
                textField.setMaxWidth(300);
                list.getChildren().add(textField);
                textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                            String text = textField.getText();
                            try {
                                int number = Integer.parseInt(text);
                                Message message = UnitController.rangedFight(unit, number);
                                showAlert(message);
                                list.getChildren().remove(textField);
                            } catch (NumberFormatException e) {
                                textField.clear();
                                textField.setPromptText("invalid number");
                                textField.setStyle("-fx-prompt-text-fill: red");
                            }
                        }
                    }
                });
            }
        });

        MenuItem meleeFight = new MenuItem("melee fight");
        meleeFight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextField textField = new TextField();
                textField.setPromptText("enter ground number for melee fight");
                textField.setMaxWidth(300);
                list.getChildren().add(textField);
                textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                            String text = textField.getText();
                            try {
                                int number = Integer.parseInt(text);
                                Message message = UnitController.meleeFight(unit, number);
                                showAlert(message);
                                list.getChildren().remove(textField);
                            } catch (NumberFormatException e) {
                                textField.clear();
                                textField.setPromptText("invalid number");
                                textField.setStyle("-fx-prompt-text-fill: red");
                            }
                        }
                    }
                });
            }
        });

        MenuItem readyRangedFight = new MenuItem("ready to ranged fight");
        readyRangedFight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                Message message = UnitController.readyToRangedFight(unit);
                showAlert(message);
            }
        });

        MenuItem plundering = new MenuItem("plundering");
        plundering.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                Message message = UnitController.plundering(unit);
                showAlert(message);
            }
        });

        MenuItem deleteOrder = new MenuItem("delete one order");
        deleteOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!unit.getStatus().equals(UnitStatus.AWAKE)) unit.setStatus(UnitStatus.AWAKE);
                String message = UnitController.removeOneOrder(unit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText(message.toString());
                alert.show();
            }
        });

        MenuItem awake = new MenuItem("awake");
        awake.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (unit.getStatus().equals(UnitStatus.SLEEP)) unit.setStatus(UnitStatus.AWAKE);
            }
        });

        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UnitController.deleteUnit(unit);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("The unit is deleted successfully");
                alert.show();
                stage.close();
            }
        });

        contextMenu.getItems().add(sleep);
        contextMenu.getItems().add(ready);
        contextMenu.getItems().add(improving);
        contextMenu.getItems().add(improvingForHealth);
        contextMenu.getItems().add(established);
        contextMenu.getItems().add(rangedFight);
        contextMenu.getItems().add(meleeFight);
        contextMenu.getItems().add(readyRangedFight);
        contextMenu.getItems().add(plundering);
        contextMenu.getItems().add(deleteOrder);
        contextMenu.getItems().add(awake);
        contextMenu.getItems().add(delete);
        return contextMenu;
    }

    private void addToList() {
        for (Unit unit: player.getUnits()) {
            Label label = new Label("-unit type: " + unit.getMilitaryType().name() + " ground number: " +
                    unit.getGround().getNumber() + " status: " + unit.getStatus());
            settingHoverToEach(label);

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    buildContextMenu(unit).show(label, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            });
            list.getChildren().add(label);
        }

        if (player.getUnits().size() == 0) {
            Label text = new Label("-Sorry, but you haven't have any units, yet");
            list.getChildren().add(text);
        }
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
