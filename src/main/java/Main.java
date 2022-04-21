import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import view.LoginMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("dataBase.txt")));
        ArrayList<User> listOfUsers;
        listOfUsers = new Gson().fromJson(json, new TypeToken<List<User>>(){}.getType());
        if (listOfUsers == null) listOfUsers = new ArrayList<>();
        User.setListOfUsers(listOfUsers);

        LoginMenu.getInstance().run();

        FileWriter dataBase = new FileWriter("dataBase.txt");
        dataBase.write(new Gson().toJson(User.getListOfUsers()));
        dataBase.close();
    }
}
