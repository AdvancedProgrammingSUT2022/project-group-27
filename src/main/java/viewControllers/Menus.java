package viewControllers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public abstract class Menus extends Application {
    protected void settingHoverToEach(Label ourLabel) {
        ourLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-radius: 5; -fx-background-color: rgba(93,93,93,0.39); -fx-text-fill: white;");
            }
        });
        ourLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ourLabel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.61); -fx-text-fill: black;");
            }
        });
    }
}
