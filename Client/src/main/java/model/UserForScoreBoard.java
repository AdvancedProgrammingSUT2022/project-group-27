package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;
import java.util.Arrays;

public class UserForScoreBoard {
    private static ArrayList<UserForScoreBoard> list = new ArrayList<>();
    private byte[] image;
    private String username;
    private Integer score;
    private String timeOfScoreGame;
    private String lastLoginTime;

    private UserForScoreBoard(byte[] image, String username, Integer score, String timeOfScoreGame, String lastLoginTime) {
        this.image = image;
        this.username = username;
        this.score = score;
        this.timeOfScoreGame = timeOfScoreGame;
        this.lastLoginTime = lastLoginTime;
        list.add(this);
    }

    public byte[] getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public String getTimeOfScoreGame() {
        return timeOfScoreGame;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setTimeOfScoreGame(String timeOfScoreGame) {
        this.timeOfScoreGame = timeOfScoreGame;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public static ArrayList<UserForScoreBoard> getListOfUsers() {
        return list;
    }

    public static void sort() {
        Request request = new Request();
        request.setHeader("scoreBoardList");
        Response response = NetworkController.send(request);

        list = new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<UserForScoreBoard>>(){}.getType());
        for (UserForScoreBoard user: list) {
            user.image = UserController.getInstance().getImage(user.username);
        }
    }
}
