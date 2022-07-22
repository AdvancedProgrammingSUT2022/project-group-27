package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import Enum.FriendshipEnum;

public class FriendshipController {
    public static Response addFriendship(Request request) {
        Response response = new Response();

        Friendship friendship = new Gson().fromJson((String) request.getData().get("friendship"), new TypeToken<Friendship>(){}.getType());
        Friendship friendship1 = new Friendship(User.findUser(friendship.getSender()), User.findUser(friendship.getReceiver()));
        friendship1.setAccepted(friendship.getIsAccepted());

        response.setStatus(200);
        return response;
    }

    public static Response setAccepted(Request request) {
        Response response = new Response();

        Friendship friendship = new Gson().fromJson((String) request.getData().get("friendship"), new TypeToken<Friendship>(){}.getType());
        FriendshipEnum friendshipEnum = new Gson().fromJson((String) request.getData().get("accepted"), new TypeToken<FriendshipEnum>(){}.getType());
        Friendship friendship1 = Friendship.findFriendship(friendship.getSender(), friendship.getReceiver());
        if (friendship1 != null) friendship1.setAccepted(friendshipEnum);

        response.setStatus(200);
        return response;
    }

    public static Response listOfFriendshipRequest(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        response.addData("list", new Gson().toJson(Friendship.listOfFriendshipRequest(user)));

        response.setStatus(200);
        return response;
    }

    public static Response listOfSenderFriendship(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        response.addData("list", new Gson().toJson(Friendship.listOfSenderFriendship(user)));

        response.setStatus(200);
        return response;
    }

    public static Response listOfFriends(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        response.addData("list", new Gson().toJson(Friendship.listOfFriends(user)));

        response.setStatus(200);
        return response;
    }
}
