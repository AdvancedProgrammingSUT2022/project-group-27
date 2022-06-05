package model;

import java.util.ArrayList;

public class ChatGroup {
    private static ArrayList<ChatGroup> listOfGroups = new ArrayList<>();
    private ArrayList<User> listOfUsers;
    private final ArrayList<ChatText> chats = new ArrayList<>();
    private String name = "";

    public ChatGroup(ArrayList<User> listOfUsers) {
        boolean flag = true;
        for (ChatGroup chatGroup: listOfGroups) {
            flag = true;
            for (User user: listOfUsers) {
                if (!chatGroup.listOfUsers.contains(user)) {
                    flag = false;
                    break;
                }
            }
            if (flag && listOfUsers.size() == chatGroup.listOfUsers.size()) return;
        }

        this.listOfUsers = listOfUsers;
        listOfGroups.add(this);
    }

    public ChatGroup(ArrayList<User> listOfUsers, String name) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.name.equals(name)) return;
        }

        this.listOfUsers = listOfUsers;
        this.name = name;
        listOfGroups.add(this);
    }

    public User getOtherUser(User own) {
        for (User user: listOfUsers) {
            if (!user.getUsername().equals(own.getUsername())) return user;
        }

        return null;
    }

    public void add(ChatText chatText) {
        chats.add(chatText);
    }

    public ArrayList<ChatText> getChats() {
        return chats;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public static ArrayList<ChatGroup> listOfGroupsWithUser(User user) {
        ArrayList<ChatGroup> list = new ArrayList<>();
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.listOfUsers.contains(user)) list.add(chatGroup);
        }

        return list;
    }

    public static ChatGroup findChat(User one, User two) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.listOfUsers.contains(one) && chatGroup.listOfUsers.contains(two) && chatGroup.listOfUsers.size() == 2)
                return chatGroup;
        }

        return null;
    }

    public static ChatGroup findChat(String name) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.name.equals(name)) return chatGroup;
        }

        return null;
    }
}
