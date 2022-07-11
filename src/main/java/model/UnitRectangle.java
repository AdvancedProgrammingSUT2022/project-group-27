package model;


import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class UnitRectangle extends Circle {
    private Unit unit;

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
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
                    }
                }
            }
        });
    }

}
