package model;

import java.util.ArrayList;

public class ChatText {
    private static final ArrayList<ChatText> listOfChats = new ArrayList<>();
    private User sender;
    private User receiver;
    private String time;
    private String text;
    private boolean isSend;
    private boolean isSeen;

    public ChatText(User sender, User receiver, String time, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.text = text;
        this.isSend = true;
        this.isSeen = false;
        listOfChats.add(this);
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
