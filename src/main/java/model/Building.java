package model;

import Enum.BuildingsType;

public class Building implements Productions{
    private BuildingsType type;
    private City city;
    private RemainedTurns turnRemainedToCompleted = new RemainedTurns(0);

    public Building(BuildingsType type, City city) {
        this.type = type;
        this.city = city;
    }

    public void setTurnRemainedToCompleted(int turnRemainedToCompleted) {
        this.turnRemainedToCompleted.setTurns(turnRemainedToCompleted);
    }

    public BuildingsType getType() {
        return type;
    }

    public int getCost() {
        return type.getCost();
    }

    public int getMaintenance() {
        return type.getMaintenance();
    }

    @Override
    public String name() {
        return type.name();
    }

    @Override
    public RemainedTurns getTurnRemainedToComplete() {
        return turnRemainedToCompleted;
    }
}
