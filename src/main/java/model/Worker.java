package model;


import Enum.MilitaryType;

public class Worker extends UnMilitaryUnit{
    public Worker(Ground ground, Player player, MilitaryType militaryUnit) {
        super(ground, player, militaryUnit);
    }
    private boolean isWorking;
    public boolean getIsWorking(){
        return this.isWorking;
    }
    public void setWorking(boolean working) {
        this.isWorking = working;
    }

}
