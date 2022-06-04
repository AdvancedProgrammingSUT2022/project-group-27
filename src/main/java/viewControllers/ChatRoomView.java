package viewControllers;

import Main.Main;
import controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.Menu;

public class ChatRoomView extends Application {
    private static Stage stage;
    private static MediaPlayer audio;

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
        testingHBBoxes();
        ChatController.newPrivateChat(Menu.getLoggedInUser(), rooms);
        for (int i = 0; i < 24; i++) ChatController.newPrivateChat(Menu.getLoggedInUser(), rooms);
    }

    private void testingHBBoxes() {
        TextField textField1 = new TextField();
        textField1.setPromptText("chat");
        chat.getChildren().add(textField1);
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
}
