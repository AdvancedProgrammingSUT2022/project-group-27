package model;

import com.google.gson.Gson;
import controller.ChatController;
import controller.NetworkController;
import controller.UserController;
import viewControllers.NewChatGroups;

import java.util.ArrayList;

public class ChatGroup {
    private ArrayList<String> listOfUsers;
    private final ArrayList<ChatText> chats = new ArrayList<>();
    private String name = "";

    public ChatGroup(ArrayList<String> listOfUsers) {
        boolean flag = true;
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
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
        ChatController.addChatGroup(this);
    }

    public ChatGroup(ArrayList<String> listOfUsers, String name) {
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
            if (chatGroup.name.equals(name)) return;
        }

        this.listOfUsers = listOfUsers;
        this.name = name;
        ChatController.addChatGroup(this);
    }

    public static boolean isRepeatedName(String name) {
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
            if (chatGroup.name.equals(name)) return true;
        }

        return false;
    }

    public String getOtherUser(String own) {
        for (String user: listOfUsers) {
            if (!user.equals(own)) return user;
        }

        return null;
    }

    public void add(ChatText chatText) {
        chats.add(chatText);
        Request request = new Request();
        request.setHeader("addChatText");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("chatGroup", new Gson().toJson(this));
        request.addData("chatText", new Gson().toJson(chatText));
        Response response = NetworkController.send(request);
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

    public static ArrayList<ChatGroup> listOfGroupsWithUser(String user) {
        return UserController.getInstance().getListOfAllChatsUser();
    }

    public static ChatGroup findChat(String one, String two) {
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
            if (chatGroup.name.equals("") && chatGroup.listOfUsers.contains(one) && chatGroup.listOfUsers.contains(two) && chatGroup.listOfUsers.size() == 2)
                return chatGroup;
        }

        return null;
    }

    public static ChatGroup findChat(String name) {
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
            if (chatGroup.name.equals(name)) return chatGroup;
        }

        return null;
    }

    public static ChatGroup findChatGroupByChat(ChatText chatText) {
        ArrayList<ChatGroup> listOfAllGroups = ChatController.getListOfAllChats();
        for (ChatGroup chatGroup: listOfAllGroups) {
            if (chatGroup.getChats().contains(chatText)) return chatGroup;
        }

        return null;
    }
}
