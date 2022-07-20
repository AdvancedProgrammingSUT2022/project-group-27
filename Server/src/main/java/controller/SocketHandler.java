package controller;

import model.Request;
import model.Response;
import model.User;
import Enum.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
            } case "loginUser" -> {
                response = handleLoginUser(request);
            } case "createUser" -> {
                response = handleCreateUser(request);
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
            } case "setImage" -> {
                response = handleSetImage(request);
            }
            default -> {
                response.setStatus(400);
                response.addData("error", "invalid command");
            }
        }

        return response;
    }

    private Response handleSetImage(Request request) {
        Response response = new Response();
        User user = User.findUserByToken((String) request.getData().get("token"));
        byte[] image = (byte[]) request.getData().get("image");

        user.setImage(image);
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
            } catch (NullPointerException e) {
                response.setStatus(400);
            }
        }
        else response.setStatus(400);

        response.addData("message", message.toString());

        return response;
    }
}
