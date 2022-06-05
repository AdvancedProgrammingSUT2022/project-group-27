package viewControllers;

import Main.Main;
import controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ChatGroup;
import model.User;
import view.Menu;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

    @FXML
    private Button newChatRoom;

    @FXML
    private VBox chat;

    @FXML
    private VBox rooms;

    @FXML
    private Button back;

    @FXML
    public void initialize() {
        audio.play();
        audio.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                audio.seek(Duration.ZERO);
            }
        });

        back.setCursor(Cursor.HAND);
        newChatRoom.setCursor(Cursor.HAND);
        //rooms.setStyle("-fx-border-color: rgba(128,128,128,0.49); -fx-border-insets: 5; -fx-border-width: 1; -fx-border-style: dashed"); TODO do we want border for it
        firstInitialize();
        //ChatController.newPrivateChat(Menu.getLoggedInUser(), rooms, chat);
        //ChatController.newRoomChat(new ArrayList<>(List.of(User.findUser("a"), User.findUser("b"), User.findUser("c"))), "friends", rooms, chat);
    }

    private void firstInitialize() {
        chat.getChildren().clear();
        chat.setAlignment(Pos.CENTER);
        Text textChat = new Text("please select one of chats...");
        chat.getChildren().add(textChat);

        ChatController.publicChat(rooms, chat);
        for (ChatGroup chatGroup: ChatGroup.listOfGroupsWithUser(Menu.getLoggedInUser())) {
            if (chatGroup.getName().equals("")) {
                User user = chatGroup.getOtherUser(Menu.getLoggedInUser());
                ChatController.newPrivateChat(user, rooms, chat);
            } else if (chatGroup.getName().equals("public"));
            else {
                ChatController.newRoomChat(chatGroup.getListOfUsers(), chatGroup.getName(), rooms, chat);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ChatRoomView.stage = stage;
        audio = Main.loadingAudio("menu.m4a");
        Parent root = Main.loadFXML("chat-room");
        root.getStylesheets().add(Main.class.getResource("/css/chatRoom.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat Room");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        audio.stop();
        MainMenuView mainMenuView = new MainMenuView();
        mainMenuView.start(stage);
    }

    public void addingChatRoom(MouseEvent mouseEvent) {

    }
}
