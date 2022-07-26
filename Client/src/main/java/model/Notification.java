package model;

public class Notification {
    private String notification;
    private int whichTurn;

    @Override
    public String toString() {
        return "title: " + notification + " in turn: " + whichTurn;
    }
}
