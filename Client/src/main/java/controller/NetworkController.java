package controller;

import model.Request;
import model.Response;
import com.google.gson.Gson;
import model.UserForScoreBoard;
import viewControllers.ScoreBoardView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkController {
    private static Socket socket;
    private static Socket readerSocket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

    public static boolean connect() {
        try {
            socket = new Socket("localhost", 8000);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            return false;
        }
        return true;
    }

    public static void listenForUpdates(ScoreBoardView scoreBoardView) throws IOException {
        readerSocket = new Socket("localhost", 8000);
        DataOutputStream outputStream = new DataOutputStream(readerSocket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(readerSocket.getInputStream());
        Request request = new Request();
        request.setHeader("register_update");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        outputStream.writeUTF(request.toJson());
        outputStream.flush();
        /*new Thread(() -> {
            while (!ScoreBoardView.getInstance().isBack) {
                try {
                    System.out.println("Waiting for update");
                    Response response = Response.fromJson(inputStream.readUTF());
                    System.out.println("update received");
                    if (!ScoreBoardView.getInstance().isBack) handleUpdate(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!scoreBoardView.isBack) {
                    try {
                        System.out.println("Waiting for update");
                        Response response = Response.fromJson(inputStream.readUTF());
                        System.out.println("update received");
                        if (!scoreBoardView.isBack) handleUpdate(response, scoreBoardView);
                        else break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void handleUpdate(Response response, ScoreBoardView scoreBoardView) {
        //UserForScoreBoard.sort();
        if (!scoreBoardView.isBack) scoreBoardView.initializing();
        System.out.println("update");
        //String messageJson = new Gson().toJson(response.getData().get("message"));
        //Message message = new Gson().fromJson(messageJson, Message.class);
        //int fromId = (int) Math.floor((Double) response.getData().get("fromId"));
        //ChatController.addMessageToChat(fromId, message);
    }

    public static Response send(Request request) {
        try {
            outputStream.writeUTF(request.toJson());
            outputStream.flush();
            return Response.fromJson(inputStream.readUTF());
        } catch (IOException ignored) {
            System.out.println("oh no!");
            return null;
        }
    }
}
