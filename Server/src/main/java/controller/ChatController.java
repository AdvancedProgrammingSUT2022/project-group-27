package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.util.ArrayList;

public class ChatController {
    public static Response getListOfAllChats(Request request) {
        Response response = new Response();

        response.addData("list", new Gson().toJson(ChatGroup.getListOfGroups()));
        response.setStatus(200);
        return response;
    }

    public static Response addChatGroup(Request request) {
        Response response = new Response();

        //ChatGroup chatGroup = (ChatGroup) request.getData().get("chatGroup");
        ChatGroup chatGroup = new Gson().fromJson((String) request.getData().get("chatGroup"), new TypeToken<ChatGroup>(){}.getType());
        ChatGroup.add(chatGroup);
        response.setStatus(200);
        return response;
    }

    public static Response delete(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        //ChatGroup chatGroup = (ChatGroup) request.getData().get("chatGroup");
        ChatGroup chatGroup = new Gson().fromJson((String) request.getData().get("chatGroup"), new TypeToken<ChatGroup>(){}.getType());
        //ChatText chatText = (ChatText) request.getData().get("chatText");
        ChatText chatText = new Gson().fromJson((String) request.getData().get("chatText"), new TypeToken<ChatText>(){}.getType());

        chatText.delete();
        response.setStatus(200);
        return response;
    }

    public static Response setText(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        //ChatText chatText = (ChatText) request.getData().get("chatText");
        ChatText chatText = new Gson().fromJson((String) request.getData().get("chatText"), new TypeToken<ChatText>(){}.getType());
        String text = (String) request.getData().get("text");

        chatText.setText(text);
        response.setStatus(200);
        return response;
    }

    public static Response setDeleted(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        //ChatText chatText = (ChatText) request.getData().get("chatText");
        ChatText chatText = new Gson().fromJson((String) request.getData().get("chatText"), new TypeToken<ChatText>(){}.getType());
        boolean deleted = (boolean) request.getData().get("deleted");

        chatText.setDeleted(deleted);
        response.setStatus(200);
        return response;
    }

    public static Response setSeen(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        //ChatText chatText = (ChatText) request.getData().get("chatText");
        ChatText chatText = new Gson().fromJson((String) request.getData().get("chatText"), new TypeToken<ChatText>(){}.getType());
        boolean seen = (boolean) request.getData().get("seen");

        chatText.setSeen(seen);
        response.setStatus(200);
        return response;
    }

    public static Response addChatText(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        ChatGroup chatGroup = new Gson().fromJson((String) request.getData().get("chatGroup"), new TypeToken<ChatGroup>(){}.getType());
        if (chatGroup.getName().equals("")) chatGroup = ChatGroup.findChat(User.findUser(chatGroup.getListOfUsers().get(0)), User.findUser(chatGroup.getListOfUsers().get(1)));
        else chatGroup = ChatGroup.findChat(chatGroup.getName());
        ChatText chatText = new Gson().fromJson((String) request.getData().get("chatText"), new TypeToken<ChatText>(){}.getType());

        chatGroup.add(chatText);
        response.setStatus(200);
        return response;
    }

    public static Response getListOfAllUsers(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        ArrayList<String> allUsers = new ArrayList<>();
        for (User user1: User.getListOfUsers()) allUsers.add(user1.getUsername());
        response.addData("list", new Gson().toJson(allUsers));
        response.setStatus(200);
        return response;
    }

    public static Response getListOfAllChatsUser(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        response.addData("list", new Gson().toJson(ChatGroup.listOfGroupsWithUser(user)));
        response.setStatus(200);
        return response;
    }

    public static Response getListOfAllChatTexts(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        ChatGroup chatGroup = new Gson().fromJson((String) request.getData().get("chatGroup"), new TypeToken<ChatGroup>(){}.getType());
        if (chatGroup.getName().equals("")) chatGroup = ChatGroup.findChat(User.findUser(chatGroup.getListOfUsers().get(0)), User.findUser(chatGroup.getListOfUsers().get(1)));
        else chatGroup = ChatGroup.findChat(chatGroup.getName());
        response.addData("list", new Gson().toJson(chatGroup.getChats()));
        response.setStatus(200);
        return response;
    }
}
