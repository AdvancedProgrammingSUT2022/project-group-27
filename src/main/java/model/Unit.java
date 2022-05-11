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
    //protected boolean isSleeping = false;
    //protected boolean hasDoneSomething = false;

    public Unit(Ground ground, Player player, MilitaryType militaryType) {
        this.player = player;
        this.ground = ground;
        this.militaryType = militaryType;
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

    /*public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }

    public boolean HasDoneSomething() {
        return hasDoneSomething;
    }

    public void setHasDoneSomething(boolean hasDoneSomething) {
        this.hasDoneSomething = hasDoneSomething;
    }*/

    public int getMovement() {
        return this.militaryType.getMovement();
    }

    //public int getTurn() {
    //    return this.militaryType.getTurn();
   // }

    public MilitaryType getMilitaryType() {
        return militaryType;
    }

    public void setMilitaryType(MilitaryType militaryType) {
        this.militaryType = militaryType;
    }

    //TODO : vaghti dota shodan dota tabe niyaze
    public void moveUnitToAdjacentGround(Ground ground) {
        decreaseMp(Ground.distanceOfTheseTwoGround(this.ground, ground));
        this.ground = ground;
        //TODO : amount dorost shavad
    }

    public Ground getGround() {
        return this.ground;
    }

    public void setCityGround (Ground ground) {
        this.ground = ground;
    }

    public void checkDestination() {
        if (destination == null) return;
        int[] dis = new int[GlobalVariables.numberOfTiles + 1];
        int[] par = new int[GlobalVariables.numberOfTiles + 1];
        int[] vis = new int[GlobalVariables.numberOfTiles + 1];
        for (int i = 1; i < GlobalVariables.numberOfTiles + 1; i++) {
            dis[i] = 10000;
            par[i] = 0;
            vis[i] = 0;
        }
        dis[this.destination.getNumber()] = 0;

        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++) {
            int best = 1000;
            int id = -1;
            for (int j = 1; j <= GlobalVariables.numberOfTiles; j++) {
                if (vis[j] == 1) continue;
                if (dis[j] < best) {
                    best = dis[j];
                    id = j;
                }
            }
            if (id == -1) break;
            for (int j = 1; j <= GlobalVariables.numberOfTiles; j++) {
                if (dis[j] > dis[id] + Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(id), Ground.getGroundByNumber(j))) {
                    par[j] = id;
                }
                dis[j] = min(dis[j], dis[id] + Ground.distanceOfTheseTwoGround(Ground.getGroundByNumber(id), Ground.getGroundByNumber(j)));
            }
            vis[id] = 1;
        }
        if (dis[this.getGround().getNumber()] >= 1000) return;
        while (this.mp > 0 && this.ground.getNumber() != this.destination.getNumber()) {
            int father = par[this.getGround().getNumber()];
            if (this.getMp() - Ground.distanceOfTheseTwoGround(this.getGround(), Ground.getGroundByNumber(father)) == 0) {
                if (this instanceof MilitaryUnit && !Ground.getGroundByNumber(father).isFreeOfMilitaryUnit())
                    break;
                if (this instanceof UnMilitaryUnit && !Ground.getGroundByNumber(father).isFreeOfUnMilitaryUnit())
                    break;
            }
            System.out.println("father : " + father);
            this.decreaseMp(Ground.distanceOfTheseTwoGround(this.getGround(), Ground.getGroundByNumber(father)));
            this.ground = Ground.getGroundByNumber(father);
            System.out.println("mp : " + this.getMp());
            ShowMap showMap = new ShowMap(player);
            showMap.run();
        }
        System.out.println(this.ground.getNumber());
        if (this.getGround().getNumber() == this.destination.getNumber()) this.destination = null;

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
            player.getUnits().add(unit);
        } else if (unit instanceof UnMilitaryUnit) {
            if (city.getGround().getUnMilitaryUnit() != null) return false;
            player.getUnits().add(unit);
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit type: " + this.militaryType + " status: " + this.status + " ground number: " + this.ground.getNumber();
    }
}
