package model;

import java.util.ArrayList;

public class ChatGroup {
    private static ArrayList<ChatGroup> listOfGroups = new ArrayList<>();
    private ArrayList<String> listOfUsers;
    private final ArrayList<ChatText> chats = new ArrayList<>();
    private String name = "";

    public ChatGroup(ArrayList<String> listOfUsers) {
        boolean flag = true;
        for (ChatGroup chatGroup: listOfGroups) {
            flag = true;
            for (String user: listOfUsers) {
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

    public ChatGroup(ArrayList<String> listOfUsers, String name) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.name.equals(name)) return;
        }

        this.listOfUsers = listOfUsers;
        this.name = name;
        listOfGroups.add(this);
    }

    public static boolean isRepeatedName(String name) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.name.equals(name)) return true;
        }

        return false;
    }

    public User getOtherUser(User own) {
        for (String user: listOfUsers) {
            if (!user.equals(own.getUsername())) return User.findUser(user);
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

    public ArrayList<String> getListOfUsers() {
        return listOfUsers;
    }

    public static ArrayList<ChatGroup> getListOfGroups() {
        return listOfGroups;
    }

    public static void setListOfGroups(ArrayList<ChatGroup> listOfGroups) {
        ChatGroup.listOfGroups = listOfGroups;
    }

    public static ArrayList<ChatGroup> listOfGroupsWithUser(User user) {
        ArrayList<ChatGroup> list = new ArrayList<>();
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.listOfUsers.contains(user.getUsername())) list.add(chatGroup);
        }

        return list;
    }

    public static ChatGroup findChat(User one, User two) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.name.equals("") && chatGroup.listOfUsers.contains(one.getUsername()) && chatGroup.listOfUsers.contains(two.getUsername()) && chatGroup.listOfUsers.size() == 2)
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

    public static ChatGroup findChatGroupByChat(ChatText chatText) {
        for (ChatGroup chatGroup: listOfGroups) {
            if (chatGroup.getChats().contains(chatText)) return chatGroup;
        }

        return null;
    }

    public static void add(ChatGroup chatGroup) {
        ChatGroup newChatGroup;
        if (chatGroup.name.equals("")) newChatGroup = new ChatGroup(chatGroup.getListOfUsers());
        else newChatGroup = new ChatGroup(chatGroup.getListOfUsers(), chatGroup.name);
        for (ChatText chatText: chatGroup.chats) {
            newChatGroup.add(chatText);
        }
    }
}
