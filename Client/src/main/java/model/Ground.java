package model;

import Enum.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.NetworkController;
import controller.UserController;

import java.util.ArrayList;
import java.util.HashMap;

public class Ground {
    private int counterOfDestroyingFeature=0;
    private Road road=null;
    private RailWay railWay=null;
    private static ArrayList<Ground> allGround = new ArrayList<>();
    private static HashMap<Integer, Ground> pixelInWhichGround = new HashMap<>();
    private ImprovementType improvementType=null;
    private Improvement plunderingImprovementType = null;
    private Improvement improvementTypeInProgress=null;
    private ArrayList<Pair> pixelsOfThisGround = new ArrayList<>();
    //private transient ArrayList<Ground> adjacentGrounds = new ArrayList<>();
    private ArrayList<Integer> adjacentGrounds = new ArrayList<>();
    private int xLocation;
    private int yLocation;
    private int number;
    private int cost = 50;
    private boolean isWorkedOn = false;
    private GroundType groundType;
    private FeatureType featureType;
    private ArrayList<BonusResource> bonusResource = new ArrayList<>();
    private ArrayList<StrategicResource> strategicResources = new ArrayList<>();
    private ArrayList<LuxuryResource> luxuryResources = new ArrayList<>();
    private boolean hasRuin = false;

    public static ArrayList<Ground> getAllGround() {
        Request request = new Request();
        request.setHeader("listOfGrounds");
        Response response = NetworkController.send(request);
        allGround = new Gson().fromJson((String) response.getData().get("listOfGrounds"), new TypeToken<ArrayList<Ground>>(){}.getType());
        return allGround;
    }

    public static Ground getGroundByNumber(int number) {
        for (Ground ground: allGround) {
            if (ground.number == number) return ground;
        }

        return null;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getNumber() {
        return number;
    }

    public Improvement getImprovementTypeInProgress() {
        Request request = new Request();
        request.setHeader("getImprovementTypeInProgress");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        ImprovementType improvementType = ImprovementType.getImprovementByName((String) response.getData().get("improvementType"));
        int timeRemain = (int) Math.floor((Double) response.getData().get("timeRemain"));
        return new Improvement(improvementType, timeRemain, this.getNumber());
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public ArrayList<StrategicResource> getStrategicResources() {
        Request request = new Request();
        request.setHeader("getStrategicResources");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<StrategicResource>>(){}.getType());
    }

    public ArrayList<LuxuryResource> getLuxuryResources() {
        Request request = new Request();
        request.setHeader("getLuxuryResources");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<LuxuryResource>>(){}.getType());
    }

    public ArrayList<BonusResource> getBonusResource() {
        Request request = new Request();
        request.setHeader("getBonusResource");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<BonusResource>>(){}.getType());
    }

    public FeatureType getFeatureType() {
        Request request = new Request();
        request.setHeader("getFeatureType");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("feature"), new TypeToken<FeatureType>(){}.getType());
    }

    public void setImprovementTypeInProgress(ImprovementType improvementType) {
        Request request = new Request();
        request.setHeader("setImprovementTypeInProgress");
        request.addData("groundNumber", this.getNumber());
        request.addData("improvementType", improvementType);
        Response response = NetworkController.send(request);
    }

    public ArrayList<ImprovementType> listOfImprovementTypes() {
        Request request = new Request();
        request.setHeader("listOfImprovementTypes");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("list"), new TypeToken<ArrayList<ImprovementType>>(){}.getType());
    }

    public ImprovementType getImprovementType() {
        Request request = new Request();
        request.setHeader("getImprovementType");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
        return new Gson().fromJson((String) response.getData().get("improvement"), new TypeToken<ImprovementType>(){}.getType());
    }

    public void deleteRoadAndRailway() {
        Request request = new Request();
        request.setHeader("deleteRoadAndRailway");
        request.addData("groundNumber", this.getNumber());
        Response response = NetworkController.send(request);
    }
}
