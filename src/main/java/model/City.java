package model;

import java.security.PublicKey;
import java.util.ArrayList;
import Enum.MilitaryType;
import Enum.LuxuryResource;
import Enum.BonusResource;
import Enum.StrategicResource;
import Enum.BuildingsType;

public class City {
    private Player player;
    private String name;
    private int savedFood;
    //private int production;
    //private int science;
    //private int gold;
    private RemainedTurns remainedTurnsToBuild = new RemainedTurns(0);
    private MilitaryType buildingUnit = null;
    private Ground ground;
    private Productions construction; //in the future, we should write a class for constructions and get type here
    private boolean isPuppet = false;
    private double hp = 20;
    private boolean isMainCapital;
    private Player mainCapitalFor;

    private final ArrayList<Unit> listOfUnitsInCity = new ArrayList<>();
    private final ArrayList<Citizen> listOfCitizens = new ArrayList<>();
    private final ArrayList<Ground> rangeOfCity = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();

    public City(Ground ground, String name, Player player) {
        this.name = name;
        this.ground = ground;
        this.rangeOfCity.add(ground);
        this.rangeOfCity.addAll(ground.getAdjacentGrounds());
        this.player=player;
        this.increasingCitizens();
    }

    public void setMainCapital() {
        isMainCapital = true;
        mainCapitalFor = player;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public boolean doWeHaveThisBuilding(BuildingsType buildingsType) {
        for (Building building: buildings) {
            if (building.name().equals(buildingsType.name())) return true;
        }

        return false;
    }

    public boolean doWeHaveThisStrategicResource(StrategicResource strategicResource) {
        for (Ground ground: rangeOfCity) {
            for (StrategicResource resource: ground.getStrategicResources()) {
                if (resource.name().equals(strategicResource.name())) return true;
            }
        }

        return false;
    }

    public boolean doWeHaveThisLuxuryResource(LuxuryResource luxuryResource) {
        for (Ground ground: rangeOfCity) {
            for (LuxuryResource resource: ground.getLuxuryResources()) {
                if (resource.name().equals(luxuryResource.name())) return true;
            }
        }

        return false;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void changeConstruction(Productions construction) {
        this.construction = construction;
    }

    public Productions getConstruction() {
        return construction;
    }

    public void setSavedFood(int savedFood) {
        this.savedFood = savedFood;
    }

    public void setConstruction(Productions construction) {
        this.construction = construction;
        this.remainedTurnsToBuild = construction.getTurnRemainedToComplete();
    }

    public void setPuppet(boolean puppet, Player player) {
        isPuppet = puppet;
        this.setPlayer(player);
    }

    public void setPlayer(Player player) {
        this.player.getCities().remove(this);

        this.player = player;
        this.ground.setOwner(player);
        for (Ground ground: this.rangeOfCity) {
            ground.setOwner(player);
        }

        player.getCities().add(this);
    }

    public boolean isPuppet() {
        return isPuppet;
    }

    //public void increaseGold(int amount) {
      //  this.gold += amount;
   // }

    public ArrayList<Unit> getListOfUnitsInCity() {
        return listOfUnitsInCity;
    }

    public Ground getGround() {
        return ground;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Citizen> getListOfCitizens() {
        return listOfCitizens;
    }

    public ArrayList<Ground> getRangeOfCity() {
        return rangeOfCity;
    }

    public int getSavedFood() {
        return savedFood;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    //public int getProduction() {
     //   return production;
   // }

    //public int getScience() {
  //      return science;
   // }

    //public int getGold() {
    //    return gold;
  //  }

    public MilitaryType getBuildingUnit() {
        return buildingUnit;
    }

    public void setBuildingUnit(MilitaryType buildingUnit) {
        this.buildingUnit = buildingUnit;
    }

    public int getRemainedTurnsToBuild() {
        return remainedTurnsToBuild.getTurns();
    }

    public void setRemainedTurnsToBuild(int remainedTurnsToBuild) {
        this.remainedTurnsToBuild.setTurns(remainedTurnsToBuild);
    }

    //public void giveMoneyForBuying(int amount) {
   //     this.gold -= amount;
   // }

    public void finishedConstructed() {
        if (this.construction == null) return;

        if (construction instanceof Unit unit) listOfUnitsInCity.add(unit);
        else if (construction instanceof Building building) buildings.add(building);

        this.construction = null;
    }

    public void addGroundToRangeOfCity(Ground ground){
        this.rangeOfCity.add(ground);
        ground.setOwner(this.player);
    }

    public boolean isThisGroundInThisCityRange(Ground ground){
        for (Ground value : rangeOfCity) {
            if (ground.getNumber() == value.getNumber()) return true;
        }
        return false;
    }

    public ArrayList<Citizen> withoutWorkCitizens() {
        ArrayList<Citizen> listOfWithoutWork = new ArrayList<>();
        for (Citizen citizen: this.getListOfCitizens()) {
            if (!citizen.isHaveWork()) listOfWithoutWork.add(citizen);
        }

        return listOfWithoutWork;
    }

    public Citizen isAnyoneWorkOnGround(Ground ground) {
        for (Citizen citizen : this.listOfCitizens) {
            if (citizen.getGround() != null && citizen.getGround().getNumber() == ground.getNumber()) return citizen;
        }

        return null;
    }

    public boolean isThisGroundNearThisCity(Ground ground) {
        for (int i = 1; i <= GlobalVariables.numberOfTiles; i++){
            if (!Ground.AreTheseTwoGroundAdjacent(ground,Ground.getGroundByNumber(i))) continue;
            for (int j = 1; j <= GlobalVariables.numberOfTiles; j++){
                if (!Ground.AreTheseTwoGroundAdjacent(Ground.getGroundByNumber(j), Ground.getGroundByNumber(i))) continue;
                if (Ground.getGroundByNumber(j).isInRangeOfCity()) return true;
            }
        }
        return false;
    }

    public void increasingCitizens() {
        Citizen.addCitizen(this);
        //TODO... do all the things that happen by increasing citizens
    }

    public ArrayList<Ground> groundsNearTheCity() {
        ArrayList<Ground> nearCity = new ArrayList<>();
        for (Ground ground: this.getRangeOfCity()) {
            nearCity.addAll(ground.getAdjacentGrounds());
        }

        nearCity.removeIf(ground -> this.getRangeOfCity().contains(ground));
        return nearCity;
    }

    public int howMuchFoodIsProduced() {
        //TODO write the body for it
        return 0;
    }


    public double getCityStrength() {
        double cityStrength = (double) this.rangeOfCity.size() / 2;
        if (this.ground.getMilitaryUnit() != null)
            cityStrength += 1.5;
        return cityStrength;
    }

    public void combat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        double decreasedHp = this.getCityStrength();
        decreasedHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        if (militaryUnit.militaryType.getCombatType() != "Mounted" && militaryUnit.militaryType.getCombatType() != "Siege") {
            if (militaryUnit.turnsFortified >= 2)
                decreasedHp /= 2;
            if (militaryUnit.turnsFortified == 1)
                decreasedHp *= 0.75;
        }
        militaryUnit.hp -= decreasedHp;
        if (militaryUnit.hp <= 0.000001)
            militaryUnit.removeUnit();
    }

    public int getGold() {
        int gold = 0;
        for (Ground ground : getRangeOfCity()) {
            if (ground.isWorkedOn()) {
                if (ground.canWeUseThisLuxuryResource())
                    gold += ground.getLuxuryResources().get(0).getGold();
                if (ground.canWeUseThisBonusResource())
                    gold += ground.getBonusResource().get(0).getGold();
                if (ground.canWeUseThisStrategicResource())
                    gold += ground.getStrategicResources().get(0).getGold();
                gold += ground.getGroundType().getGold();
                gold += ground.getFeatureType().getGold();
            }
        }
        return gold;
    }

    public int getProduction() {
        int production = listOfCitizens.size();
        for (Ground ground : getRangeOfCity()) {
            if (ground.canWeUseThisLuxuryResource())
                production += ground.getLuxuryResources().get(0).getProduction();
            if (ground.canWeUseThisBonusResource())
                production += ground.getBonusResource().get(0).getProduction();
            if (ground.canWeUseThisStrategicResource())
                production += ground.getStrategicResources().get(0).getProduction();
            if (ground.isWorkedOn()) {
                production += ground.getGroundType().getProduction();
                production += ground.getFeatureType().getProduction();
            }
        }
        return production;
    }

    public int getFoodPerTurn() {
        int food = 3;
        for (Ground ground : getRangeOfCity()) {
            if (ground.canWeUseThisLuxuryResource())
                food += ground.getLuxuryResources().get(0).getFood();
            if (ground.canWeUseThisBonusResource())
                food += ground.getBonusResource().get(0).getFood();
            if (ground.canWeUseThisStrategicResource())
                food += ground.getStrategicResources().get(0).getFood();
            if (ground.isWorkedOn()) {
                food += ground.getGroundType().getFood();
                food += ground.getFeatureType().getFood();
            }
        }
        food -= listOfCitizens.size() * 2;
        if (this.player.getHappiness() < 0)
            food /= 3;
        if (food < 0)
            food = 0;
        if (this.construction != null && this.construction.name().equals(MilitaryType.SETTLER.name()))
            food = 0;
        return food;
    }

    public int getScience() {
        int science = 5;
        science += this.listOfCitizens.size();
        return science;
    }

    public static City findCityByGround(Ground ground, Player player) {
        if (player == null) return null;

        for (City city: player.getCities()) {
            if (city.isThisGroundInThisCityRange(ground)) return city;
        }

        return null;
    }
}
