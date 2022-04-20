package model;
import java.util.ArrayList;
import java.util.HashMap;

public class Ground {
    public static ArrayList <Ground> allGround=new ArrayList<>();
    public static HashMap<Integer,Ground> pixelInWitchGround=new HashMap<>();
    public Player owner;
    private int xLocation;

    private int yLocation;
    public int number;
    public Ground(int x, int y,int number){
        this.xLocation=x;
        this.yLocation=y;
        allGround.add(this);
        this.number=number;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }
    public static Ground getGroundByXY(int x,int y){
        for (int i=0;i<allGround.size();i++){
            if (allGround.get(i).xLocation==x && allGround.get(i).yLocation==y) return allGround.get(i);
        }
        return null;
    }
    public static int PairToInt(int x,int y){
        GlobalVariables globalVariables=new GlobalVariables();
        return (x-1)*globalVariables.surfaceWidth+y;
    }

    public ArrayList <Pair> pixelsOfThisGround = new ArrayList<>();


}
