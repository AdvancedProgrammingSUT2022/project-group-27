package model;

import javafx.scene.control.Alert;

import java.util.ArrayList;

public class Trade {
    private static ArrayList<Trade> list = new ArrayList<>();

    private User sender;
    private User receiver;
    private String receive;
    private int numberReceive;
    private String send;
    private int numberSend;

    private boolean isAccepted = false;
    private boolean isDeny = false;

    public Trade(User sender, User receiver, String receive, int numberReceive, String send, int numberSend) {
        this.sender = sender;
        this.receiver = receiver;
        this.receive = receive;
        this.numberReceive = numberReceive;
        this.send = send;
        this.numberSend = numberSend;
        list.add(this);
    }

    public void accept() {
        isAccepted = true;
        //TODO ... do things should do by accept should happen and show errors, too.
        if (receive.equals("gold") && Player.findPlayerByUser(receiver).getGold() < numberReceive) invalidAlert();
        else if (receive.equals("gold") && send.equals("gold")) {
            Player.findPlayerByUser(sender).setGold(Player.findPlayerByUser(sender).getGold() - numberSend + numberReceive);
            Player.findPlayerByUser(receiver).setGold(Player.findPlayerByUser(receiver).getGold() - numberReceive + numberSend);
        }
    }

    private void invalidAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("you don't have enough recourse for trading.");
        alert.show();
    }

    public void deny() {
        isDeny = true;
    }

    public static ArrayList<Trade> userTradesAll(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.sender == user || trade.receiver == user) userList.add(trade);
        }

        return userList;
    }

    public static ArrayList<Trade> userSendTrades(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.sender == user) userList.add(trade);
        }

        return userList;
    }

    public static ArrayList<Trade> userReceiverTrades(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.receiver == user) userList.add(trade);
        }

        return userList;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDeny() {
        return isDeny;
    }

    @Override
    public String toString() {
        return "sending object: " + numberSend + "- " + send.toString() + "\nreceiving object: " + numberReceive + "- " +
                receive.toString() + "\nreceiver: " + receiver.getUsername() + " sender: " + sender.getUsername();
    }
}
