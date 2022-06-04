package model;

import java.util.ArrayList;

public class ChatGroup {
    private static ArrayList<ChatGroup> listOfGroups = new ArrayList<>();
    private ArrayList<User> listOfUsers;
    private final ArrayList<ChatText> chats = new ArrayList<>();

    public ChatGroup(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
        listOfGroups.add(this);
    }

    public void add(ChatText chatText) {
        chats.add(chatText);
    }

    public ArrayList<ChatText> getChats() {
        return chats;
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
}
