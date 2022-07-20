package model;

import com.google.gson.Gson;
import controller.NetworkController;
import controller.UserController;

public class ChatText {
    private String sender;
    private String time;
    private String text;
    private boolean isSeen;
    private boolean isDeleted;

    public ChatText(String sender, ChatGroup chatGroup, String time, String text) {
        this.sender = sender;
        this.time = time;
        this.text = text;
        this.isSeen = false;
        this.isDeleted = false;
        chatGroup.add(this);
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
        Request request = new Request();
        request.setHeader("setSeen");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("chatText", new Gson().toJson(this));
        request.addData("seen", seen);
        Response response = NetworkController.send(request);
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
        Request request = new Request();
        request.setHeader("setDeleted");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("chatText", new Gson().toJson(this));
        request.addData("deleted", deleted);
        Response response = NetworkController.send(request);
    }

    public void setText(String text) {
        this.text = text;
        Request request = new Request();
        request.setHeader("setText");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("chatText", new Gson().toJson(this));
        request.addData("text", text);
        Response response = NetworkController.send(request);
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void delete() {
        ChatGroup chatGroup = ChatGroup.findChatGroupByChat(this);
        if (chatGroup != null) {
            chatGroup.getChats().remove(this);
            Request request = new Request();
            request.setHeader("deleteChatText");
            request.addData("token", UserController.getInstance().getUserLoggedIn());
            request.addData("chatGroup", new Gson().toJson(chatGroup));
            request.addData("chatText", new Gson().toJson(this));
            Response response = NetworkController.send(request);
        }
    }
}
