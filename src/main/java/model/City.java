package model;

import java.util.ArrayList;

public class City {
    public ArrayList<Ground> groundsInCity;
    public Player owner;



    public void addGround(Ground ground){
        // check
        for (int i=0;i<this.groundsInCity.size();i++){
            if (this.groundsInCity.get(i).equals(ground)) return ;
        }
        this.groundsInCity.add(ground);

    }
}
