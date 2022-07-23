package model;

import controller.NetworkController;
import controller.UserController;
import javafx.stage.Stage;
import viewControllers.GraphicOfGame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> list = new ArrayList<>();
    private GraphicOfGame graphicOfGame;
    private String user;
    private Socket server;
    private Stage stage;

    public Player(String user, Socket server, Stage stage) {
        this.user = user;
        this.server = server;
        this.stage = stage;

        GraphicOfGame game = new GraphicOfGame();
        game.setPlayerInstance(this);
        graphicOfGame = game;
        list.add(this);
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO request to server
        Request request = new Request();
        request.setHeader("addingPlayer");
        request.addData("user", user);
        Response response = NetworkController.send(request);
    }

    public String getUser() {
        return user;
    }

    public static Player getPlayerByUser(String user) {
        for (Player player: list) {
            if (player.user.equals(user)) return player;
        }

        return null;
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

    public int getHappiness() {
        int happiness = 0;
        //TODO get it from server
        return happiness;
    }
}
