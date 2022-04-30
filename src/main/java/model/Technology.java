package model;
import Enum.TechnologyType;
public class Technology {
    private TechnologyType technologyType;
    private int timeRemain;

    public int getTimeRemain() {
        return timeRemain;
    }

    public void decreaseTimeRemain(int amount) {
        this.timeRemain -= amount;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }
    public Technology(TechnologyType technologyType){
        this.technologyType=technologyType;
        this.timeRemain=technologyType.getTime();
    }
}
