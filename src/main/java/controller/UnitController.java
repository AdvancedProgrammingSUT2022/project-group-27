package controller;

import model.*;
import Enum.*;
import Enum.MilitaryType;

public class UnitController extends Controller {
    public static Message findUnitFromMatcher(int groundNumber, String type, Player player) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) return Message.INVALID_GROUND_NUMBER;
        if (type.equals("Military") && (ground.isFreeOfMilitaryUnit() || ground.getMilitaryUnit().getPlayer() != player))
            return Message.INVALID_TYPE;
        if (type.equals("UnMilitary") && (ground.isFreeOfUnMilitaryUnit() || ground.getUnMilitaryUnit().getPlayer() != player))
            return Message.INVALID_TYPE;
        
        return Message.UNIT_CHOICE_SUCCESSFUL;
    }
    
    public static void deleteUnit(Unit unit) {
        Player player = unit.getPlayer();
        player.setGold(player.getGold() + unit.getCost() / 10);
        unit.removeUnit();
    }

    public static void buildUnit(City city, MilitaryType militaryType) {
        Player player = city.getOwner();
        if (city.getRemainedTurnsToBuild() > 0) {
            System.out.println("Another building is in process");
            return;
        }
        city.setRemainedTurnsToBuild((militaryType.getCost() + city.getProduction() - 1) / city.getProduction());
        city.setBuildingUnit(militaryType);
    }

    public static void addUnit(Player player, Ground ground, MilitaryType militaryType) {
        if (militaryType.equals(MilitaryType.SETTLER)) {
            SettlerUnit settlerUnit = new SettlerUnit(ground, player);
            player.getUnits().add(settlerUnit);
            return ;
        }
        if (militaryType.equals(MilitaryType.WORKER)) {
            Worker worker = new Worker(ground, player);
            player.getUnits().add(worker);
            return ;
        }
        if (militaryType.getCombatType().equals("Archery") || militaryType.getCombatType().equals("Siege") || militaryType.equals(militaryType.CHARIOTARCHER)) {
            RangedUnit rangedUnit = new RangedUnit(ground, player, militaryType);
            player.getUnits().add(rangedUnit);
            return ;
        }
        MeleeUnit meleeUnit = new MeleeUnit(ground, player, militaryType);
        player.getUnits().add(meleeUnit);
    }

    public static void spawnUnit(City city) {
        //note: keep main ground first in range of city
        MilitaryType militaryType = city.getBuildingUnit();
        if (militaryType == null) return;
        for (Ground ground : city.getRangeOfCity()) {
            if ((militaryType.getCombatType() == "Civilian" && ground.getUnMilitaryUnit() == null) ||
                    (militaryType.getCombatType() != "Civilian" && ground.getMilitaryUnit() == null)) {
                addUnit(city.getOwner(), ground, militaryType);
                city.setBuildingUnit(null);
                System.out.println(militaryType + " mili");
                return;
            }
        }
    }

    public static Message established(Unit unit) {
        Ground ground = unit.getGround();
        City city = City.findCityByGround(ground, unit.getPlayer());
        if (city == null) return Message.UNIT_CAN_NOT_DO;
        if (unit instanceof MilitaryUnit) {
            if (city.getGround().getMilitaryUnit() != null || city.getGround().getMilitaryUnit() != unit)
                return Message.UNIT_CAN_NOT_DO;
            unit.setCityGround(ground);
        }

        if (unit instanceof UnMilitaryUnit) {
            if (city.getGround().getUnMilitaryUnit() != null || city.getGround().getUnMilitaryUnit() != unit)
                return Message.UNIT_CAN_NOT_DO;
            unit.setCityGround(ground);
        }

        return Message.SUCCESS_WORK;
    }

    public static Message rangedFight(Unit unit, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) return Message.INVALID_GROUND_NUMBER;

        if (unit instanceof RangedUnit) {
            if ((!unit.getMilitaryType().getCombatType().equals("Siege") ||
                    ((RangedUnit)unit).isReadyToRangedFight())) {
                if ((unit.getMilitaryType().equals(MilitaryType.ARTILLERY) && unit.getGround().getDistance(ground) <= unit.getMilitaryType().getRange()) ||
                        (!unit.getMilitaryType().equals(MilitaryType.ARTILLERY) && unit.getGround().getDistanceWithoutBlocks(ground) <= unit.getMilitaryType().getRange())) {
                    City city = City.findCityByGround(ground, ground.ownerOfThisGround());
                    if (city == null) ((RangedUnit) unit).combat(ground);
                    else ((RangedUnit) unit).combat(city);

                    return Message.SUCCESS_WORK;
                }
            }
        }

        return Message.UNIT_CAN_NOT_DO;
    }

    public static Message readyToRangedFight(Unit unit) {
        if (unit instanceof RangedUnit && unit.getMilitaryType().getCombatType().equals("Siege")) {
            ((RangedUnit)unit).setReadyToRangedFight(true);
            return Message.SUCCESS_WORK;
        }
        return Message.UNIT_CAN_NOT_DO;
    }

    public static Message plundering(Unit unit) {
        Ground ground = unit.getGround();
        ground.setPlunderingImprovementType(ground.getImprovementType());
        ground.setImprovementType(null);
        if (ground.getRoad()!=null) ground.getRoad().setTurn(2000000);
        if (ground.getRailWay()!=null) ground.getRailWay().setTurn(2000000);
        return Message.SUCCESS_WORK;
    }
    public static Message freePlundering(Unit unit) {
        Ground ground = unit.getGround();
        if (ground.getRoad()!=null) ground.getRoad().setTurn(2);
        if (ground.getRailWay()!=null) ground.getRailWay().setTurn(2);
        if (ground.getPlunderingImprovementType()!=null) ground.getPlunderingImprovementType().setTurnRemained(3);
        return Message.SUCCESS_WORK;
    }

    public static String removeOneOrder(Unit unit) {
        unit.setDestination(null);
        return unit.getMilitaryType().name();
    }

    public static Message meleeFight(Unit unit, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) return Message.INVALID_GROUND_NUMBER;

        if (unit instanceof MeleeUnit && unit.getGround().AreTheseTwoGroundAdjacent(unit.getGround(), ground)) {
            City city = City.findCityByGround(ground, ground.ownerOfThisGround());
            if (city == null) ((MeleeUnit)unit).combat(ground);
            else ((MeleeUnit)unit).combat(city);
            return Message.SUCCESS_WORK;
        }

        return Message.UNIT_CAN_NOT_DO;
    }
}
