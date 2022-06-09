package model;

import Enum.BuildingsType;

public class Building implements Productions{
    private BuildingsType type;
    private City city;

    public Building(BuildingsType type, City city) {
        this.type = type;
        this.city = city;
    }

    public int getCost() {
        return type.getCost();
    }

    @Override
    public String name() {
        return type.name();
    }
}
