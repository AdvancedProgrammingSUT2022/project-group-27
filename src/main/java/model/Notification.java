package model;

public class Notification {
    private final String notification;
    private final int whichTurn;

    public Notification(String notification, int whichTurn) {
        this.notification = notification;
        this.whichTurn = whichTurn;
    }

    @Override
    public String toString() {
        return "title: " + notification + " in turn: " + whichTurn;
    }
}
