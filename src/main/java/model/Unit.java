package model;

import view.game.ShowMap;

import static java.lang.Math.min;
import Enum.MilitaryType;
import Enum.UnitStatus;

public abstract class Unit {
    protected MilitaryType militaryType;
    protected Ground ground;
    protected Player player;
    protected Ground destination = null;
    protected double mp = 10;
    protected double hp = 10;
    protected int turnsFortified = 0;
    protected UnitStatus status = UnitStatus.AWAKE;
    protected RemainedTurns turnRemainedToCompleted = new RemainedTurns(0);
    protected int combatStrength;
    protected int rangedCombatStrength;
    //protected boolean isSleeping = false;
    //protected boolean hasDoneSomething = false;

    public Unit(Ground ground, Player player, MilitaryType militaryType) {
        this.player = player;
        this.ground = ground;
        this.militaryType = militaryType;
        combatStrength = militaryType.getCombatStrength();
    }

    public int getRangedCombatStrength() {
        if (this.player.getHappiness() < 0) return rangedCombatStrength * 3 / 4;

        return rangedCombatStrength;
    }

    public int getCombatStrength() {
        if (this.player.getHappiness() < 0) return combatStrength * 3 / 4;

        return combatStrength;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public void setTurnsFortified(int turnsFortified) {
        this.turnsFortified = turnsFortified;
    }

    public int getTurnsFortified() {
        return turnsFortified;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
        System.out.println(status); //we should put it in view.
    }

    public void setTurnRemainedToCompleted(int turnRemainedToCompleted) {
        this.turnRemainedToCompleted.setTurns(turnRemainedToCompleted);
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public void setDestination(Ground ground) {
        this.destination = ground;
        if (ground != null) {
            if (!this.status.equals(UnitStatus.AWAKE)) this.status = UnitStatus.AWAKE;
        }
    }

    public void putMp(double amount) {
        this.mp = amount;
    }

    public void decreaseMp(double amount) {
        this.mp -= amount;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCost() {
        return this.militaryType.getCost();
    }

    public int getMovement() {
        return this.militaryType.getMovement();
    }

    public MilitaryType getMilitaryType() {
        return militaryType;
    }

    public void setMilitaryType(MilitaryType militaryType) {
        this.militaryType = militaryType;
    }

    //TODO : vaghti dota shodan dota tabe niyaze

    public Ground getGround() {
        return this.ground;
    }

    public void setCityGround (Ground ground) {
        this.ground = ground;
    }

    public boolean checkDestination() {
        if (destination == null) return true;
        int[][] distanceFloyd = new int[GlobalVariables.numberOfTiles + 1][GlobalVariables.numberOfTiles + 1];
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            for (int j = i + 1; j <= GlobalVariables.numberOfTiles; j++) {
                distanceFloyd[i][j] = Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(i), Ground.getGroundByNumber(j), player, this);
                distanceFloyd[j][i] = Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(j), Ground.getGroundByNumber(i), player, this);
                ;
            }
        }
        for (int k = 1; k <= GlobalVariables.numberOfTiles; k++) {
            for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
                for (int j = 1; j <= GlobalVariables.numberOfTiles; j++) {
                    distanceFloyd[i][j] = min(distanceFloyd[i][k] + distanceFloyd[k][j], distanceFloyd[i][j]);
                }
            }
        }
        if (distanceFloyd[this.getGround().getNumber()][this.destination.getNumber()] >= 1000) {
            this.destination = null;
            return false;
        }
        System.out.println(distanceFloyd[this.getGround().getNumber()][this.destination.getNumber()]);
        Ground middle = this.getGround().getAdjacentGrounds().get(0);
        if (middle.getNumber()==this.getGround().getNumber()) middle=this.getGround().getAdjacentGrounds().get(1);
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            Ground ground1 = Ground.getGroundByNumber(i);
            boolean isOk = false;
            for (Player opponent : Player.getAllPlayers()) {
                if (player.equals(opponent)) continue;
                for (Unit unit : opponent.getUnits()) {
                    if (unit instanceof MilitaryUnit && unit.getGround().getNumber() == ground1.getNumber())
                        isOk = true;
                    if (unit instanceof UnMilitaryUnit && this instanceof UnMilitaryUnit &&
                            unit.getGround().getNumber() == ground1.getNumber()) isOk = true;
                }
            }
            if (isOk) continue;
            for (Ground adjacentGround : Ground.getGroundByNumber(i).getAdjacentGrounds()) {
                if (distanceFloyd[this.getGround().getNumber()][adjacentGround.getNumber()] < this.mp) {
                    isOk = true;
                }
            }
               if (distanceFloyd[this.getGround().getNumber()][i]<=this.mp) isOk=true;
            if (isOk) {
                Ground adjacentGround = Ground.getGroundByNumber(i);
                if (middle == null || (distanceFloyd[this.getGround().getNumber()][middle.getNumber()] +
                        distanceFloyd[middle.getNumber()][this.destination.getNumber()] >
                        distanceFloyd[this.getGround().getNumber()][adjacentGround.getNumber()] +
                                distanceFloyd[adjacentGround.getNumber()][this.destination.getNumber()]) || (
                        distanceFloyd[middle.getNumber()][this.destination.getNumber()]
                                > distanceFloyd[adjacentGround.getNumber()][this.destination.getNumber()] &&
                                distanceFloyd[this.getGround().getNumber()][middle.getNumber()] +
                                        distanceFloyd[middle.getNumber()][this.destination.getNumber()] ==
                                        distanceFloyd[this.getGround().getNumber()][adjacentGround.getNumber()] +
                                                distanceFloyd[adjacentGround.getNumber()][this.destination.getNumber()]

                )) {
                    middle = Ground.getGroundByNumber(i);
                }
            }
        }
        while (this.getGround().getNumber()!=middle.getNumber()){
            for (Ground betweenGround : this.ground.getAdjacentGrounds()){
                if (betweenGround.getNumber()==this.ground.getNumber()) continue;
                if (distanceFloyd[this.ground.getNumber()][betweenGround.getNumber()]+distanceFloyd[betweenGround.getNumber()][middle.getNumber()]
                        ==distanceFloyd[this.getGround().getNumber()][middle.getNumber()]){
                    this.mp-=Ground.distanceOfTheseTwoGround(this.getGround(),betweenGround,player,this);
                    System.out.println("MP : " + this.mp);
                    this.ground=betweenGround;
                    ShowMap menu=new ShowMap(player);
                    menu.run();
                    break;
                }
            }
        }
        if (this.getGround().getNumber()==this.destination.getNumber()) this.destination=null;



        return true;
    }
    public void removeUnit() {
        this.player.getUnits().remove(this);
    }

    public void changeOwner(Player player) {
        this.getPlayer().getUnits().remove(this);
        player.getUnits().add(this);
    }

    public RemainedTurns getTurnRemainedToComplete() {
        return this.turnRemainedToCompleted;
    }

    public static boolean addingUnitFromArrayOfCityToCity(Player player, City city, Unit unit) {
        if (unit instanceof MilitaryUnit) {
            if (city.getGround().getMilitaryUnit() != null) return false;
            city.getListOfUnitsInCity().remove(unit);
            player.getUnits().add(unit);
        } else if (unit instanceof UnMilitaryUnit) {
            if (city.getGround().getUnMilitaryUnit() != null) return false;
            city.getListOfUnitsInCity().remove(unit);
            player.getUnits().add(unit);
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit type: " + this.militaryType + " status: " + this.status + " ground number: " + this.ground.getNumber();
    }
}