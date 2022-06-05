package model;

import java.util.ArrayList;

public class ChatText {
    private User sender;
    private ChatGroup chatGroup;
    private String time;
    private String text;
    private boolean isSeen;

    public ChatText(User sender, ChatGroup chatGroup, String time, String text) {
        this.sender = sender;
        this.chatGroup = chatGroup;
        this.time = time;
        this.text = text;
        this.isSeen = false;
        chatGroup.add(this);
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public User getSender() {
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
}
