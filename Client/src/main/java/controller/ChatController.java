package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.ChatGroup;
import model.ChatText;
import model.Request;
import model.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatController {
    public static ArrayList<ChatGroup> getListOfAllChats() {
        Request request = new Request();
        request.setHeader("getListOfChats");
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<ChatGroup>>(){}.getType());
    }

    public static void addChatGroup(ChatGroup chatGroup) {
        Request request = new Request();
        request.setHeader("addChatGroup");
        request.addData("chatGroup", new Gson().toJson(chatGroup));
        Response response = NetworkController.send(request);
    }

    public static void newPrivateChat(String username, VBox rooms, VBox chat) {
        Label label = new Label();
        label.setText(username);
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(ProfileController.getInstance().getImage(username));
        HBox hBox = new HBox(circle, label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (Node node: rooms.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                }
                createChatBoxForPrivate(UserController.getInstance().getUserLoggedIn(), username, chat);
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
        ChatGroup chatGroup = new ChatGroup(new ArrayList<>(List.of(UserController.getInstance().getUserLoggedIn(), username)));
    }

    public static void newRoomChat(ArrayList<String> listOfUsers, String groupName, VBox rooms, VBox chat) {
        if (!listOfUsers.contains(UserController.getInstance().getUserLoggedIn())) return;
        Label label = new Label(groupName);
        Circle circle = new Circle(20);
        Image image = new Image(ChatController.class.getResource("/images/group icon.png").toExternalForm());
        circle.setFill(new ImagePattern(image));
        HBox hBox = new HBox(circle, label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(5);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for (Node node: rooms.getChildren()) {
                    node.setStyle("-fx-background-color: transparent;");
                }
                createChatBoxForRooms(UserController.getInstance().getUsername(), groupName, chat);
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
        ChatGroup chatRoom = new ChatGroup(listOfUsers, groupName);
    }

    public static void publicChat(VBox rooms, VBox chat) {
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
                }
                createChatBoxForRooms(UserController.getInstance().getUsername(), "public", chat);
                hBox.setStyle("-fx-background-color: rgba(175,174,174,0.55); -fx-background-radius: 8;");
            }
        });

        rooms.getChildren().add(hBox);
        ArrayList<String> users = UserController.getInstance().getListOfAllUsers();

        ChatGroup publicChat = new ChatGroup(users, "public");
    }

    public static void createChatBoxForRooms(String owner, String chatRoom, VBox chat) {
        chat.getChildren().clear();
        chat.setAlignment(Pos.BOTTOM_CENTER);
        ChatGroup chatGroup = ChatGroup.findChat(chatRoom);
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
                String text = typing.getText();
                typing.clear();
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                ChatText chatText = new ChatText(owner, chatGroup, time, text);
                //addChat(chatText, chat);
                showChats(chatGroup, chat);
            }
        });

        HBox hBox = new HBox(typing, circle);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        chat.getChildren().add(hBox);

        showChats(chatGroup, chat);
    }

    public static void createChatBoxForPrivate(String owner, String usernameReceiver, VBox chat) {
        chat.setAlignment(Pos.BOTTOM_CENTER);
        ChatGroup chatGroup = ChatGroup.findChat(owner, usernameReceiver);
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
                String text = typing.getText();
                typing.clear();
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                ChatText chatText = new ChatText(owner, chatGroup, time, text);
                //addChat(chatText, chat);
                showChats(chatGroup, chat);
            }
        });

        HBox hBox = new HBox(typing, circle);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        chat.getChildren().add(hBox);
        showChats(chatGroup, chat);
    }

    public static void showChats(ChatGroup chatGroup, VBox chat) {
        Node first = chat.getChildren().get(chat.getChildren().size() - 1);
        chat.getChildren().clear();
        chat.getChildren().add(first);

        for (ChatText chatText: chatGroup.getChats()) {
            addChat(chatText, chat, chatGroup);
        }
    }

    public static void addChat(ChatText chatText, VBox chat, ChatGroup chatGroup) {
        if (chatText.isDeleted() && chatText.getSender().equals(UserController.getInstance().getUserLoggedIn())) return;
        if (!chatText.getSender().equals(UserController.getInstance().getUserLoggedIn())) chatText.setSeen(true);

        Text text = new Text();
        String seenMessage = chatText.getSender() + ": " + chatText.getTime() + "\n" + chatText.getText();
        text.setText(seenMessage);
        Circle tick = new Circle(10);
        Image imageTick;
        if (!chatText.isSeen()) imageTick= new Image(ChatController.class.getResource("/images/tick.png").toExternalForm());
        else imageTick = new Image(ChatController.class.getResource("/images/double tick.png").toExternalForm());
        tick.setFill(new ImagePattern(imageTick));
        Circle avatar = new Circle();
        avatar.setRadius(20);
        avatar.setFill(ProfileController.getInstance().getImage(chatText.getSender()));

        HBox hBox = new HBox(tick, text, avatar);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setSpacing(5);

        ContextMenu contextMenu = buildContextMenu(hBox, chat, chatText, text, chatGroup);
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY && chatText.getSender().equals(UserController.getInstance().getUserLoggedIn())) {
                    contextMenu.show(hBox, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                }
            }
        });

        chat.getChildren().add(chat.getChildren().size() - 1, hBox);
    }

    private static ContextMenu buildContextMenu(HBox hBox, VBox chat, ChatText chatText, Text text, ChatGroup chatGroup) {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteForAll = new MenuItem("delete for everyone");
        deleteForAll.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                chatText.delete();
                chat.getChildren().remove(hBox);
                showChats(chatGroup, chat);
            }
        });

        MenuItem deleteForYou = new MenuItem("delete just for yourself");
        deleteForYou.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chatText.setDeleted(true);
                chat.getChildren().remove(hBox);
                showChats(chatGroup, chat);
            }
        });

        MenuItem edit = new MenuItem("edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextField textField = new TextField();
                textField.setPromptText("edit message here");
                hBox.getChildren().add(0, textField);
                hBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                            chatText.setText(textField.getText());
                            hBox.getChildren().remove(textField);
                            String newMessage = chatText.getSender() + ": " + chatText.getTime() + "\n" + chatText.getText();
                            text.setText(newMessage);
                            showChats(chatGroup, chat);
                        }
                    }
                });
            }
        });

        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(deleteForAll);
        contextMenu.getItems().add(deleteForYou);
        return contextMenu;
    }
}
