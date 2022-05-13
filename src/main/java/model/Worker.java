package model;


import Enum.MilitaryType;

public class Worker extends UnMilitaryUnit{
    public Worker(Ground ground, Player player) {
        super(ground, player, MilitaryType.WORKER);
    }

    private boolean isWorking = false;
    public boolean getIsWorking(){
        return this.isWorking;
    }
    public void setWorking(boolean working) {
        this.isWorking = working;
    }

}
