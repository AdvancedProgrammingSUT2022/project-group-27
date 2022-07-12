package model;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import viewControllers.GraphicOfGame;

public class UnitRectangle extends Circle {
    private Unit unit;
    private static HBox hbox;
    private static Unit unitSelect = null;
    private static Stage stage = new Stage();

    public Unit getUnit() {
        return unit;
    }

    public UnitRectangle (Unit unit){
        this.unit=unit;
        if (unit instanceof MilitaryUnit) this.setLayoutX(unit.getGround().getyLocation()-30);
        else this.setLayoutX(unit.getGround().getyLocation()+30);
        this.setLayoutY(unit.getGround().getxLocation()+30);
        this.setRadius(30);
       // this.setFill(Color.AQUA);
        this.setFill(unit.getMilitaryType().getImage());
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

                if (unit.getPlayer() != Player.whichPlayerTurnIs()) {
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
                            //TODO show result by popup
                        }
                    }
                });
            }
        });
    }
}
