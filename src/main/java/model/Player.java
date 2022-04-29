package model;

import java.util.ArrayList;

public class Player {
    private int gold;
    private int science;
    private int food;
    private static final ArrayList<Player> allPlayers = new ArrayList<>();
    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<Unit> units = new ArrayList<>();
    private ArrayList<Ground> clearToSeeGrounds = new ArrayList<>();
    private ArrayList<Ground> wasClearedToSeeGrounds = new ArrayList<>();
    private static int counterOfNextRound = 0;
    private User user;
    private boolean isAlive = true;

    public Player(User user) {
        this.user = user;
        allPlayers.add(this);
    }

    public int getFood() {
        return food;
    }

    public int getGold() {
        return gold;
    }

    public int getScience() {
        return science;
    }

    public ArrayList<Ground> getClearToSeeGrounds() {
        return clearToSeeGrounds;
    }

    public ArrayList<Ground> getWasClearedToSeeGrounds() {
        return wasClearedToSeeGrounds;
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public static int getCounterOfNextRound() {
        return counterOfNextRound;
    }

    public User getUser() {
        return user;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void handleClearToSeeGrounds1depth(ArrayList<Ground> clearToSeeGrounds) {
        GlobalVariables globalVariables = new GlobalVariables();
        ArrayList<Ground> newArray = new ArrayList<>();
        for (int i = 0; i < clearToSeeGrounds.size(); i++) {
            Ground ground = clearToSeeGrounds.get(i);
            /// TODO : check kardan Kooh o ina
            if (ground.getGroundType().isBlock() && ground.unitsOfASpecificPlayerInThisGround(this).size() == 0)
                continue;
            for (int x = ground.getxLocation() - globalVariables.tool6Zelie; x <= ground.getxLocation() + globalVariables.tool6Zelie; x++) {
                for (int y = ground.getyLocation() - globalVariables.arz6Zelie; y <= ground.getyLocation() + globalVariables.arz6Zelie; y++) {
                    if (x == 0 && y == 0) continue;

                    Ground newGround = Ground.getGroundByXY(x, y);
                    if (newGround == null) continue;

                    if (newGround.getxLocation() == 0 || newGround.getxLocation() == globalVariables.surfaceHeight - 1 || newGround.getyLocation() == 0
                            || newGround.getyLocation() == globalVariables.surfaceWidth - 1) continue;

                    boolean shouldWeAddThisGround = false;
                    for (Ground clearToSeeGround : clearToSeeGrounds) {
                        if (clearToSeeGround.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    for (Ground value : newArray) {
                        if (value.equals(newGround)) {
                            shouldWeAddThisGround = true;
                            break;
                        }
                    }

                    if (shouldWeAddThisGround) continue;

                    newArray.add(newGround);
                }
            }
        }

        clearToSeeGrounds.addAll(newArray);
    }
    public void addGroundToClearGround(Ground ground){
        boolean exist = false;
        for (Ground clearToSeeGround : clearToSeeGrounds) {
            if (clearToSeeGround.getNumber() == ground.getNumber()) {
                exist = true;
                break;
            }
        }

        if (exist) return;
        this.clearToSeeGrounds.add(ground);
    }
    public void handleClearToSee() {
        this.clearToSeeGrounds = new ArrayList<>();
        for (Unit unit : this.units) {
            this.addGroundToClearGround(unit.getGround());
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds);
        for (int i=0;i<this.cities.size();i++){
            for (int j=0;j<this.cities.get(i).getRangeOfCity().size();j++){
                this.addGroundToClearGround(this.cities.get(i).getRangeOfCity().get(j));
            }
        }
        handleClearToSeeGrounds1depth(this.clearToSeeGrounds); //for the second depth
    }

    public static void nextTurn() {
        counterOfNextRound++;
        while (!whichPlayerTurnIs().isAlive) counterOfNextRound++;

        Player player = Player.whichPlayerTurnIs();
        for (int i = 0; i < player.units.size(); i++) {
            player.units.get(i).putMp(10);
            player.units.get(i).checkDestination();
        }
        player.gold=0;
        player.food=0;
        /// TODO : otherthings
        for (int i=0;i<player.getCities().size();i++){
            player.getCities().get(i).updateCityGoldAndFoodAndOtherThings();
            player.gold += player.getCities().get(i).getGold();
            player.food += player.getFood();
        }
    }

    public static Player whichPlayerTurnIs() {
        return allPlayers.get(counterOfNextRound % allPlayers.size());
    }

    public void addGroundToVisitedGround(Ground ground) {
        boolean exist = false;
        for (Ground wasClearedToSeeGround : wasClearedToSeeGrounds) {
            if (wasClearedToSeeGround.getNumber() == ground.getNumber()) {
                exist = true;
                break;
            }
        }

        if (exist) return;
        Ground copyGround = ground.copyOfCurrentGround();
        wasClearedToSeeGrounds.add(copyGround);
    }

    public boolean isThisGroundVisible(Ground ground) {
        for (Ground clearToSeeGround : clearToSeeGrounds) {
            if (clearToSeeGround.getNumber() == ground.getNumber()) return true;
        }
        return false;
    }

    public void addCityToThisGround(Ground ground){
        ///TODO : check UnMilitary Type is Settler
        boolean canWeCreateCity = false;
        for (Ground adjacentGround : ground.getAdjacentGrounds()){
            if (adjacentGround.isInRangeOfCity()) return ;
        }
        for (int i = 0;i < this.units.size(); i++){
            if (this.units.get(i) instanceof UnMilitaryUnit && this.units.get(i).ground.getNumber()==ground.getNumber()) canWeCreateCity=true;
        }
        if (canWeCreateCity){
            /// TODO : change something;
            City city = new City(ground,"something", this);
            this.cities.add(city);
        }

    }
}
