package model;

import java.util.ArrayList;
import java.util.HashMap;

import Enum.GroundType;

import static java.lang.Math.abs;

import Enum.FeatureType;

import Enum.BonusResource;
import Enum.StrategicResource;
import Enum.ImprovementType;
import Enum.TechnologyType;

import javax.swing.plaf.basic.BasicRootPaneUI;

public class Ground {
    private static ArrayList<Ground> allGround = new ArrayList<>();
    private static HashMap<Integer, Ground> pixelInWhichGround = new HashMap<>();
    private ImprovementType improvementType=null;
    private ImprovementType improvementTypeInProgress=null;
    private ArrayList<Pair> pixelsOfThisGround = new ArrayList<>();
    private ArrayList<Ground> adjacentGrounds = new ArrayList<>();
    private Player owner;
    private final int xLocation;
    private final int yLocation;
    private int number;
    private int cost; //TODO initialize it at first of game
    private boolean isWorkedOn = false;
    private GroundType groundType;
    private FeatureType featureType;
    private ArrayList<BonusResource> bonusResource=new ArrayList<>();
    private ArrayList<StrategicResource> strategicResources = new ArrayList<>();

    public ArrayList<Ground> getAdjacentGrounds() {
        return adjacentGrounds;
    }
    public void addGroundToAdjacentGround(Ground ground){
        this.adjacentGrounds.add(ground);
    }

