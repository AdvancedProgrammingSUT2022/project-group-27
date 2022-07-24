package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import Enum.*;

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
            } case "listOfOnlineUsers" -> {
                response = listOfOnlineUsers(request);
            } case "startGame" -> {
                startGame((String) request.getData().get("list"), (String) request.getData().get("token"));
                response.setStatus(200);
                response.addData("message","invitation sent to users");
            } case "updateInvitationList" -> {
                response.setStatus(200);
                response.addData("list",User.findUser((String) request.getData().get("token")).getListOfInvitation());
            } case "rejectInvitation" -> {
                User.findUser((String) request.getData().get("user")).removeInvitation((String) request.getData().get("token"));
                notStartingGame(User.findUser((String) request.getData().get("token")));
            } case "acceptInvitation" -> {
                response.setStatus(200);
                User.findUser((String) request.getData().get("token")).addToAccepted((String) request.getData().get("admin"));
                User.findUser((String) request.getData().get("admin")).removeInvitation((String) request.getData().get("token"));

                if (User.findUser((String) request.getData().get("token")).canWeStart()) {
                    startingGame();
                    response.setStatus(201);
                }
            } case "register_startGame" -> {
                String token = (String) request.getData().get("token");
                User user = User.findUserByToken(token);
                System.out.println("Registered start game socket for " + token);
                user.setStartGameSocket(socket);
            } case "addingPlayer" -> {
                User user = User.findUser((String) request.getData().get("user"));
                Player player = new Player(user);
                player.setSocket(socket);
            } case "register_turn" -> {
                String token = (String) request.getData().get("token");
                User user = User.findUserByToken(token);
                Player player = Player.findPlayerByUser(user);
                System.out.println("Registered turn socket for " + token);
                if (player != null) player.setSocket(socket);
            } case "getScience" -> {
                Player player = Player.findPlayerByUser(User.findUser((String) request.getData().get("player")));
                response.setStatus(200);
                response.addData("science", player.getScience());
            } case "getGold" -> {
                Player player = Player.findPlayerByUser(User.findUser((String) request.getData().get("player")));
                response.setStatus(200);
                response.addData("gold", player.getGold());
            } case "getYear" -> {
                response.setStatus(200);
                response.addData("year", Player.getYear());
            } case "getUnderConstructionTechnology" -> {
                Player player = Player.findPlayerByUser(User.findUser((String) request.getData().get("player")));
                response.setStatus(200);
                response.addData("technology", player.getUnderConstructionTechnology());
            } case "getHappiness" -> {
                Player player = Player.findPlayerByUser(User.findUser((String) request.getData().get("player")));
                response.setStatus(200);
                response.addData("happiness", player.getHappiness());
            } case "nextTurn" -> {
                if (Game.getInstance().canWeNextTurn()) {
                    response.setStatus(200);
                    Player.nextTurn();
                    turnPlayerNotify();
                } else response.setStatus(400);
            } case "canWeNextTurn" -> {
                if (Game.getInstance().canWeNextTurn()) response.setStatus(200);
                else response.setStatus(400);
            } case "settingGame" -> {
                ArrayList<String> users = new Gson().fromJson((String) request.getData().get("users"), new TypeToken<ArrayList<String>>(){}.getType());
                Double seed = (Double) request.getData().get("seed");
                GameModel.getInstance().setting(users, seed);
            } case "getTurnRemained" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                String improvementType = (String) request.getData().get("improvementType");
                if (Ground.getGroundByNumber(groundNumber).getPlunderingImprovementType().getImprovementType().toString().equals(improvementType))
                    response.addData("turnRemained", Ground.getGroundByNumber(groundNumber).getPlunderingImprovementType().getTurnRemained());
                if (Ground.getGroundByNumber(groundNumber).getImprovementTypeInProgress().getImprovementType().toString().equals(improvementType))
                    response.addData("turnRemained", Ground.getGroundByNumber(groundNumber).getImprovementTypeInProgress().getTurnRemained());
            } case "listOfGrounds" -> {
                response.addData("listOfGrounds", new Gson().toJson(Ground.getAllGround()));
            } case "doWeHaveThisTechnology" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String technologyType = (String) request.getData().get("technologyType");
                Boolean answer = player.doWeHaveThisTechnology(TechnologyType.findByName(technologyType));
                response.addData("answer", answer);
            } case "canWeAddThisTechnology" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String technologyType = (String) request.getData().get("technologyType");
                Boolean answer = player.canWeAddThisTechnology(TechnologyType.findByName(technologyType));
                response.addData("answer", answer);
            } case "getTimeRemain" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String technologyType = (String) request.getData().get("technologyType");
                response.addData("timeRemain",player.getUnderConstructionTechnology().getTimeRemain());
            } case "setUnderConstructionTechnology" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String technologyType = (String) request.getData().get("technologyType");
                Technology technology = null;
                for (Technology technology1: player.technologiesThatCanBeObtained()) {
                    if (technology1.getTechnologyType().name().equals(technologyType)) technology = technology1;
                }
                player.setUnderConstructionTechnology(technology);
            } case "getTurnRemainedToComplete" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String militaryType = (String) request.getData().get("militaryType");
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                response.addData("timeRemain", City.findCityByGround(Ground.getGroundByNumber(groundNumber), player).getRemainedTurnsToBuild());
            }  case "getStatus" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String militaryType = (String) request.getData().get("militaryType");
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                for (Unit unit: player.getUnits()) {
                    if (unit.getGround().getNumber() == groundNumber && unit.getMilitaryType().name().equals(militaryType)) response.addData("status", unit.getStatus());
                }
            } case "getImprovementTypeInProgress" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                Improvement improvement = Ground.getGroundByNumber(groundNumber).getImprovementTypeInProgress();
                response.addData("improvementType", improvement.getImprovementType().toString());
                response.addData("timeRemain", improvement.getTurnRemained());
            } case "getHp" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String militaryType = (String) request.getData().get("militaryType");
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                for (Unit unit: player.getUnits()) {
                    if (unit.getGround().getNumber() == groundNumber && unit.getMilitaryType().name().equals(militaryType)) response.addData("hp", unit.getHp());
                }
            } case "getMp" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                String militaryType = (String) request.getData().get("militaryType");
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                for (Unit unit: player.getUnits()) {
                    if (unit.getGround().getNumber() == groundNumber && unit.getMilitaryType().name().equals(militaryType)) response.addData("mp", unit.getHp());
                }
            } case "getCities" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("player")));
                ArrayList<Integer> list = new ArrayList<>();
                for (City city: player.getCities()) {
                    list.add(city.getGround().getNumber());
                }
                response.addData("cities", new Gson().toJson(list));
            } case "getWasClearedToSeeGrounds" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                Boolean answer = player.getWasClearedToSeeGrounds().contains(Ground.getGroundByNumber(groundNumber));
                response.addData("answer", answer);
            } case "getStrategicResources" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                response.addData("list", new Gson().toJson(Ground.getGroundByNumber(groundNumber).getStrategicResources()));
            } case "getLuxuryResources" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                response.addData("list", new Gson().toJson(Ground.getGroundByNumber(groundNumber).getLuxuryResources()));
            } case "getBonusResource" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                response.addData("list", new Gson().toJson(Ground.getGroundByNumber(groundNumber).getBonusResource()));
            } case "getFeatureType" -> {
                int groundNumber = (int) Math.floor((Double) request.getData().get("groundNumber"));
                response.addData("feature", new Gson().toJson(Ground.getGroundByNumber(groundNumber).getFeatureType()));
            } case "handleVisitedGrounds" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                player.handleVisitedGrounds();
            } case "handleClearToSee" -> {
                Player player = Player.findPlayerByUser(User.findUserByToken((String) request.getData().get("token")));
                player.handleClearToSee();
            } case "moveUnits" -> {
                int first = (int) Math.floor((Double) request.getData().get("firstGroundNumber"));
                int second = (int) Math.floor((Double) request.getData().get("secondGroundNumber"));
                String type = (String) request.getData().get("type");
                Game.getInstance().moveUnits(first, second, type);
            }
            default -> {
                response.setStatus(400);
                response.addData("error", "invalid command");
            }
        }

        return response;
    }

    private void turnPlayerNotify() {
        Response update = new Response();
        update.setStatus(200);
        update.addData("status", "you");
        Player player = Player.whichPlayerTurnIs();
        try {
            if (player.getDataOutputStream() == null) return;
            player.getDataOutputStream().writeUTF(update.toJson());
            player.getDataOutputStream().flush();
        } catch (IOException ignored) {
            //Nothing
        }
    }

    private void startGame(String list, String token) {
        String s[]=list.split(",");
        ArrayList<String> arrayList=new ArrayList<>();
        for (int i=0;i<s.length;i++){
            if (s[i].trim().equals("")) continue;
            arrayList.add(s[i].trim());
        }
        String admin=token;
        for (String string : arrayList){
            User user=User.findUser(string);
            if (string.equals(admin)) continue;
            user.addInvitation(admin);
            User.findUser(admin).addToAdminList(string);
            System.out.println("admin = "+ User.findUser(admin).getUsername());
        }
    }

    private Response listOfOnlineUsers(Request request) {
        Response response = new Response();

        User user = User.findUserByToken((String) request.getData().get("token"));
        ArrayList<String> allUsers = new ArrayList<>();
        for (User user1: User.getListOfUsers()) if (user1.isConnect) allUsers.add(user1.getUsername());
        response.addData("list", new Gson().toJson(allUsers));
        response.setStatus(200);
        return response;

    }

    private Response handleLogout(Request request) {
        Response response = new Response();

        String token = (String) request.getData().get("token");

        response.setStatus(200);
        try {
            User.findUserByToken(token).isConnect = false;
        } catch (NullPointerException e) {
            response.setStatus(400);
        }

        return response;
    }

    public void notStartingGame(User user) {
        if (user!= null) user.clearAcceptedUsers();
        Response update = new Response();
        update.setStatus(400);
        update.addData("status", "no");
        for (int i = 0; i < User.getListOfUsers().size(); i++) {
            User receiver = User.getListOfUsers().get(i);
            try {
                if (receiver.getStartGameOutputStream() == null) continue;
                receiver.getStartGameOutputStream().writeUTF(update.toJson());
                receiver.getStartGameOutputStream().flush();
            } catch (IOException ignored) {
                //Nothing
            }
        }
    }

    public void startingGame() {
        Response update = new Response();
        update.setStatus(200);
        update.addData("status", "yes");
        for (int i = 0; i < User.getListOfUsers().size(); i++) {
            User receiver = User.getListOfUsers().get(i);
            try {
                if (receiver.getStartGameOutputStream() == null) continue;
                receiver.getStartGameOutputStream().writeUTF(update.toJson());
                receiver.getStartGameOutputStream().flush();
            } catch (IOException ignored) {
                //Nothing
            }
        }
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
