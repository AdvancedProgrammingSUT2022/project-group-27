package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;

import java.util.ArrayList;

public class Trade {
    private static ArrayList<Trade> list = new ArrayList<>();

    private String sender;
    private String receiver;
    private String receive;
    private int numberReceive;
    private String send;
    private int numberSend;

    private boolean isAccepted = false;
    private boolean isDeny = false;

    public Trade(String sender, String receiver, String receive, int numberReceive, String send, int numberSend) {
        this.sender = sender;
        this.receiver = receiver;
        this.receive = receive;
        this.numberReceive = numberReceive;
        this.send = send;
        this.numberSend = numberSend;
        Request request = new Request();
        request.setHeader("addTrade");
        request.addData("sender", sender);
        request.addData("receiver", receiver);
        request.addData("receive", receive);
        request.addData("numberReceive", numberReceive);
        request.addData("send", send);
        request.addData("numberSend", numberSend);
        Response response = NetworkController.send(request);
    }

    public static ArrayList<Trade> userTradesAll(String user) {
        Request request = new Request();
        request.setHeader("userTradesAll");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Trade>>(){}.getType());
    }

    @Override
    public String toString() {
        return "sending object: " + numberSend + "- " + send.toString() + "\nreceiving object: " + numberReceive + "- " +
                receive.toString() + "\nreceiver: " + receiver + " sender: " + sender;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isDeny() {
        return isDeny;
    }
}
