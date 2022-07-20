package controller;

import model.Request;
import model.Response;
import model.User;

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
                response.setStatus(400);
            } case "lastTime" -> {
                User user = User.findUserByToken((String) request.getData().get("token"));
                user.setLastLoginTime((String) request.getData().get("time"));
                response.setStatus(400);
            }
            default -> {
                response.setStatus(400);
                response.addData("error", "invalid command");
            }
        }

        return response;
    }
}
