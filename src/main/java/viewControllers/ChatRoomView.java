package viewControllers;

import Main.Main;
import controller.ProfileController;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
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
        newPrivateChat(Menu.getLoggedInUser());
        newPrivateChat(Menu.getLoggedInUser());
    }

    private void newPrivateChat(User user) {
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
