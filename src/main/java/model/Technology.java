package model;
import Enum.TechnologyType;
public class Technology {
    private final TechnologyType technologyType;
    private int timeRemain;

    public int getTimeRemain() {
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

    public Technology(TechnologyType technologyType,int timeRemain){
        this.technologyType = technologyType;
        this.timeRemain = timeRemain;
    }
}
