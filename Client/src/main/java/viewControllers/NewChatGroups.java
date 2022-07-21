package viewControllers;

import controller.ChatController;
import controller.UserController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ChatGroup;

import java.util.ArrayList;
import java.util.List;

public class NewChatGroups extends Application {
    private static Stage stage;
    private static boolean isRoom;
    private ComboBox<String> comboBox;
    private TextField name;
    private static VBox chat;
    private static VBox rooms;
    private final ArrayList<String> multiUser = new ArrayList<>();

    @FXML
    private Button submit;

    @FXML
    private VBox box;

    @FXML
    public void initialize() {
        submit.setCursor(Cursor.HAND);
        box.setPadding(new Insets(20));
        List<String> users = new ArrayList<>();

        if (!isRoom) {
            users = UserController.getInstance().getListOfAllUsers();
            users.remove(UserController.getInstance().getUsername());
            ComboBox<String> comboBox = new ComboBox<>();
            this.comboBox = comboBox;
            comboBox.getItems().addAll(users);
            comboBox.setPrefWidth(200);
            comboBox.setPromptText("Enter one user below");
            box.getChildren().add(0, comboBox);
        } else {
            TextField name = new TextField();
            this.name = name;
            name.setPromptText("Enter your room's name.");

            Menu menu = new Menu();
            addingMenu(menu);
            MenuBar menuBar = new MenuBar(menu);

            Button usersList = new Button("choice users.");
            usersList.setCursor(Cursor.HAND);
            usersList.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    menu.show();
                }
            });

            HBox hBox = new HBox(menuBar, usersList);
            hBox.setAlignment(Pos.CENTER);

            box.getChildren().add(0, name);
            box.getChildren().add(0, hBox);
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

    private void addingMenu(Menu menu) {
        ArrayList<String> users = UserController.getInstance().getListOfAllUsers();
        for (String user: users) {
            if (!user.equals(UserController.getInstance().getUsername())) {
                CheckMenuItem checkMenuItem = new CheckMenuItem(user);
                menu.getItems().add(checkMenuItem);
                checkMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (!multiUser.contains(user)) multiUser.add(user);
                        else multiUser.remove(user);
                    }
                });
            }
        }
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
            String user = comboBox.getValue();
            ChatController.newPrivateChat(user, rooms, chat);
            stage.close();
        } else if (multiUser.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("no user");
            alert.setContentText("sorry, but you don't choose any user");
            alert.show();
        } else if (name.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("no name");
            alert.setContentText("sorry, but you don't choose any name for your room.");
            alert.show();
        } else if (ChatGroup.isRepeatedName(name.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("repeated name");
            alert.setContentText("sorry, but you choose a repeated name for your room.");
            alert.show();
        } else {
            multiUser.add(UserController.getInstance().getUserLoggedIn());
            ChatController.newRoomChat(multiUser, name.getText(), rooms, chat);
            stage.close();
        }
    }
}
