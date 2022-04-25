package model;
import java.util.ArrayList;
import java.util.HashMap;
import Enum.GroundType;

public class Ground {
    public static ArrayList <Ground> allGround = new ArrayList<>();
    public static HashMap<Integer,Ground> pixelInWhichGround = new HashMap<>();
    public ArrayList <Pair> pixelsOfThisGround = new ArrayList<>();

    public Player owner;
    private final int xLocation;
    private final int yLocation;
    public int number;
    public GroundType groundType;

    public Ground(int x, int y,int number){
        this.xLocation = x;
        this.yLocation = y;
        allGround.add(this);
        this.number = number;
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public static Ground getGroundByXY(int x,int y){
        for (Ground ground : allGround) {
            if (ground.xLocation == x && ground.yLocation == y) return ground;
        }
        return null;
    }

    public static int PairToInt(int x, int y){
        GlobalVariables globalVariables = new GlobalVariables();
        return (x - 1) * globalVariables.surfaceWidth + y;
    }

    public boolean checkIsGroundInPage() {
        GlobalVariables globalVariables = new GlobalVariables();
        return this.getxLocation() != 0 && this.getxLocation() != globalVariables.surfaceHeight - 1 &&
                this.getyLocation() != 0 && this.getyLocation() != globalVariables.surfaceWidth - 1;
    }

    public static Ground getGroundByNumber(int number){
        for (Ground ground : allGround) {
            if (ground.number == number) return ground;
        }

        return null;
    }
    public boolean isFreeOfUnit(){
        //TODO: vaghti unit ha do no shodan avaz she
        for (int i = 0; i < Player.allPlayers.size(); i++){
            for (int j = 0; j < Player.allPlayers.get(i).units.size(); j++){
                if (Player.allPlayers.get(i).units.get(j).ground.number == this.number) return false;
            }
        }
        return true;
    }
    public ArrayList<Unit> unitsInThisGround(){
        ArrayList <Unit> answer=new ArrayList<>();
        for (int i=0;i<Player.allPlayers.size();i++){
            for (int j=0;j<Player.allPlayers.get(i).units.size();j++){
                if (Player.allPlayers.get(i).units.get(j).ground.number==this.number){
                    answer.add(Player.allPlayers.get(i).units.get(j));
                }
            }
        }
        return answer;
    }
    public ArrayList<Unit> unitsOfASpecificPlayerInThisGround(Player player){
        ArrayList <Unit> answer=new ArrayList<>();

        for (int i=0;i<player.units.size();i++){
            if (player.units.get(i).ground.number==this.number){
                answer.add(player.units.get(i));
            }
        }

        return answer;
    }
}
