package model;

import controller.UserController;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    String user;
    Socket server;

    public Player(String user, Socket server) {
        this.user = user;
        this.server = server;
        //TODO request to server
    }

    public void listenForTurn() throws IOException {
        DataOutputStream outputStreamGame = new DataOutputStream(server.getOutputStream());
        DataInputStream inputStreamGame = new DataInputStream(server.getInputStream());
        Request request = new Request();
        request.setHeader("register_turn");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        outputStreamGame.writeUTF(request.toJson());
        outputStreamGame.flush();

        while (true) {
            try {
                System.out.println("Waiting for update of game");
                Response response = Response.fromJson(inputStreamGame.readUTF());
                System.out.println("update of game received");
                if (response.getStatus() == 200) {
                    //TODO draw game pane on stage
                    break;
                }
                else if (response.getStatus() == 400) {
                    //TODO end of player turn maybe win maybe lose
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
