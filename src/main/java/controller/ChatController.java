package controller;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.User;

public class ChatController {
    public static void newPrivateChat(User user, VBox rooms) {
        Label label = new Label();
        label.setText(user.getUsername());
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(ProfileController.getInstance().getImage(user));
        HBox hBox = new HBox(circle, label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (Node node: rooms.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                }
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
    }
}
