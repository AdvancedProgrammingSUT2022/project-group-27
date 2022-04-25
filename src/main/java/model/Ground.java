package model;
import java.util.ArrayList;
import java.util.HashMap;
import Enum.GroundType;

import static java.lang.Math.abs;

public class Ground {
    private static ArrayList <Ground> allGround = new ArrayList<>();
    private static HashMap<Integer,Ground> pixelInWhichGround = new HashMap<>();
    private ArrayList <Pair> pixelsOfThisGround = new ArrayList<>();

    private Player owner;
    private final int xLocation;
    private final int yLocation;
    private int number;
    private GroundType groundType;

    public Ground(int x, int y,int number){
        this.xLocation = x;
        this.yLocation = y;
        this.number = number;
        allGround.add(this);
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

    public int getNumber() {
        return number;
    }

    public Player getOwner() {
        return owner;
    }

    public static ArrayList<Ground> getAllGround() {
        return allGround;
    }

    public static HashMap<Integer, Ground> getPixelInWhichGround() {
        return pixelInWhichGround;
    }

    public ArrayList<Pair> getPixelsOfThisGround() {
        return pixelsOfThisGround;
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public static void setAllGround(ArrayList<Ground> allGround) {
        Ground.allGround = allGround;
    }

    public static void setPixelInWhichGround(HashMap<Integer, Ground> pixelInWhichGround) {
        Ground.pixelInWhichGround = pixelInWhichGround;
    }

    public void setPixelsOfThisGround(ArrayList<Pair> pixelsOfThisGround) {
        this.pixelsOfThisGround = pixelsOfThisGround;
    }

    public void setGroundType(GroundType groundType) {
        this.groundType = groundType;
    }

    public Ground copyOfCurrentGround() {
        Ground newGround = new Ground(this.xLocation, this.yLocation, this.number);
        newGround.setOwner(this.owner);
        newGround.setGroundType(this.groundType);
        newGround.setPixelsOfThisGround(this.pixelsOfThisGround);
        return newGround;
    }

    public static Ground getGroundByXY(int x, int y){
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
    public boolean isFreeOfMilitaryUnit(){
        for (int i = 0; i < Player.getAllPlayers().size(); i++){
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++){
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number && Player.getAllPlayers().get(i).getUnits().get(j) instanceof MilitaryUnit) return false;
            }
        }
        return true;
    }
    public boolean isFreeOfUnMilitaryUnit(){
        //TODO: vaghti unit ha do no shodan avaz she
        for (int i = 0; i < Player.getAllPlayers().size(); i++){
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++){
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number && Player.getAllPlayers().get(i).getUnits().get(j) instanceof UnMilitaryUnit) return false;
            }
        }
        return true;
    }
    public ArrayList<Unit> unitsInThisGround(){
        ArrayList <Unit> answer=new ArrayList<>();
        for (int i = 0; i < Player.getAllPlayers().size(); i++){
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++){
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number){
                    answer.add(Player.getAllPlayers().get(i).getUnits().get(j));
                }
            }
        }
        return answer;
    }
    public ArrayList<Unit> unitsOfASpecificPlayerInThisGround(Player player){
        ArrayList <Unit> answer=new ArrayList<>();

        for (int i=0;i<player.getUnits().size();i++){
            if (player.getUnits().get(i).ground.number==this.number){
                answer.add(player.getUnits().get(i));
            }
        }

        return answer;
    }
    public static boolean AreTheseTwoGroundAdjacent(Ground firstGround,Ground secondGround){
        GlobalVariables globalVariables= new GlobalVariables();
        if (abs(firstGround.getxLocation()-secondGround.getxLocation())<=globalVariables.tool6Zelie
                && abs(firstGround.getyLocation()-secondGround.getyLocation())<=globalVariables.arz6Zelie) return true;
        return false;
    }
    public static int distanceOfTheseTwoGround(Ground firstGround,Ground secondGround){
        int inf = 10000;
        if (!AreTheseTwoGroundAdjacent(firstGround,secondGround)) return inf;
        if (secondGround.groundType==GroundType.OCEAN || secondGround.groundType==GroundType.MOUNTAIN){
            return inf;
        }
        int amount = 0;
        for (int i = 0 ;i<River.getAllRivers().size();i++){
            if (River.getAllRivers().get(i).getFirstGround().number==firstGround.number && River.getAllRivers().get(i).getSecondGround().number==secondGround.number){
                amount=1;
            }
            if (River.getAllRivers().get(i).getFirstGround().number==secondGround.number && River.getAllRivers().get(i).getSecondGround().number==firstGround.number){
                amount=1;
            }
        }
        amount *= 9;
        amount +=secondGround.groundType.getMovementCost();
        return amount;

    }
}
