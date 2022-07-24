package model;
import Enum.ImprovementType;
import com.google.gson.Gson;
import controller.NetworkController;

public class Improvement {
    private ImprovementType improvementType;
    private double turnRemained;
    private int ground;
    public Improvement(ImprovementType improvementType, double turnRemained, int ground){
        this.improvementType=improvementType;
        this.turnRemained=turnRemained;
        this.ground = ground;
        //TODO make request when build it
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public Double getTurnRemained() {
        Request request = new Request();
        request.setHeader("getTurnRemained");
        request.addData("improvementType", new Gson().toJson(improvementType));
        request.addData("groundNumber", ground);
        Response response = NetworkController.send(request);
        return  (Double) response.getData().get("turnRemained");
    }
}
