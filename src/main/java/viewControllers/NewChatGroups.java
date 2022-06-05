package viewControllers;

import Main.Main;
import controller.ChatController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class NewChatGroups extends Application {
    private static Stage stage;
    private static boolean isRoom;
    private ComboBox<String> comboBox;
    private static VBox chat;
    private static VBox rooms;

    @FXML
    private Button submit;

    @FXML
    private VBox box;

    @FXML
    public void initialize() {
        submit.setCursor(Cursor.HAND);
        box.setPadding(new Insets(20));
        List<String> users = new ArrayList<>();
        System.out.println(isRoom);
        if (!isRoom) {
            for (User user: User.getListOfUsers()) {
                users.add(user.getUsername());
            }
            ComboBox<String> comboBox = new ComboBox<>();
            this.comboBox = comboBox;
            comboBox.getItems().addAll(users);
            comboBox.setPrefWidth(200);
            comboBox.setPromptText("Enter one user below");
            box.getChildren().add(0, comboBox);
        } else {
            
        }
    }

    public void setIsRoom(boolean isRoom) {
        NewChatGroups.isRoom = isRoom;
    }

    public void setRooms(VBox rooms) {
        NewChatGroups.rooms = rooms;
    }

    public void setChat(VBox chat) {
        NewChatGroups.chat = chat;
    }

    @Override
    public void start(Stage stage) throws Exception {
        NewChatGroups.stage = stage;
        Parent root = Main.loadFXML("new-chat-room");
        root.getStylesheets().add(Main.class.getResource("/css/chatRoom.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Build New Chat Room");
        stage.show();
    }

    public void submit(MouseEvent mouseEvent) {
        if (!isRoom && comboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("invalid user");
            alert.setContentText("sorry, but you don't choose user");
            alert.show();
        } else if (!isRoom) {
            User user = User.findUser(comboBox.getValue());
            ChatController.newPrivateChat(user, rooms, chat);
            stage.close();
        }
        else stage.close();
    }
}
