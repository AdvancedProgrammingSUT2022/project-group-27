package model;

import java.util.ArrayList;

public class City {
    private Ground ground;
    private ArrayList<Ground> rangeOfCity=new ArrayList<>();
    public City(Ground ground){
        this.ground=ground;
        this.rangeOfCity.add(ground);
    }

    public Ground getGround() {
        return ground;
    }

    public ArrayList<Ground> getRangeOfCity() {
        return rangeOfCity;
    }
    public void addGroundToRangeOfCity(Ground ground){
        this.rangeOfCity.add(ground);
    }
    public boolean isThisGroundInThisCityRange(Ground ground){
        for (int i=0;i<rangeOfCity.size();i++){
            if (ground.getNumber()==rangeOfCity.get(i).getNumber()) return true;
        }
        return false;
    }
}
