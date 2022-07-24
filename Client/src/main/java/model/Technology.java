package model;
import Enum.TechnologyType;
import controller.NetworkController;
import controller.UserController;

public class Technology {
    private final TechnologyType technologyType;
    private int timeRemain;

    public int getTimeRemain() {
        Request request = new Request();
        request.setHeader("getTimeRemain");
        request.addData("token", UserController.getInstance().getUserLoggedIn());
        request.addData("technologyType", technologyType);
        Response response = NetworkController.send(request);
        timeRemain = (int) Math.floor((Double) response.getData().get("timeRemain"));
        return timeRemain;
    }

    public void setTimeRemain(int timeRemain) {
        this.timeRemain = timeRemain;
    }

    public void decreaseTimeRemain(int amount) {
        this.timeRemain -= amount;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public Technology(TechnologyType technologyType, int timeRemain){
        this.technologyType = technologyType;
        this.timeRemain = timeRemain;
    }
}
