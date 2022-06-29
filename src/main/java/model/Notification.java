package model;

import javafx.scene.control.Alert;

public class Notification {
    private final String notification;
    private final int whichTurn;

    public Notification(String notification, int whichTurn, Player player) {
        this.notification = notification;
        this.whichTurn = whichTurn;
        System.out.println(notification);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Notification");
        alert.setContentText(notification);
        alert.show();
        player.getNotificationHistory().add(this);
    }

    @Override
    public String toString() {
        return "title: " + notification + " in turn: " + whichTurn;
    }
}
