package model;

import static java.lang.Math.min;

public abstract class Unit  {
    protected Ground ground;
    protected Player player;

    protected Ground destination=null;
    protected double mp=10;

    public double getMp() {
        return mp;
    }

    public void setDestination(Ground ground){
        this.destination=ground;
    }
    public void putMp(double amount){this.mp=amount;}
    public void decreaseMp(double amount){this.mp-=amount;}
    //TODO : vaghti dota shodan dota tabe niyaze
    public void moveUnitToAdjacentGround(Ground ground){
        decreaseMp(Ground.distanceOfTheseTwoGround(this.ground,ground));
        this.ground=ground;
        //TODO : amount dorost shavad
    }
    public  Ground getGround() {
        return this.ground;
    }
    public void checkDestination(){
        if (destination==null) return ;
        int[] dis = new int[GlobalVariables.numberOfTiles+1];
        int[] par = new int[GlobalVariables.numberOfTiles+1];
        int[] vis = new int[GlobalVariables.numberOfTiles+1];
        for (int i=1;i<GlobalVariables.numberOfTiles+1;i++){
            dis[i]=10000;
            par[i]=0;
            vis[i]=0;
        }
        dis[this.destination.getNumber()]=0;

        for (int i=1;i<=GlobalVariables.numberOfTiles;i++){
            int best=1000;
            int id=-1;
            for (int j=1;j<=GlobalVariables.numberOfTiles;j++){
                if (vis[j]==1) continue;
                if (dis[j]<best){
                    best=dis[j];
                    id=j;
                }
            }
            if (id==-1) break;
            for (int j=1;j<=GlobalVariables.numberOfTiles;j++){
                if (dis[j]>dis[id]+Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(id),Ground.getGroundByNumber(j))){
                    par[j]=id;
                }
                dis[j]=min(dis[j],dis[id]+Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(id),Ground.getGroundByNumber(j)));
            }
            vis[id]=1;
        }
        System.out.println(this.destination.getNumber() + " " + this.ground.getNumber() + " " + dis[this.ground.getNumber()] + " " + par[this.ground.getNumber()]+ " " + this.mp);
        if (dis[this.getGround().getNumber()] >= 1000) return ;
        while(this.mp > 0 && this.ground.getNumber() != this.destination.getNumber()){
            int father = par[this.getGround().getNumber()];
            System.out.println("father : " + father);
            this.decreaseMp(Ground.distanceOfTheseTwoGround(this.getGround(),Ground.getGroundByNumber(father)));
            this.ground=Ground.getGroundByNumber(father);
            System.out.println("mp : " +this.getMp());
            GlobalVariables.gameView.showMap(player);
            System.out.printf("MP : "+ this.getMp());
        }
        System.out.println(this.ground.getNumber());
        if (this.getGround().getNumber() == this.destination.getNumber()) this.destination = null;

    }

}
