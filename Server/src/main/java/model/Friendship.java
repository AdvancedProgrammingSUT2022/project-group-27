package model;

import Enum.FriendshipEnum;

import java.util.ArrayList;

public class Friendship {
    private static final ArrayList<Friendship> list = new ArrayList<>();
    private String sender;
    private String receiver;
    private FriendshipEnum isAccepted = FriendshipEnum.IN_PROGRESS;

    public Friendship(User sender, User receiver) {
        for (Friendship friendship: list) {
            if (friendship.sender.equals(sender.getUsername()) && friendship.receiver.equals(receiver.getUsername()))
                return;
        }
        this.sender = sender.getUsername();
        this.receiver = receiver.getUsername();
        list.add(this);
    }

    public static Friendship findFriendship(String sender, String receiver) {
        for (Friendship friendship: list) {
            if (friendship.sender.equals(sender) && friendship.receiver.equals(receiver))
                return friendship;
        }

        return null;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public FriendshipEnum getIsAccepted() {
        return isAccepted;
    }

    public void setAccepted(FriendshipEnum accepted) {
        isAccepted = accepted;
    }

    public static ArrayList<Friendship> listOfSenderFriendship(User user) {
        ArrayList<Friendship> friendshipArrayList = new ArrayList<>();
        for (Friendship friendship: list) {
            if (friendship.sender.equals(user.getUsername())) friendshipArrayList.add(friendship);
        }

        return friendshipArrayList;
    }

    public static ArrayList<Friendship> listOfFriends(User user) {
        ArrayList<Friendship> friendshipArrayList = new ArrayList<>();
        for (Friendship friendship: list) {
            if ((friendship.sender.equals(user.getUsername()) && friendship.isAccepted == FriendshipEnum.ACCEPTED) ||
                    (friendship.receiver.equals(user.getUsername()) && friendship.isAccepted == FriendshipEnum.ACCEPTED))
                friendshipArrayList.add(friendship);
        }

        return friendshipArrayList;
    }

    public static ArrayList<Friendship> listOfFriendshipRequest(User user) {
        ArrayList<Friendship> friendshipArrayList = new ArrayList<>();
        for (Friendship friendship: list) {
            if (friendship.receiver.equals(user.getUsername())) friendshipArrayList.add(friendship);
        }

        return friendshipArrayList;
    }
}
