package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;

import java.util.ArrayList;
import java.util.List;

public class River {
    private static ArrayList<River> allRivers = new ArrayList<>();
    private Ground firstGround, secondGround;

    public static ArrayList<River> getAllRivers() {
        Request request = new Request();
        request.setHeader("getAllRivers");
        Response response = NetworkController.send(request);
        allRivers =  new Gson().fromJson((String) response.getData().get("rivers"), new TypeToken<ArrayList<River>>(){}.getType());
        return allRivers;
    }

    public Ground getFirstGround() {
        return firstGround;
    }

    public Ground getSecondGround() {
        return secondGround;
    }
}
