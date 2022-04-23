package model;

public abstract class Unit  {
    protected Ground ground;
    protected Player player;

    protected Ground destination=null;
    protected double mp;
    public void setDestination(Ground ground){
        this.destination=ground;
    }
    public void addMp(double amount){this.mp+=amount;}
    public void decreaseMp(double amount){this.mp-=amount;}
    //TODO : vaghti dota shodan dota tabe niyaze
    public void moveUnitToAdjacentGround(Ground ground){
        this.ground=ground;
        decreaseMp(1);
        //TODO : amount dorost shavad
    }
    public  Ground getGround() {
        return this.ground;
    }

}
