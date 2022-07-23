package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        Request request = new Request();
        request.setHeader("addingPlayer");
        request.addData("user", user);
        Response response = NetworkController.send(request);

        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Double getYear() {
        Double year = 0.0;
        Request request = new Request();
        request.setHeader("getYear");
        Response response = NetworkController.send(request);

        if (response != null) year = (Double) response.getData().get("year");
        return year;
    }

    public void nextTurn() {
        Request request = new Request();
        request.setHeader("nextTurn");
        Response response = NetworkController.send(request);
        if (response != null && response.getStatus() == 200) {
            try {
                listenForTurn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        server = new Socket("localhost", 8000);
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

    public Double getHappiness() {
        Double happiness = 0.0;
        Request request = new Request();
        request.setHeader("getHappiness");
        request.addData("player", user);
        Response response = NetworkController.send(request);

        if (response != null) happiness = (Double) response.getData().get("happiness");
        return happiness;
    }

    public Double getScience() {
        Double science = 0.0;
        Request request = new Request();
        request.setHeader("getScience");
        request.addData("player", user);
        Response response = NetworkController.send(request);

        if (response != null) science = (Double) response.getData().get("science");
        return science;
    }

    public Double getGold() {
        Double gold = 0.0;
        Request request = new Request();
        request.setHeader("getGold");
        request.addData("player", user);
        Response response = NetworkController.send(request);

        if (response != null) gold = (Double) response.getData().get("gold");
        return gold;
    }

    public Technology getUnderConstructionTechnology() {
        Technology technology = null;
        Request request = new Request();
        request.setHeader("getUnderConstructionTechnology");
        request.addData("player", user);
        Response response = NetworkController.send(request);

        if (response != null) technology = new Gson().fromJson((String) response.getData().get("technology"), new TypeToken<Technology>(){}.getType());
        return technology;
    }
}
