package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ChatGroup;
import model.Request;
import model.Response;
import model.User;
import Enum.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class SocketHandler extends Thread{
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public SocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Request request = Request.fromJson(inputStream.readUTF());
                System.out.println("New request from " + socket);
                Response response = handleRequest(request);
                outputStream.writeUTF(response.toJson());
                outputStream.flush();
            }
        } catch (IOException ignored) {
            //TODO any other things
        }
    }

    private Response handleRequest(Request request) throws IOException {
        Response response = new Response();

        switch (request.getHeader()) {
            case "nullCurrentImage" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                user.setCurrentImage(null);
                response.setStatus(200);
            } case "lastTime" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                user.setLastLoginTime((String) request.getData().get("time"));
                response.setStatus(200);
                update();
            } case "loginUser" -> {
                response = handleLoginUser(request);
                update();
            } case "createUser" -> {
                response = handleCreateUser(request);
                update();
            } case "getUsername" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                response.setStatus(200);
                response.addData("username", user.getUsername());
            } case "getScore" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                response.setStatus(200);
                response.addData("score", user.getScore());
            } case "profileImage" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                response.setStatus(200);
                response.addData("image", user.getProfileImage());
            } case "currentImage" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                response.setStatus(200);
                response.addData("image", user.getCurrentImage());
            } case "setProfileImage" -> {
                response = handleSetProfileImage(request);
                update();
            } case "setImage" -> {
                response = handleSetImage(request);
                update();
            } case "setCurrentImage" -> {
                response = handleSetCurrentImage(request);
                update();
            } case "getListOfChatTexts" -> {
                response = ChatController.getListOfAllChatTexts(request);
            } case "getListOfChatsUser" -> {
                response = ChatController.getListOfAllChatsUser(request);
            } case "getListOfUsers" -> {
                response = ChatController.getListOfAllUsers(request);
            } case "addChatText" -> {
                response = ChatController.addChatText(request);
            } case "deleteChatText" -> {
                response = ChatController.delete(request);
            } case "setSeen" -> {
                response = ChatController.setSeen(request);
            } case "setText" -> {
                response = ChatController.setText(request);
            } case "setDeleted" -> {
                response = ChatController.setDeleted(request);
            } case "addChatGroup" -> {
                response = ChatController.addChatGroup(request);
            } case "getListOfChats" -> {
                response = ChatController.getListOfAllChats(request);
            } case "scoreBoardList" -> {
                response = handleScoreBoardList(request);
            } case "getImage" -> {
                response = handleGetImage(request);
            } case "register_update" -> {
                String token = (String) request.getData().get("token");
                User user = User.findUserByToken(token);
                System.out.println("Registered update socket for " + token);
                user.setUpdateSocket(socket);
            } case "changeNickname" -> {
                response = ProfileController.getInstance().handleChangeNickname(request);
            } case "changePassword" -> {
                response = ProfileController.getInstance().handleChangePassword(request);
            } case "addFriendship" -> {
                response = FriendshipController.addFriendship(request);
            } case "setAccepted" -> {
                response = FriendshipController.setAccepted(request);
            } case "listOfFriendshipRequest" -> {
                response = FriendshipController.listOfFriendshipRequest(request);
            } case "listOfSenderFriendship" -> {
                response = FriendshipController.listOfSenderFriendship(request);
            } case "listOfFriends" -> {
                response = FriendshipController.listOfFriends(request);
            } case "logout" -> {
                response = handleLogout(request);
            }
            default -> {
                response.setStatus(400);
                response.addData("error", "invalid command");
            }
        }

        return response;
    }

    private Response handleLogout(Request request) {
        Response response = new Response();

        String token = (String) request.getData().get("token");

        response.setStatus(200);
        try {
            User.findUserByToken(token).isConnect = true;
        } catch (NullPointerException e) {
            response.setStatus(400);
        }

        return response;
    }

    public void update() {
        Response update = new Response();
        update.setStatus(400);
        update.addData("update", "update");
        for (int i = 0; i < User.getListOfUsers().size(); i++) {
            User receiver = User.getListOfUsers().get(i);
            try {
                if (receiver.getUpdateOutputStream() == null) continue;
                receiver.getUpdateOutputStream().writeUTF(update.toJson());
                receiver.getUpdateOutputStream().flush();
            } catch (IOException ignored) {
                //Nothing
            }
        }
    }

    private Response handleGetImage(Request request) {
        Response response = new Response();

        User user = User.findUser((String) request.getData().get("username"));
        response.addData("image", new Gson().toJson(user.getImage()));

        response.setStatus(200);
        return response;
    }

    private Response handleScoreBoardList(Request request) {
        Response response = new Response();
        User.sort();
        response.addData("list", new Gson().toJson(User.getListOfUsers()));
        response.setStatus(200);
        return response;
    }

    private Response handleSetImage(Request request) {
        Response response = new Response();
        User user = User.findUserByToken((String) request.getData().get("token"));
        //byte[] image = new Gson().fromJson((String) request.getData().get("image"), new TypeToken<byte []>(){}.getType());
        //byte[] image = (byte[]) request.getData().get("image");

        //ProfileController.getInstance().settingProfile(user);
        response.setStatus(200);
        return response;
    }

    private Response handleSetProfileImage(Request request) {
        Response response = new Response();
        User user = User.findUserByToken((String) request.getData().get("token"));
        String image = (String) request.getData().get("image");

        user.setProfileImage(image);
        response.setStatus(200);
        return response;
    }

    private Response handleSetCurrentImage(Request request) {
        Response response = new Response();
        User user = User.findUserByToken((String) request.getData().get("token"));
        String image = (String) request.getData().get("image");

        user.setCurrentImage(image);
        response.setStatus(200);
        return response;
    }

    private Response handleCreateUser(Request request) {
        Response response = new Response();

        String username = (String) request.getData().get("username");
        String password = (String) request.getData().get("password");
        String nickname = (String) request.getData().get("nickname");
        Message message = LoginController.getInstance().createUser(username, password, nickname);

        if (message == Message.CREATE_USER_SUCCESSFUL) {
            response.setStatus(200);
            try {
                response.addData("token", User.findUser(username).getToken());
                User.findUser(username).isConnect = true;
            } catch (NullPointerException e) {
                response.setStatus(400);
            }
        }
        else response.setStatus(400);

        response.addData("message", message.toString());

        return response;
    }

    private Response handleLoginUser(Request request) {
        Response response = new Response();

        String username = (String) request.getData().get("username");
        String password = (String) request.getData().get("password");
        Message message = LoginController.getInstance().loginUser(username, password);

        if (message == Message.LOGIN_SUCCESSFUL) {
            response.setStatus(200);
            try {
                response.addData("token", User.findUser(username).getToken());
                User.findUser(username).isConnect = true;
            } catch (NullPointerException e) {
                response.setStatus(400);
            }
        }
        else response.setStatus(400);

        response.addData("message", message.toString());

        return response;
    }
}
