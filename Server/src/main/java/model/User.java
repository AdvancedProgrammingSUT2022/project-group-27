package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;

public class User {
    public transient boolean isConnect = false;
    private String username;
    private String password;
    private String nickname;
    private transient Socket updateSocket;
    private transient DataOutputStream updateOutputStream;
    private transient Socket startGameSocket;
    private transient DataOutputStream startGameOutputStream;
    private transient Socket startGameSocketOther;
    private transient DataOutputStream startGameOutputStreamOther;
    private ArrayList<String> listOfInvitation=new ArrayList<>();
    private String profileImage = null;
    private String currentImage = null;
    private int score;
    private String timeOfScoreGame;
    private String lastLoginTime;
    private ArrayList <String> adminList=new ArrayList<>();
    private ArrayList<String> listOfAcceptedUsers=new ArrayList<>();
    private byte[] image;
    private int isInGame=0;

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

    public void clearAcceptedUsers() {
        listOfAcceptedUsers.clear();
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUpdateSocket(Socket updateSocket) throws IOException {
        this.updateSocket = updateSocket;
        this.updateOutputStream = new DataOutputStream(updateSocket.getOutputStream());
    }

    public void setStartGameSocket(Socket startGameSocket) throws IOException {
        this.startGameSocket = startGameSocket;
        this.startGameOutputStream = new DataOutputStream(startGameSocket.getOutputStream());
    }

    public void setStartGameSocketOther(Socket startGameSocketOther) throws IOException {
        this.startGameSocketOther = startGameSocketOther;
        this.startGameOutputStreamOther = new DataOutputStream(startGameSocketOther.getOutputStream());
    }

    public DataOutputStream getStartGameOutputStream() {
        return startGameOutputStream;
    }

    public DataOutputStream getUpdateOutputStream() {
        return updateOutputStream;
    }

    public DataOutputStream getStartGameOutputStreamOther() {
        return startGameOutputStreamOther;
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

    public String getToken() {
        return username; //TODO maybe should change it
    }

    public static User findUserByToken(String token) {
        return findUser(token);
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
    public static boolean doWeHaveAllOfTheseUsers(ArrayList<String> list){
        for (String username : list){
            boolean isOk=false;
            for (User user : listOfUsers){
                if (user.getUsername().equals(username)) isOk=true;
            }
            if (!isOk) return false;
        }
        return true;
    }

    public static boolean isThisListOfUsersUnique(ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            for (int j=i+1;j<list.size();j++){
                if (list.get(i).equals(list.get(j))) return false;
            }
        }
        return true;
    }

    public Socket getUpdateSocket() {
        return updateSocket;
    }


    public void addInvitation(String s){
        listOfInvitation.add(s);
    }

    public String getListOfInvitation() {
        String ans="";
        if (listOfInvitation.size()>0){
            ans+=listOfInvitation.get(0);
        }
        for (int i=1;i<listOfInvitation.size();i++){
            ans+=',' + listOfInvitation.get(i);
        }
        System.out.println(ans+ " eirfj ");
        return ans;
    }
    public void removeInvitation(String s){
        listOfInvitation.remove(s);
        System.out.println(listOfInvitation);
    }
    public void addToAdminList(String s){
        if (adminList.contains(s)) return ;
        adminList.add(s);
    }
    public void addToAccepted(String s){
        if (listOfAcceptedUsers.contains(s)) return ;
        listOfAcceptedUsers.add(s);
    }
    public boolean canWeStart(){
        System.out.println(adminList.size() + " " + listOfAcceptedUsers.size());
        if (adminList.size()==listOfAcceptedUsers.size()) return true;
        return false;
    }
}
