package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private int score;

    private static ArrayList<User> listOfUsers = new ArrayList<>();

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        listOfUsers.add(this);
    }

    public String getUsername() {
        return username;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public static void setListOfUsers(ArrayList<User> listOfUsers) {
        User.listOfUsers = listOfUsers;
    }

    public static ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public static User findUser(String username, String password) {
        for (User user : listOfUsers) {
            if (user.username.equals(username) && user.password.equals(password)) return user;
        }

        return null;
    }

    public static User findUser(String username) {
        for (User user : listOfUsers) {
            if (user.username.equals(username)) return user;
        }

        return null;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public static boolean isNicknameExist(String name) {
        for (User user : listOfUsers) {
            if (user.nickname.equals(name)) return true;
        }

        return false;
    }

    public static boolean isUsernameExist(String name) {
        for (User user : listOfUsers) {
            if (user.username.equals(name)) return true;
        }

        return false;
    }

    public boolean changePassword(String newPassword) {
        if (this.password.equals(newPassword)) return false;
        this.password = newPassword;
        return true;
    }

    public boolean changeNickname(String newNickname) {
        if (User.isNicknameExist(newNickname)) return false;
        this.nickname = newNickname;
        return true;
    }
}
