package model;

import java.util.ArrayList;
import java.util.Comparator;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String profileImage = null;
    private String currentImage = null;
    private int score;
    private String timeOfScoreGame;
    private String lastLoginTime;

    private static ArrayList<User> listOfUsers = new ArrayList<>();

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.score = 0;
        this.timeOfScoreGame = "0";
        listOfUsers.add(this);
    }

    public void setCurrentImage(String currentImage) {
        this.currentImage = currentImage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentImage() {
        return currentImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeOfScoreGame() {
        return timeOfScoreGame;
    }

    public void setTimeOfScoreGame(String timeOfScoreGame) {
        this.timeOfScoreGame = timeOfScoreGame;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    public static void setNullAllOfTheCurrentImages() {
        for (User user: listOfUsers) {
            user.setCurrentImage(null);
        }
    }

    public static void sort() {
        Comparator<User> sortByScore = Comparator.comparing(User::getScore).reversed();
        Comparator<User> sortByTime = Comparator.comparing(User::getTimeOfScoreGame);
        Comparator<User> sortByName = Comparator.comparing(User::getUsername);

        Comparator<User> full = sortByScore.thenComparing(sortByTime).thenComparing(sortByName);
        User.listOfUsers.sort(full);
    }
}
