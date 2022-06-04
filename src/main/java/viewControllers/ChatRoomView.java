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
import view.Menu;

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
        firstInitialize();
        //for (int i = 0; i < 24; i++) ChatController.newPrivateChat(Menu.getLoggedInUser(), rooms);
    }

    private void firstInitialize() {
        chat.getChildren().clear();
        chat.setAlignment(Pos.CENTER);
        Text textChat = new Text("please select one of chats...");
        chat.getChildren().add(textChat);

        ChatController.publicChat(rooms); //TODO add other chats that user have
        /*TextField textField1 = new TextField();
        textField1.setPromptText("chat");
        chat.getChildren().add(textField1);

        Label label = new Label("hello");
        chat.getChildren().add(chat.getChildren().size() - 1, label);
        Label label1 = new Label("hi");
        chat.getChildren().add(chat.getChildren().size() - 1, label1);*/
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
