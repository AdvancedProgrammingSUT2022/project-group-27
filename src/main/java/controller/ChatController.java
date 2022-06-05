package controller;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.ChatGroup;
import model.ChatText;
import model.User;
import view.Menu;

import java.util.ArrayList;
import java.util.List;

public class ChatController {
    public static void newPrivateChat(User user, VBox rooms, VBox chat) {
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
                    createChatBox(Menu.getLoggedInUser(), user.getUsername(), chat);
                }
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
        ChatGroup chatGroup = new ChatGroup(new ArrayList<>(List.of(Menu.getLoggedInUser(), user)));
    }

    public static void publicChat(VBox rooms) {
        Label label = new Label("public chat");
        Circle circle = new Circle(20);
        Image image = new Image(ChatController.class.getResource("/images/publicChat.png").toExternalForm());
        circle.setFill(new ImagePattern(image));
        HBox hBox = new HBox(circle, label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (Node node: rooms.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                    //TODO createChatBox
                }
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
    }

    public static void createChatBox(User owner, String usernameReceiver, VBox chat) {
        chat.getChildren().clear();
        chat.setAlignment(Pos.BOTTOM_CENTER);
        User receiver = User.findUser(usernameReceiver);
        ChatGroup chatGroup = ChatGroup.findChat(owner, receiver);
        if (chatGroup == null) return;

        TextField typing = new TextField();
        typing.setPromptText("Message");
        typing.setPrefWidth(400);
        Circle circle = new Circle(20);
        Image image = new Image(ChatController.class.getResource("/images/sender icon.jpg").toExternalForm());
        circle.setFill(new ImagePattern(image));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("send");
            }
        });

        HBox hBox = new HBox(typing, circle);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        chat.getChildren().add(hBox);

        for (ChatText chatText: chatGroup.getChats()) {
            addChat(chatGroup, chatText, chat);
        }
    }

    public static void addChat(ChatGroup chatGroup, ChatText chatText, VBox chat) {
        System.out.println("adding");
    }
}
