package model;


import Enum.ImprovementType;
import controller.UserController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import viewControllers.GraphicOfGame;

import java.util.ArrayList;

public class UnitRectangle extends Circle {
    private Unit unit;
    private static HBox hbox;
    private static Unit unitSelect = null;
    private static Stage stage = new Stage();

    public Unit getUnit() {
        return unit;
    }

    public UnitRectangle(Unit unit){
        this.unit=unit;
        if (unit instanceof MilitaryUnit) this.setLayoutX(unit.getGround().getyLocation()-30);
        else this.setLayoutX(unit.getGround().getyLocation()+30);
        this.setLayoutY(unit.getGround().getxLocation()+30);
        this.setRadius(30);
       // this.setFill(Color.AQUA);
        this.setStroke(unit.getStatus().getColor());
        this.setStrokeWidth(5);
        this.setFill(unit.getMilitaryType().getImage());
        if (unit.getHp() < 9.7)
            setEffect(new InnerShadow((int) ((10 - unit.getHp()) * 5),1,1,Color.RED));
        hbox = new HBox();
        unitSelect = null;
        stage.close();
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Circle circle = new Circle();
                circle.setFill(unit.getMilitaryType().getImage());
                circle.setRadius(30);
                Label label = new Label("unit name: " + unit.getMilitaryType().name() +
                        " combat type: " + unit.getMilitaryType().getCombatType() +
                        "\ncombat strength: " + unit.getMilitaryType().getCombatStrength() +
                        " range and ranged strength: " + unit.getMilitaryType().getRange() + ", "
                        + unit.getMilitaryType().getRangedCombatStrength() +
                        "\nmp: " + unit.getMp() + " hp: " + unit.getHp() +
                        " status: " + unit.getStatus());

                hbox = new HBox(circle, label);
                if (unit instanceof SettlerUnit) addingSettler((SettlerUnit) unit);
                unitSelect = unit;

                if (unit instanceof Worker) setContextMenu((Worker) unit).show(UnitRectangle.this, mouseEvent.getScreenX(), mouseEvent.getScreenY());

                if (unit.getPlayer() != Player.getPlayerByUser(UserController.getInstance().getUsername())) {
                    hbox = new HBox();
                    unitSelect = null;
                    stage.close();
                }

                if (GlobalVariables.unitRectangle==null){
                    GlobalVariables.unitRectangle=UnitRectangle.this;
                    UnitRectangle.this.setStroke(Color.RED);
                }
                else{
                    GlobalVariables.unitRectangle.setStroke(Color.WHITE);
                    if (GlobalVariables.unitRectangle!=UnitRectangle.this){
                        GlobalVariables.unitRectangle=UnitRectangle.this;
                        GlobalVariables.unitRectangle.setStroke(Color.RED);
                    }
                    else{
                        GlobalVariables.unitRectangle=null;
                        hbox = new HBox();
                        unitSelect = null;
                        stage.close();
                    }
                }

                Scene scene = new Scene(hbox);
                stage.setScene(scene);
                if (!stage.isShowing() && unitSelect != null) {
                    stage.show();
                }
            }
        });

        setHoverForWorker(unit);
    }

    private void alertSuccess(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setContentText(name + " happens successfully");
        alert.show();
    }

    private ContextMenu setContextMenu(Worker worker) {
        final ContextMenu contextMenu = new ContextMenu();

        MenuItem clearLand = new MenuItem("clear land");
        clearLand.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                worker.setWorking(true);
                controller.clearLand(worker.getGround());
                alertSuccess("clear land");
            }
        });

        MenuItem buildRoad = new MenuItem("build road");
        buildRoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                worker.setWorking(true);
                controller.buildRoad(worker.getGround());
                alertSuccess("build road");
            }
        });

        MenuItem buildRailway = new MenuItem("build railway");
        buildRailway.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                worker.setWorking(true);
                controller.buildRailway(worker.getGround());
                alertSuccess("build railway");
            }
        });

        MenuItem improvementMenu = new MenuItem("improvement menu");
        improvementMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Stage preStage = new Stage();
                VBox vBox = new VBox();
                setImprovementMenu(worker, vBox);
                Scene scene = new Scene(vBox);
                preStage.setScene(scene);
                preStage.initOwner(stage);
                preStage.show();
            }
        });

        MenuItem delete = new MenuItem("delete road and railway");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                worker.setWorking(true);
                worker.getGround().deleteRoadAndRailway();
                alertSuccess("delete road and railway");
            }
        });

        MenuItem repair = new MenuItem("repair");
        repair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (worker.getGround().getImprovementType()==null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("No Improvement");
                    alert.setContentText("Unfortunately, you don't have any improvement here");
                    alert.show();
                } else {
                    worker.setWorking(true);
                    UnitController.freePlundering(worker);
                    alertSuccess("repair");
                }
            }
        });

        contextMenu.getItems().add(clearLand);
        contextMenu.getItems().add(buildRoad);
        contextMenu.getItems().add(buildRailway);
        contextMenu.getItems().add(improvementMenu);
        contextMenu.getItems().add(delete);
        contextMenu.getItems().add(repair);
        return contextMenu;
    }

    private void setImprovementMenu(Worker worker, VBox vBox) {
        Improvement improvementInProgress = worker.getGround().getImprovementTypeInProgress();

        if (improvementInProgress != null) vBox.getChildren().add(new Label("The improvement that you have here is: " +
                improvementInProgress.getImprovementType().name() + "turns in progress: " + improvementInProgress.getTurnRemained())); //TODO test if .name work as we want

        ArrayList<ImprovementType> list = worker.getGround().listOfImprovementTypes();
        Button button = new Button("list of improvement");
        vBox.getChildren().add(button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                improvementContextMenu(list, worker).show(button, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });
    }

    private ContextMenu improvementContextMenu(ArrayList<ImprovementType> list, Worker worker) {
        final ContextMenu contextMenu = new ContextMenu();

        for (int i = 0; i < list.size(); i++){
            MenuItem menuItem = new MenuItem(list.get(i).name());
            Integer I = i;
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    worker.getGround().setImprovementTypeInProgress(list.get(I));
                    worker.setWorking(true);
                }
            });

            contextMenu.getItems().add(menuItem);
        }

        return contextMenu;
    }

    private void setHoverForWorker(Unit unit) {
        if (!(unit instanceof Worker)) return;
        Stage stage = new Stage();
        stage.setMinHeight(100);
        stage.setMinWidth(100);

        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Improvement improvement = unit.getGround().getImprovementTypeInProgress();
                Label label;
                if (improvement == null) label = new Label("nothing");
                else label = new Label(improvement.getImprovementType().name() + ": " + improvement.getTurnRemained());

                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-background-color: Black; -fx-text-fill: white");
                stage.setScene(new Scene(label));
                stage.show();
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });
    }

    private void addingSettler(SettlerUnit settlerUnit) {
        Button button = new Button("create city");
        button.setDisable(City.findCityByGround(unit.getGround(), unit.getPlayer()) != null);

        hbox.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hbox.getChildren().remove(button);
                TextField textField = new TextField();
                textField.setPromptText("enter city name");
                hbox.getChildren().add(textField);
                textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            settlerUnit.buildCity(textField.getText());
                            stage.close();
                            GraphicOfGame.showMap();
                            alertSuccess("create city");
                        }
                    }
                });
            }
        });
    }
}
