package model;

import Enum.*;
import controller.ImprovementSettingController;
import controller.UnitController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Ground {
    private int counterOfDestroyingFeature=0;
    private Road road=null;
    private RailWay railWay=null;
    private static ArrayList<Ground> allGround = new ArrayList<>();
    private static HashMap<Integer, Ground> pixelInWhichGround = new HashMap<>();
    private ImprovementType improvementType=null;
    private Improvement plunderingImprovementType = null;
    private Improvement improvementTypeInProgress=null;
    private ArrayList<Pair> pixelsOfThisGround = new ArrayList<>();
    //private transient ArrayList<Ground> adjacentGrounds = new ArrayList<>();
    private ArrayList<Integer> adjacentGrounds = new ArrayList<>();
    private int xLocation;
    private int yLocation;
    private int number;
    private int cost = 50;
    private boolean isWorkedOn = false;
    private GroundType groundType;
    private FeatureType featureType;
    private ArrayList<BonusResource> bonusResource = new ArrayList<>();
    private ArrayList<StrategicResource> strategicResources = new ArrayList<>();
    private ArrayList<LuxuryResource> luxuryResources = new ArrayList<>();
    private boolean hasRuin = false;

    //TODO: luxuryResources to be completed

    public ArrayList<Ground> getAdjacentGrounds() {
        ArrayList<Ground> adjacentGroundsList = new ArrayList<>();
        for (Integer integer: adjacentGrounds) {
            adjacentGroundsList.add(Ground.getGroundByNumber(integer));
        }
        return adjacentGroundsList;
    }

    public void addGroundToAdjacentGround(Ground ground){
        this.adjacentGrounds.add(ground.getNumber());
    }

    public synchronized static void add(Ground ground) {
        if (allGround.contains(ground)) return;
        allGround.add(ground);
    }

    public Ground(int x, int y, int number) {
        this.xLocation = x;
        this.yLocation = y;
        this.number = number;
        this.cost = 50; //TODO if it become random make it more fun
        Ground.add(this);
    }
    public void setxLocation(int x){
        this.xLocation=x;
    }
    public void setyLocation(int y){
        this.yLocation=y;
    }
    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    public void setPlunderingImprovementType(ImprovementType plunderingImprovementType) {
        this.plunderingImprovementType = new Improvement(improvementType,1000000);
    }

    public Improvement getPlunderingImprovementType() {
        return plunderingImprovementType;
    }

    public ArrayList<BonusResource> getBonusResource() {
        return bonusResource;
    }

    public void addBonusResource(BonusResource bonusResource) {
        this.bonusResource.add(bonusResource);
    }
    public void addLuxuryResource(LuxuryResource luxuryResource) {
        this.luxuryResources.add(luxuryResource);
    }
    public void addStrategicResource(StrategicResource strategicResource) {
        this.strategicResources.add(strategicResource);
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



    public static ArrayList<Ground> getAllGround() {
        return allGround;
    }

    public static HashMap<Integer, Ground> getPixelInWhichGround() {
        return pixelInWhichGround;
    }

    public ArrayList<Pair> getPixelsOfThisGround() {
        return pixelsOfThisGround;
    }

    public void setBonusResource(ArrayList<BonusResource> bonusResource) {
        this.bonusResource = bonusResource;
    }

    public void setStrategicResources(ArrayList<StrategicResource> strategicResources) {
        this.strategicResources = strategicResources;
    }

    public ArrayList<LuxuryResource> getLuxuryResources() {
        return luxuryResources;
    }

    public void setLuxuryResources(ArrayList<LuxuryResource> luxuryResources) {
        this.luxuryResources = luxuryResources;
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public void setNumber(int number) {
        this.number = number;
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
        return (abs(firstGround.getxLocation() - secondGround.getxLocation()) <= GlobalVariables.tool*2+16
                && abs(firstGround.getyLocation() - secondGround.getyLocation()) <= GlobalVariables.arz*2+8);
    }

    public static int distanceOfTheseTwoGround(Ground firstGround, Ground secondGround,Player player,Unit unitType) {
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
        amount *= 10;
        amount += secondGround.groundType.getMovementCost() + secondGround.getFeatureType().getMovementCost();
        if ((secondGround.getRoad()!=null && secondGround.getRoad().getTurn()<0) || (secondGround.getRailWay()!=null &&  secondGround.getRoad().getTurn()<0)){
            amount=1;
        }
        for (Player secondPlayer : Player.getAllPlayers()){
            if (player.equals(secondPlayer)) continue;
            for (Unit unit : secondPlayer.getUnits()){
                if (unit instanceof UnMilitaryUnit) continue;
                if (AreTheseTwoGroundAdjacent(secondGround,unit.getGround())) amount=10;
            }
        }
        amount=min(10,amount);
        if (unitType.getMilitaryType()== MilitaryType.SCOUT) amount=1;
        return amount;

    }

    public Improvement getImprovementTypeInProgress() {
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
        for (int i = 0; i < Player.getAllPlayers().size(); i++){
            for (int j = 0; j < Player.getAllPlayers().get(i).getCities().size(); j++){
                for (int k = 0; k < Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().size(); k++){
                    if (this.number == Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().get(k).number){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Player ownerOfThisGround(){
        for (int i = 0; i < Player.getAllPlayers().size(); i++){
            for (int j = 0; j < Player.getAllPlayers().get(i).getCities().size(); j++){
                for (int k = 0; k < Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().size(); k++){
                    if (this.number == Player.getAllPlayers().get(i).getCities().get(j).getRangeOfCity().get(k).number){
                        return Player.getAllPlayers().get(i);
                    }
                }
            }
        }
        return null;
    }
    public boolean canWeAddThisBonusResourceToThisGround(BonusResource bonusResource){
        for (int i=0;i<bonusResource.getGroundTypes().size();i++){
            if (bonusResource.getGroundTypes().get(i)==this.groundType) return true;
        }
        for (int i=0;i<bonusResource.getGroundFeatureTypes().size();i++){
            if (bonusResource.getGroundFeatureTypes().get(i)==this.featureType) return true;
        }
        return false;
    }
    public boolean canWeAddThisLuxuryResourceToThisGround(LuxuryResource luxuryResource){
        for (int i=0;i<luxuryResource.getGroundTypes().size();i++){
            if (luxuryResource.getGroundTypes().get(i)==this.groundType) return true;
        }
        for (int i=0;i<luxuryResource.getGroundFeatureTypes().size();i++){
            if (luxuryResource.getGroundFeatureTypes().get(i)==this.featureType) return true;
        }
        return false;
    }
    public boolean canWeAddThisStrategicResourceToThisGround(StrategicResource strategicResource){
        for (int i=0;i<strategicResource.getGroundTypes().size();i++){
            if (strategicResource.getGroundTypes().get(i)==this.groundType) return true;
        }
        return false;
    }
    public boolean canWeSeeThisStrategicResource(){
        Player player=this.ownerOfThisGround();
        if (player==null || this.getStrategicResources().size()==0) return false;
        if (player.doWeHaveThisTechnology(this.getStrategicResources().get(0).getTechnology())) return true;
        return false;
    }
    public boolean canWeUseThisBonusResource(){
        if (this.bonusResource.size()!=0 && this.bonusResource.get(0)!=null &&   this.bonusResource.get(0).getImprovementType()==this.improvementType) return true;
        return false;
    }
    public boolean canWeUseThisStrategicResource(){
        if (this.strategicResources.size()!=0 && this.strategicResources.get(0)!=null && this.strategicResources.get(0).getImprovementType()==this.improvementType) return true;
        return false;
    }
    public boolean canWeUseThisLuxuryResource(){
        if (this.luxuryResources.size()!=0 && this.luxuryResources.get(0)!=null && this.luxuryResources.get(0).getImprovementType()==this.improvementType) return true;
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
            if (unit instanceof UnMilitaryUnit) {
                return (UnMilitaryUnit) unit;
            }
        }
        return null;
    }



    public ArrayList<ImprovementType> listOfImprovementTypes(){
        ArrayList<ImprovementType> answer=new ArrayList<>();
        for (ImprovementType improvementType1 : ImprovementType.values()){
            if (ImprovementSettingController.canWeAddThisImprovement(improvementType1, this)) answer.add(improvementType1);
        }

        return answer;
    }

    public void setImprovementTypeInProgress(ImprovementType improvementTypeInProgress){
        Player player=Player.whichPlayerTurnIs();
        this.improvementTypeInProgress=new Improvement(improvementTypeInProgress,6);
        System.out.println("test1 : "+this.improvementTypeInProgress.getImprovementType());
        if (improvementTypeInProgress==ImprovementType.FARM){
            if (this.featureType==FeatureType.FOREST){
                if (player.doWeHaveThisTechnology(TechnologyType.MINING)){
                    this.improvementTypeInProgress.setTurnRemained(10);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.JUNGLE){
                if (player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)){
                    this.improvementTypeInProgress.setTurnRemained(13);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.MARSH){
                if (player.doWeHaveThisTechnology(TechnologyType.MASONRY)){
                    this.improvementTypeInProgress.setTurnRemained(12);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
        }
        if (improvementTypeInProgress==ImprovementType.MINE){
            if (this.featureType==FeatureType.FOREST){
                this.improvementTypeInProgress.setTurnRemained(10);
            }
            if (this.featureType==FeatureType.JUNGLE){
                if (player.doWeHaveThisTechnology(TechnologyType.BRONZE_WORKING)){
                    this.improvementTypeInProgress.setTurnRemained(13);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
            if (this.featureType==FeatureType.MARSH){
                if (player.doWeHaveThisTechnology(TechnologyType.MASONRY)){
                    this.improvementTypeInProgress.setTurnRemained(12);
                }
                else{
                    this.improvementTypeInProgress=null;
                }
            }
        }
        if (this.improvementTypeInProgress.getImprovementType()==null){
            System.out.println("nemishe");
            //ToDO : bere to controller
        }
    }

    public void putImprovementTypeInThisGround(){
        this.improvementType=improvementTypeInProgress.getImprovementType();
        System.out.println(this.improvementType);
        this.featureType=FeatureType.NOTHING;
    }
    public void buildRoad(){
        road= new Road();
    }
    public void buildRailWay(){
        railWay = new RailWay();
    }
    public void deleteRoadAndRailway(){
        this.road=null;
        this.railWay=null;
    }
    public RailWay getRailWay() {
        return railWay;
    }

    public Road getRoad() {
        return road;
    }

    public int getCounterOfDestroyingFeature() {
        return counterOfDestroyingFeature;
    }

    public void setCounterOfDestroyingFeature(int counterOfDestroyingFeature) {
        this.counterOfDestroyingFeature = counterOfDestroyingFeature;
    }
    public void increaseCounterOfDestroyingFeature(int amount){
        this.counterOfDestroyingFeature+=amount;
    }

    public int getDistance(Ground ground) {
        ArrayList<Integer> isReached = new ArrayList<>();
        for (int i = 0; i < GlobalVariables.numberOfTiles; i++) {
            isReached.add(0);
        }
        isReached.set(this.number - 1, 1);
        if (this.equals(ground))
            return 0;
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            for (int j = 1; j <= GlobalVariables.numberOfTiles; j++) {
                for (int k = 1; k <= GlobalVariables.numberOfTiles; k++) {
                    if (isReached.get(j - 1) == i && isReached.get(k - 1) == 0 && AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(j), Ground.getGroundByNumber(k))) {
                        isReached.set(k - 1, i + 1);
                    }
                }
            }
            if (isReached.get(ground.number - 1) != 0)
                return i;
        }
        return GlobalVariables.numberOfTiles;
    }

    public int getDistanceWithoutBlocks(Ground ground) {
        ArrayList<Integer> isReached = new ArrayList<>();
        for (int i = 0; i < GlobalVariables.numberOfTiles; i++) {
            isReached.add(0);
        }
        isReached.set(this.number - 1, 1);
        if (this.equals(ground))
            return 0;
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            for (int j = 1; j <= GlobalVariables.numberOfTiles; j++) {
                for (int k = 1; k <= GlobalVariables.numberOfTiles; k++) {
                    if (isReached.get(j - 1) == i && isReached.get(k - 1) == 0 && AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(j), Ground.getGroundByNumber(k)) && (this.number == j || !Ground.getGroundByNumber(j).getGroundType().isBlock())) {
                        isReached.set(k - 1, i + 1);
                    }
                }
            }
            if (isReached.get(ground.number - 1) != 0)
                return i;
        }
        return GlobalVariables.numberOfTiles;
    }

    public void deleteAdjacentGround() {
        this.adjacentGrounds.clear();
    }


    public boolean getHasRuin() {
        return hasRuin;
    }

    public void setHasRuin(boolean hasRuin) {
        this.hasRuin = hasRuin;
    }

    public String implementRuin(Player player) {
        Random random = new Random();
        int randomNumber;
        if (this.isFreeOfUnMilitaryUnit()) {
            randomNumber = random.nextInt() % 5;
        }
        else {
            randomNumber = random.nextInt() % 4;
        }
        //System.out.println("74444444444444444444444444444444444444444444444444444444444444444 " + randomNumber);
        String whatHappen = "nothing";
        switch (randomNumber) {
            case 3: {
                player.setGold(player.getGold() + 40);
                whatHappen = "increase gold 40 steps";
                break;
            }
            case 0: {
                for (TechnologyType technologyType : TechnologyType.values()) {
                    if (player.canWeAddThisTechnology(technologyType)) {
                        player.addTechnology(technologyType);
                        break;
                    }
                }
                whatHappen = "add every technology that you have prerequisites";
                break;
            }
            case 1: {
                for (Ground ground : getAdjacentGrounds()) {
                    for (Ground disTwoGround : ground.getAdjacentGrounds()) {
                        player.addGroundToClearGround(ground);
                    }
                }
                whatHappen = "add some grounds in clear to see";
                break;
            }

            case 2: {
                whatHappen = "increase your citizens of one of your random cities";
                if (player.getCities().size() == 0)
                    break;
                City city = player.getCities().get(random.nextInt() % player.getCities().size());
                city.increasingCitizens();
                break;
            }
            case 4:
                if (random.nextInt() % 2 == 0) {
                    UnitController.addUnit(player, this, MilitaryType.SETTLER);
                    whatHappen = "adding one settler in this ground";
                } else {
                    UnitController.addUnit(player, this, MilitaryType.WORKER);
                    whatHappen = "adding one worker in this ground";
                }
        }

        this.hasRuin = false;

        return whatHappen;
    }
}
