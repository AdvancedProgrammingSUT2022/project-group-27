package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.ChatGroup;
import model.ChatText;
import model.Request;
import model.Response;
import Enum.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserController {
    private static UserController instance = null;
    private UserController() {}

    public static UserController getInstance() {
        if (instance == null) instance = new UserController();

        return instance;
    }

    private String userLoggedIn = null;

    public String getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(String userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    public void atEndWorks() {
        if (userLoggedIn != null) {
            setLastLoginTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            setNullTheCurrentImages();
            userLoggedIn = null;
        }
    }

    private void setNullTheCurrentImages() {
        Request request = new Request();
        request.setHeader("nullCurrentImage");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
    }

    public String getUsername() {
        Request request = new Request();
        request.setHeader("getUsername");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("username");
    }

    public Double getScore() {
        Request request = new Request();
        request.setHeader("getScore");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (Double) response.getData().get("score");
    }

    public void setLastLoginTime(String time) {
        Request request = new Request();
        request.setHeader("lastTime");
        request.addData("token", userLoggedIn);
        request.addData("time", time);
        Response response = NetworkController.send(request);
    }

    public String loginUser(String username, String password) {
        Request request = new Request();
        request.setHeader("loginUser");
        request.addData("username", username);
        request.addData("password", password);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        if (response.getStatus() == 200) userLoggedIn = (String) response.getData().get("token");
        return (String) response.getData().get("message");
    }

    public String createUser(String username, String password, String nickname) {
        Request request = new Request();
        request.setHeader("createUser");
        request.addData("username", username);
        request.addData("password", password);
        request.addData("nickname", nickname);
        Response response = NetworkController.send(request);
        if (response == null) return Message.LOST_RESPONSE.toString();

        if (response.getStatus() == 200) userLoggedIn = (String) response.getData().get("token");
        return (String) response.getData().get("message");
    }

    public String getProfileImage(String user) {
        Request request = new Request();
        request.setHeader("profileImage");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("image");
    }

    public String getCurrentImage(String user) {
        Request request = new Request();
        request.setHeader("currentImage");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (String) response.getData().get("image");
    }

    public void setProfileImage(String profileImage) {
        Request request = new Request();
        request.setHeader("setProfileImage");
        request.addData("token", userLoggedIn);
        request.addData("image", profileImage);
        Response response = NetworkController.send(request);
    }

    public void setImage(byte[] image, String user) {
        Request request = new Request();
        request.setHeader("setImage");
        request.addData("token", user);
        //request.addData("image", new Gson().toJson(image));
        Response response = NetworkController.send(request);
    }

    public byte[] getImage(String user) {
        String profileModel = UserController.getInstance().getProfileImage(user);
        File file;

        if (profileModel == null) profileModel = ProfileController.getInstance().randomImage().toString();

        String currentImage = UserController.getInstance().getCurrentImage(user);
        if (currentImage == null) file = new File("./src/main/resources/profile/" + profileModel);
        else file = new File(currentImage);

        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return bFile;
    }

    public ArrayList<String> getListOfAllUsers() {
        Request request = new Request();
        request.setHeader("getListOfUsers");
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        String answer = new Gson().toJson(response.getData().get("list"));
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<String>>(){}.getType());
    }

    public ArrayList<ChatGroup> getListOfAllChatsUser() {
        Request request = new Request();
        request.setHeader("getListOfChatsUser");
        request.addData("token", userLoggedIn);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<ChatGroup>>(){}.getType());
    }

    public ArrayList<ChatText> getListOfAllChatTexts(ChatGroup chatGroup) {
        Request request = new Request();
        request.setHeader("getListOfChatTexts");
        request.addData("token", userLoggedIn);
        request.addData("chat", new Gson().toJson(chatGroup));
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        String answer = new Gson().toJson(response.getData().get("list"));
        return new Gson().fromJson(answer, new TypeToken<ArrayList<ChatText>>(){}.getType());
    }
}
