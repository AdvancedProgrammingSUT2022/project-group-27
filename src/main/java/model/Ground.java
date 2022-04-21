package model;
import java.util.ArrayList;
import java.util.HashMap;

public class Ground {
    public static ArrayList <Ground> allGround=new ArrayList<>();
    public static HashMap<Integer,Ground> pixelInWhichGround=new HashMap<>();
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

    public static int PairToInt(int x, int y){
        GlobalVariables globalVariables=new GlobalVariables();
        return (x-1)*globalVariables.surfaceWidth+y;
    }

    public ArrayList <Pair> pixelsOfThisGround = new ArrayList<>();

    public boolean checkIsGroundInPage() {
        GlobalVariables globalVariables = new GlobalVariables();
        if (this.getxLocation() == 0 || this.getxLocation() == globalVariables.surfaceHeight - 1 ||
                this.getyLocation() == 0 || this.getyLocation() == globalVariables.surfaceWidth - 1) {
            return false;
        }
        return true;
    }
    public static Ground getGroundByNumber(int number){
        for (int i=0;i<allGround.size();i++){
            if (allGround.get(i).number==number) return allGround.get(i);
        }
        return null;
    }
    public boolean isFreeOfUnit(){
        //TODO: vaghti unit ha do no shodan avaz she
        for (int i=0;i<Player.allPlayers.size();i++){
            for (int j=0;j<Player.allPlayers.get(i).units.size();j++){
                if (Player.allPlayers.get(i).units.get(j).ground.number==this.number) return false;
            }
        }
        return true;
    }

}
