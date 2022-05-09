package model;

import java.util.ArrayList;
import Enum.MilitaryType;
import Enum.LuxuryResource;
import Enum.BonusResource;
import Enum.StrategicResource;

public class City {
    private Player player;
    private String name;
    private int power;
    private int savedFood;
    //private int production;
    private int science;
    //private int gold;
    private int income;
    private RemainedTurns remainedTurnsToBuild = new RemainedTurns(0);
    private MilitaryType buildingUnit = null;
    private Ground ground;
    private Unit construction; //in the future, we should write a class for constructions and get type here
    private boolean isPuppet = false;

    private final ArrayList<Unit> listOfUnitsInCity = new ArrayList<>();
    private final ArrayList<Citizen> listOfCitizens = new ArrayList<>();
    private final ArrayList<Ground> rangeOfCity = new ArrayList<>();

    public City(Ground ground, String name, Player player) {
        this.name = name;
        this.ground = ground;
        this.rangeOfCity.add(ground);
        this.rangeOfCity.addAll(ground.getAdjacentGrounds());
        this.player=player;
        this.increasingCitizens();
    }

    public void changeConstruction(Unit construction) {
        this.construction = construction;
    }

    public Unit getConstruction() {
        return construction;
    }

    public void setSavedFood(int savedFood) {
        this.savedFood = savedFood;
    }

    public void setConstruction(Unit construction) {
        this.construction = construction;
        this.remainedTurnsToBuild = construction.getTurnRemainedToComplete();
    }

    public void setPuppet(boolean puppet) {
        isPuppet = puppet;
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

    public int getPower() {
        return power;
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

    //public int getProduction() {
     //   return production;
   // }

    public int getScience() {
        return science;
    }

    //public int getGold() {
    //    return gold;
  //  }

    public int getIncome() {
        return income;
    }

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

        listOfUnitsInCity.add(this.construction);
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
            if (citizen.getGround().getNumber() == ground.getNumber()) return citizen;
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
        if (militaryUnit.turnsFortified >= 2)
            decreasedHp /= 2;
        if (militaryUnit.turnsFortified == 1)
            decreasedHp *= 0.75;
        militaryUnit.hp -= decreasedHp;
        if (militaryUnit.hp <= 0.000001)
            militaryUnit.removeUnit();
    }

    public int getGold() {
        int gold = 0;
        for (Ground ground : getRangeOfCity()) {
            if (ground.isWorkedOn()) {
                gold += ground.getGroundType().getGold();
                gold += ground.getFeatureType().getGold();
                for (LuxuryResource luxuryResource : ground.getLuxuryResources()) {
                    gold += luxuryResource.getGold();
                }
                for (BonusResource bonusResource : ground.getBonusResource()) {
                    gold += bonusResource.getGold();
                }
                for (StrategicResource strategicResource : ground.getStrategicResources()) {
                    gold += strategicResource.getGold();
                }
            }
        }
        return gold;
    }

    public int getProduction() {
        int production = 3;
        for (Ground ground : getRangeOfCity()) {
            if (ground.isWorkedOn()) {
                production += ground.getGroundType().getProduction();
                production += ground.getFeatureType().getProduction();
                for (LuxuryResource luxuryResource : ground.getLuxuryResources()) {
                    production += luxuryResource.getProduction();
                }
                for (BonusResource bonusResource : ground.getBonusResource()) {
                    production += bonusResource.getProduction();
                }
                for (StrategicResource strategicResource : ground.getStrategicResources()) {
                    production += strategicResource.getProduction();
                }
            }
        }
        return production;
    }

    public int getFoodPerTurn() {
        int food = 3;
        for (Ground ground : getRangeOfCity()) {
            if (ground.isWorkedOn()) {
                food += ground.getGroundType().getFood();
                food += ground.getFeatureType().getFood();
                for (LuxuryResource luxuryResource : ground.getLuxuryResources()) {
                    food += luxuryResource.getFood();
                }
                for (BonusResource bonusResource : ground.getBonusResource()) {
                    food += bonusResource.getFood();
                }
                for (StrategicResource strategicResource : ground.getStrategicResources()) {
                    food += strategicResource.getFood();
                }
            }
        }
        food -= listOfCitizens.size() * 2;
        //TODO: happiness
        if (food < 0)
            food = 0;
        return food;
    }

}
