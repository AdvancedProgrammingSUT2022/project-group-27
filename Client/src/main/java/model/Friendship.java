package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import Enum.FriendshipEnum;

import java.util.ArrayList;

public class Friendship {
    private String sender;
    private String receiver;
    private FriendshipEnum isAccepted = FriendshipEnum.IN_PROGRESS;

    public String getSender() {
        return sender;
    }

    public String getOther(String user) {
        if (sender.equals(user)) return receiver;
        else return sender;
    }

    public Friendship(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
        Request request = new Request();
        request.setHeader("addFriendship");
        request.addData("friendship", new Gson().toJson(this));
        Response response = NetworkController.send(request);
    }

    public void setAccepted(FriendshipEnum accepted) {
        isAccepted = accepted;
        Request request = new Request();
        request.setHeader("setAccepted");
        request.addData("friendship", new Gson().toJson(this));
        request.addData("accepted", accepted);
        Response response = NetworkController.send(request);
    }

    public static ArrayList<Friendship> listOfFriendshipRequest(String user) {
        Request request = new Request();
        request.setHeader("listOfFriendshipRequest");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Friendship>>(){}.getType());
    }

    public static ArrayList<Friendship> listOfSenderFriendship(String user) {
        Request request = new Request();
        request.setHeader("listOfSenderFriendship");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Friendship>>(){}.getType());
    }

    public static ArrayList<Friendship> listOfFriends(String user) {
        Request request = new Request();
        request.setHeader("listOfFriends");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Friendship>>(){}.getType());
    }

    @Override
    public String toString() {
        return "receiver: " + receiver + " sender: " + sender + " status: " + isAccepted;
    }
}
