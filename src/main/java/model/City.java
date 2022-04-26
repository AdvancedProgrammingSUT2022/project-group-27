package model;

import java.util.ArrayList;

public class City {
    private ArrayList<Ground> groundsInCity;
    private Player owner;


    public void addGround(Ground ground) {
        for (Ground value : this.groundsInCity) {
            if (value.equals(ground)) return;
        }
        this.groundsInCity.add(ground);

    }
}
