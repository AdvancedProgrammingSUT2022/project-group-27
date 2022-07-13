package model;

import java.util.ArrayList;

public class Trade {
    private ArrayList<Trade> list = new ArrayList<>();

    private User sender;
    private User receiver;
    private Object receive;
    private int numberReceive;
    private Object send;
    private int numberSend;

    private boolean isAccepted = false;

    public Trade(User sender, User receiver, Object receive, int numberReceive, Object send, int numberSend) {
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
        //TODO ... do things should do by accept should happen
    }

    public ArrayList<Trade> userTradesAll(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.sender == user || trade.receive == user) userList.add(trade);
        }

        return userList;
    }

    public ArrayList<Trade> userSendTrades(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.sender == user) userList.add(trade);
        }

        return userList;
    }

    public ArrayList<Trade> userReceiverTrades(User user) {
        ArrayList<Trade> userList = new ArrayList<>();
        for (Trade trade: list) {
            if (trade.receive == user) userList.add(trade);
        }

        return userList;
    }
}
