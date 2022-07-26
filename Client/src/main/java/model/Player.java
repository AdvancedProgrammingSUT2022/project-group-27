package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import controller.UserController;
import javafx.stage.Stage;
import viewControllers.GraphicOfGame;
import Enum.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
    private static ArrayList<Player> list = new ArrayList<>();
    private GraphicOfGame graphicOfGame;
    private String user;
    private Socket server;
    private Stage stage;
    private ArrayList<City> cities = new ArrayList<>();

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

    public static ArrayList<Player> getAllPlayers() {
        return list;
    }

    public ArrayList<City> getCities() {
        Request request = new Request();
        request.setHeader("getCities");
        request.addData("player", user);
        Response response = NetworkController.send(request);
        ArrayList<Integer> citiesGroundNumbers =  new Gson().fromJson((String) response.getData().get("cities"), new TypeToken<ArrayList<Integer>>(){}.getType());
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            if (!citiesGroundNumbers.contains(city.groundNumber())) cities.remove(city);
        }

        return cities;
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

    public boolean canWeNextTurn() {
        Request request = new Request();
        request.setHeader("canWeNextTurn");
        Response response = NetworkController.send(request);
        return response != null && response.getStatus() == 200;
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

    public Double getFood() {
        Double food = 0.0;
        Request request = new Request();
        request.setHeader("getPlayerFood");
        request.addData("player", user);
        Response response = NetworkController.send(request);

        if (response != null) food = (Double) response.getData().get("food");
        return food;
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

    public boolean doWeHaveThisTechnology(TechnologyType technologyType) {
        Request request = new Request();
        request.setHeader("doWeHaveThisTechnology");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("technologyType", technologyType);
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public boolean canWeAddThisTechnology(TechnologyType technologyType) {
        Request request = new Request();
        request.setHeader("canWeAddThisTechnology");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("technologyType", technologyType);
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public void setUnderConstructionTechnology(Technology technology) {
        Request request = new Request();
        request.setHeader("setUnderConstructionTechnology");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("technologyType", technology.getTechnologyType());
        Response response = NetworkController.send(request);
    }

    public boolean getWasClearedToSeeGrounds(Ground ground) {
        Request request = new Request();
        request.setHeader("getWasClearedToSeeGrounds");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public void handleVisitedGrounds() {
        Request request = new Request();
        request.setHeader("handleVisitedGrounds");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
    }

    public void handleClearToSee() {
        Request request = new Request();
        request.setHeader("handleClearToSee");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
    }

    public int getCapitalGroundNumber() {
        Request request = new Request();
        request.setHeader("getCapitalGroundNumber");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
        return (int) Math.floor((Double) response.getData().get("number"));
    }

    public boolean getClearToSeeGrounds(Ground ground) {
        Request request = new Request();
        request.setHeader("getClearToSeeGrounds");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("groundNumber", ground.getNumber());
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public ArrayList<Unit> getUnits() {
        Request request = new Request();
        request.setHeader("getUnits");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("units"), new TypeToken<ArrayList<Unit>>(){}.getType());
    }

    public boolean checkDies() {
        Request request = new Request();
        request.setHeader("checkDies");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
        return (boolean) response.getData().get("answer");
    }

    public void setScoreAndTimeAtEnd(boolean isWin) {
        Request request = new Request();
        request.setHeader("setScoreAndTimeAtEnd");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        Response response = NetworkController.send(request);
    }

    public Double getScore() {
        Request request = new Request();
        request.setHeader("getScore");
        request.addData("token", user);
        Response response = NetworkController.send(request);
        if (response == null) return null;

        return (Double) response.getData().get("score");
    }

    public void increaseGold(int number) {
        Request request = new Request();
        request.setHeader("increaseGold");
        request.addData("token", user);
        request.addData("number", number);
        Response response = NetworkController.send(request);
    }

    public void increaseHappiness(int number) {
        Request request = new Request();
        request.setHeader("increaseHappiness");
        request.addData("token", user);
        request.addData("number", number);
        Response response = NetworkController.send(request);
    }

    public void increaseScore(int number) {
        Request request = new Request();
        request.setHeader("increaseScore");
        request.addData("token", user);
        request.addData("number", number);
        Response response = NetworkController.send(request);
    }

    public ArrayList<Boolean> getIsInWar() {
        Request request = new Request();
        request.setHeader("getIsInWar");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Boolean>>(){}.getType());
    }

    public double countScore() {
        Request request = new Request();
        request.setHeader("countScore");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        return (double) response.getData().get("score");
    }

    public ArrayList<TechnologyType> getTechnologyType() {
        Request request = new Request();
        request.setHeader("getTechnologyType");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<TechnologyType>>(){}.getType());
    }

    public void setInWar(Player other) {
        Request request = new Request();
        request.setHeader("setInWar");
        request.addData("user", user);
        request.addData("other", other);
        Response response = NetworkController.send(request);
    }

    public ArrayList<StrategicResource> getAllStrategicResources() {
        Request request = new Request();
        request.setHeader("getAllStrategicResources");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<StrategicResource>>(){}.getType());
    }

    public ArrayList<LuxuryResource> getAllLuxuryResources() {
        Request request = new Request();
        request.setHeader("getAllLuxuryResources");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<LuxuryResource>>(){}.getType());
    }

    public ArrayList<Notification> getNotificationHistory() {
        Request request = new Request();
        request.setHeader("getNotificationHistory");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Notification>>(){}.getType());
    }

    public ArrayList<Technology> getAllTechnologyTypes() {
        Request request = new Request();
        request.setHeader("getAllTechnologyTypes");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Technology>>(){}.getType());
    }

    public ArrayList<Technology> technologiesThatCanBeObtained() {
        Request request = new Request();
        request.setHeader("technologiesThatCanBeObtained");
        request.addData("user", user);
        Response response = NetworkController.send(request);
        if (response == null) return new ArrayList<>();

        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<Technology>>(){}.getType());
    }
}
