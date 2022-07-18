package database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.ChatGroup;
import model.Player;
import model.Unit;
import model.User;
//import com.gilecode.yagson.YaGson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static void readGameDatabase() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("game.txt")));
        ArrayList<Player> list;
        //YaGson gson = new YaGson();
        //Gson gson = new GsonBuilder().registerTypeAdapter(Unit.class, new UnitTypeAdapter()).create();
        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<List<Player>>(){}.getType());
        if (list == null) list = new ArrayList<>();
        Player.setAllPlayer(list);
    }

    public static void writeGameDatabase() throws IOException {
        FileWriter dataBase = new FileWriter("game.txt");
        //YaGson gson = new YaGson();
        Gson gson = new Gson();
        dataBase.write(gson.toJson(Player.getAllPlayers()));
        dataBase.close();
    }

    public static void writeGameDatabase(int number) throws IOException {
        FileWriter dataBase = new FileWriter(number + ".txt");
        //YaGson gson = new YaGson();
        Gson gson = new Gson();
        dataBase.write(gson.toJson(Player.getAllPlayers()));
        dataBase.close();
    }

    public static void readFromDatabase() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("database.txt")));
        ArrayList<User> listOfUsers;
        listOfUsers = new Gson().fromJson(json, new TypeToken<List<User>>(){}.getType());
        if (listOfUsers == null) listOfUsers = new ArrayList<>();
        User.setListOfUsers(listOfUsers);

        json = new String(Files.readAllBytes(Paths.get("chat.txt")));
        ArrayList<ChatGroup> listOfChatGroups;
        listOfChatGroups = new Gson().fromJson(json, new TypeToken<List<ChatGroup>>(){}.getType());
        if (listOfChatGroups == null) listOfChatGroups = new ArrayList<>();
        ChatGroup.setListOfGroups(listOfChatGroups);
    }

    public static void writeOnDataBase() throws IOException {
        FileWriter dataBase = new FileWriter("database.txt");
        dataBase.write(new Gson().toJson(User.getListOfUsers()));
        dataBase.close();
        FileWriter dataBaseChat = new FileWriter("chat.txt");
        dataBaseChat.write(new Gson().toJson(ChatGroup.getListOfGroups()));
        dataBaseChat.close();
    }
}
