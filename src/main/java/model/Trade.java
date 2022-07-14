package model;

import javafx.scene.control.Alert;
import Enum.*;

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
        if (receive.equals("gold") && Player.findPlayerByUser(receiver).getGold() < numberReceive) invalidAlert();
        else if (receive.equals("gold") && send.equals("gold")) {
            Player.findPlayerByUser(sender).setGold(Player.findPlayerByUser(sender).getGold() - numberSend + numberReceive);
            Player.findPlayerByUser(receiver).setGold(Player.findPlayerByUser(receiver).getGold() - numberReceive + numberSend);
        } else if (receive.equals("gold")) {
            Player receiverPlayer = Player.findPlayerByUser(sender);
            for (LuxuryResource luxuryResource: receiverPlayer.getAllLuxuryResources()) {
                if (luxuryResource.name().equals(send.toUpperCase())) Player.findPlayerByUser(receiver).getAllLuxuryResources().add(luxuryResource);
            }

            for (StrategicResource strategicResource: receiverPlayer.getAllStrategicResources()) {
                if (strategicResource.name().equals(send.toUpperCase())) Player.findPlayerByUser(receiver).getAllStrategicResources().add(strategicResource);
            }

            if (send.equals("piece")) {
                Player.findPlayerByUser(sender).setInPeace(Player.findPlayerByUser(receiver));
                Player.findPlayerByUser(receiver).setInPeace(Player.findPlayerByUser(sender));
            }

            Player.findPlayerByUser(receiver).setGold(Player.findPlayerByUser(receiver).getGold() - numberReceive);
        } else {
            boolean canDo = false;
            Player receiverPlayer = Player.findPlayerByUser(receiver);
            for (LuxuryResource luxuryResource: receiverPlayer.getAllLuxuryResources()) {
                if (luxuryResource.name().equals(receive.toUpperCase())) {
                    canDo = true;
                    Player.findPlayerByUser(sender).getAllLuxuryResources().add(luxuryResource);
                }
            }

            for (StrategicResource strategicResource: receiverPlayer.getAllStrategicResources()) {
                if (strategicResource.name().equals(receive.toUpperCase())) {
                    canDo = true;
                    Player.findPlayerByUser(sender).getAllStrategicResources().add(strategicResource);
                }
            }

            if (receive.equals("piece")) {
                canDo = true;
                Player.findPlayerByUser(sender).setInPeace(Player.findPlayerByUser(receiver));
                Player.findPlayerByUser(receiver).setInPeace(Player.findPlayerByUser(sender));
            }

            if (send.equals("gold")) {
                if (canDo) Player.findPlayerByUser(sender).setGold(Player.findPlayerByUser(sender).getGold() - numberSend);
                else invalidAlert();
            } else if (send.equals("piece")) {
                Player.findPlayerByUser(sender).setInPeace(Player.findPlayerByUser(receiver));
                Player.findPlayerByUser(receiver).setInPeace(Player.findPlayerByUser(sender));
            } else {
                for (LuxuryResource luxuryResource: receiverPlayer.getAllLuxuryResources()) {
                    if (luxuryResource.name().equals(send.toUpperCase())) Player.findPlayerByUser(receiver).getAllLuxuryResources().add(luxuryResource);
                }

                for (StrategicResource strategicResource: receiverPlayer.getAllStrategicResources()) {
                    if (strategicResource.name().equals(send.toUpperCase())) Player.findPlayerByUser(receiver).getAllStrategicResources().add(strategicResource);
                }
            }
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
        if (send.equals("piece") || receive.equals("piece")) {
            Player.findPlayerByUser(sender).setInWar(Player.findPlayerByUser(receiver));
            Player.findPlayerByUser(receiver).setInWar(Player.findPlayerByUser(sender));
        }
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
