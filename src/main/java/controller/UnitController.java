package controller;

import model.*;
import Enum.*;

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
        Player player = city.getPlayer();
        if (city.getRemainedTurnsToBuild() > 0) {
            System.out.println("Another building is in process");
            return;
        }
        city.setRemainedTurnsToBuild((militaryType.getCost() + city.getProduction() - 1) / city.getProduction());
        city.setBuildingUnit(militaryType);
    }

    public static void addUnit(Player player, Ground ground, MilitaryType militaryType) {
        if (militaryType.equals(militaryType.SETTLER)) {
            SettlerUnit settlerUnit = new SettlerUnit(ground, player, militaryType);
            player.getUnits().add(settlerUnit);
            return ;
        }
        if (militaryType.equals(militaryType.WORKER)) {
            Worker worker = new Worker(ground, player, militaryType);
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
        for (Ground ground : city.getRangeOfCity()) {
            if ((militaryType.getCombatType() == "Civilian" && ground.getUnMilitaryUnit() == null) ||
                    (militaryType.getCombatType() != "Civilian" && ground.getMilitaryUnit() == null)) {
                addUnit(city.getPlayer(), ground, militaryType);
                System.out.println(militaryType + " mili");
                return;
            }
        }
    }

    public static Message established(Unit unit) {
        return Message.SUCCESS_WORK;
    }

    public static Message readyToFight(Unit unit) {
        return Message.SUCCESS_WORK;
    }

    public static Message readyToRangedFight(Unit unit) {
        return Message.SUCCESS_WORK;
    }

    public static Message plundering(Unit unit) {
        return Message.SUCCESS_WORK;
    }

    public static String removeOneOrder(Unit unit) {
        return unit.getMilitaryType().name();
    }
}
