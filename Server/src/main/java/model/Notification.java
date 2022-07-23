package model;

public class Notification {
    private final String notification;
    private final int whichTurn;

    public Notification(String notification, int whichTurn, Player player) {
        this.notification = notification;
        this.whichTurn = whichTurn;

        player.getNotificationHistory().add(this);
    }

    @Override
    public String toString() {
        return "title: " + notification + " in turn: " + whichTurn;
    }
}