    public Ground(int x, int y, int number) {
        this.xLocation = x;
        this.yLocation = y;
        this.number = number;
        allGround.add(this);
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    public ArrayList<BonusResource> getBonusResource() {
        return bonusResource;
    }

    public void addBonusResource(BonusResource bonusResource) {
        this.bonusResource.add(bonusResource);
    }

    public ArrayList<StrategicResource> getStrategicResources() {
        return strategicResources;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
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

    public int getCost() {
        return cost;
    }

    public boolean isWorkedOn() {
        return isWorkedOn;
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

    public void setWorkedOn() {
        isWorkedOn = true;
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

    public static Ground getGroundByXY(int x, int y) {
        for (Ground ground : allGround) {
            if (ground.xLocation == x && ground.yLocation == y) return ground;
        }
        return null;
    }

    public static int PairToInt(int x, int y) {
        GlobalVariables globalVariables = new GlobalVariables();
        return (x - 1) * globalVariables.surfaceWidth + y;
    }

    public boolean checkIsGroundInPage() {
        GlobalVariables globalVariables = new GlobalVariables();
        return this.getxLocation() != 0 && this.getxLocation() != globalVariables.surfaceHeight - 1 &&
                this.getyLocation() != 0 && this.getyLocation() != globalVariables.surfaceWidth - 1;
    }

    public static Ground getGroundByNumber(int number) {
        for (Ground ground : allGround) {
            if (ground.number == number) return ground;
        }

        return null;
    }

    public boolean isFreeOfMilitaryUnit() {
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++) {
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number
                        && Player.getAllPlayers().get(i).getUnits().get(j) instanceof MilitaryUnit)
                    return false;
            }
        }
        return true;
    }

    public boolean isFreeOfUnMilitaryUnit() {
        //TODO: vaghti unit ha do no shodan avaz she
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++) {
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number
                        && Player.getAllPlayers().get(i).getUnits().get(j) instanceof UnMilitaryUnit)
                    return false;
            }
        }
        return true;
    }

    public ArrayList<Unit> unitsInThisGround() {
        ArrayList<Unit> answer = new ArrayList<>();
        for (int i = 0; i < Player.getAllPlayers().size(); i++) {
            for (int j = 0; j < Player.getAllPlayers().get(i).getUnits().size(); j++) {
                if (Player.getAllPlayers().get(i).getUnits().get(j).ground.number == this.number) {
                    answer.add(Player.getAllPlayers().get(i).getUnits().get(j));
                }
            }
        }
        return answer;
    }

    public ArrayList<Unit> unitsOfASpecificPlayerInThisGround(Player player) {
        ArrayList<Unit> answer = new ArrayList<>();

        for (int i = 0; i < player.getUnits().size(); i++) {
            if (player.getUnits().get(i).ground.number == this.number) {
                answer.add(player.getUnits().get(i));
            }
        }

        return answer;
    }

    public static boolean AreTheseTwoGroundAdjacent(Ground firstGround, Ground secondGround) {
        GlobalVariables globalVariables = new GlobalVariables();
        return abs(firstGround.getxLocation() - secondGround.getxLocation()) <= globalVariables.tool6Zelie
                && abs(firstGround.getyLocation() - secondGround.getyLocation()) <= globalVariables.arz6Zelie;
    }

    public static int distanceOfTheseTwoGround(Ground firstGround, Ground secondGround) {
        int inf = 10000;
        if (!AreTheseTwoGroundAdjacent(firstGround, secondGround)) return inf;
        if (secondGround.groundType == GroundType.OCEAN || secondGround.groundType == GroundType.MOUNTAIN) {
            return inf;
        }
        int amount = 0;
        for (int i = 0; i < River.getAllRivers().size(); i++) {
            if (River.getAllRivers().get(i).getFirstGround().number == firstGround.number
                    && River.getAllRivers().get(i).getSecondGround().number == secondGround.number) {
                amount = 1;
            }
            if (River.getAllRivers().get(i).getFirstGround().number == secondGround.number
                    && River.getAllRivers().get(i).getSecondGround().number == firstGround.number) {
                amount = 1;
            }
        }
        amount *= 9;
        amount += secondGround.groundType.getMovementCost() + secondGround.getFeatureType().getMovementCost();
        return amount;

    }

    public ImprovementType getImprovementTypeInProgress() {
        return improvementTypeInProgress;
    }

    public boolean containRiver() {
        for (int i = 0; i < River.getAllRivers().size(); i++) {
            if (River.getAllRivers().get(i).getFirstGround().number == this.number) return true;
            if (River.getAllRivers().get(i).getSecondGround().number == this.number) return true;
        }
        return false;
    }

    public boolean isInRangeOfCity(){
        for (int i=0;i<Player.getAllPlayers().size();i++){
            for (int j=0;j<Player.getAllPlayers().get(i).getCities().size();j++){
                for (int k=0;k<Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().size();k++){
                    if (this.number==Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().get(k).number){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean canWeAddThisBonusResourceToThisGround(BonusResource bonusResource){
        for (int i=0;i<bonusResource.getGroundTypes().size();i++){
            if (bonusResource.getGroundTypes().get(i)==this.groundType) return false;
        }
        for (int i=0;i<bonusResource.getGroundFeatureTypes().size();i++){
            if (bonusResource.getGroundFeatureTypes().get(i)==this.featureType) return true;
        }
        return false;
    }

    public MilitaryUnit getMilitaryUnit() {
        ArrayList<Unit> units = this.unitsInThisGround();
        for (Unit unit : units) {
            if (unit instanceof MilitaryUnit) {
                return (MilitaryUnit) unit;
            }
        }
        return null;
    }

    public UnMilitaryUnit getUnMilitaryUnit() {
        ArrayList<Unit> units = this.unitsInThisGround();
        for (Unit unit : units) {
            if (unit instanceof MilitaryUnit) {
                return (UnMilitaryUnit) unit;
            }
        }
        return null;
    }
    public boolean canWeAddThisImprovement(ImprovementType improvementType){
        Player player=Player.whichPlayerTurnIs();
        if (!player.doWeHaveThisTechnology(improvementType.getTechnologyTypes())) return false;
        for (int i=0;i<improvementType.getGroundTypes().size();i++){
            if (this.groundType==improvementType.getGroundTypes().get(i)) return true;
        }
        for (int i=0;i<improvementType.getGroundFeatureTypes().size();i++){
            if (this.getFeatureType()==improvementType.getGroundFeatureTypes().get(i)) return true;
        }
        return false;
    }
    public ArrayList<ImprovementType> listOfImprovementTypes(){
        ArrayList<ImprovementType> answer=new ArrayList<>();
        for (ImprovementType improvementType1 : ImprovementType.values()){
            if (canWeAddThisImprovement(improvementType1)) answer.add(improvementType1);
        }
        return answer;
    }
    public void setImprovementTypeInProgress(ImprovementType improvementTypeInProgress){
        Player player=Player.whichPlayerTurnIs();
        this.improvementTypeInProgress=improvementTypeInProgress;
        this.improvementTypeInProgress.setTurn(6);
        if (improvementTypeInProgress==ImprovementType.FARM){
            if (this.featureType==FeatureType.FOREST){
                if (player.doWeHaveThisTechnology(TechnologyType.MINING)){
                    this.improvementTypeInProgress.setTurn(10);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.JUNGLE){
                if (player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)){
                    this.improvementTypeInProgress.setTurn(13);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.MARSH){
                if (player.doWeHaveThisTechnology(TechnologyType.MASONRY)){
                    this.improvementTypeInProgress.setTurn(12);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
        }
        if (improvementTypeInProgress==ImprovementType.MINE){
            if (this.featureType==FeatureType.FOREST){
                this.improvementTypeInProgress.setTurn(10);
            }
            if (this.featureType==FeatureType.JUNGLE){
                if (player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)){
                    this.improvementTypeInProgress.setTurn(13);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.MARSH){
                if (player.doWeHaveThisTechnology(TechnologyType.MASONRY)){
                    this.improvementTypeInProgress.setTurn(12);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
        }
        if (this.improvementTypeInProgress==null){
            System.out.println("nemishe");
            //ToDO : bere to controller
        }
    }
    public void putImprovementTypeInThisGround(){
        this.improvementType=improvementTypeInProgress;
        this.featureType=FeatureType.NOTHING;
    }

}
