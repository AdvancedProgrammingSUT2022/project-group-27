package viewControllers.Info;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Notification;
import model.Player;
import viewControllers.Main;
import viewControllers.Menus;

import java.util.ArrayList;

public class NotificationPanel extends Menus {
    private static Stage stage;
    private static Player player;

    @FXML
    private Button back;

    @FXML
    private VBox list;

    @FXML
    public void initialize() {
        Label firstText = new Label("List of notifications you get: ");
        firstText.setStyle("-fx-text-fill:  #b71135; -fx-font-size: 20; -fx-background-color: rgba(255, 255, 255, 0.61); -fx-font-weight: bold");
        list.getChildren().add(firstText);

        setList();
        back.setCursor(Cursor.HAND);
    }

    public static void setPlayer(Player player) {
        NotificationPanel.player = player;
    }

    @Override
    public void start(Stage stage) throws Exception {
        NotificationPanel.stage = stage;
        Parent root = Main.loadFXML("notification-panel");
        root.getStylesheets().add(Main.class.getResource("/css/menu.css").toExternalForm());
        root.getStyleClass().add("background");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Notification History");
        stage.show();
    }

    public void setList() {
        ArrayList<Notification> listNotification = player.getNotificationHistory();
        for (Notification notification: listNotification) {
            list.getChildren().add(new Label(notification.toString()));
        }

        if (listNotification.size() == 0) list.getChildren().add(new Label("nothing"));
    }

    public void back(MouseEvent mouseEvent) {
        stage.close();
    }
}
