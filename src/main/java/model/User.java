package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;

    private final static ArrayList<User> listOfUsers = new ArrayList<>();

    public static User findUser(String username, String password) {
        for (User user: listOfUsers) {
            if (user.username.equals(username) && user.password.equals(password)) return user;
        }

        return null;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        listOfUsers.add(this);
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public static boolean isNicknameExist(String name) {
        for (User user: listOfUsers) {
            if (user.nickname.equals(name)) return true;
        }

        return false;
    }

    public static boolean isUsernameExist(String name) {
        for (User user: listOfUsers) {
            if (user.username.equals(name)) return true;
        }

        return false;
    }
}
