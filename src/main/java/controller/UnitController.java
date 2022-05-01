package controller;

import model.*;
import Enum.MilitaryType;

public class UnitController {
    public void deleteUnit(Unit unit) {
        Player player = unit.getPlayer();
        player.setGold(player.getGold() + unit.getCost() / 10);
        unit.removeUnit();
    }

    public void buildUnit(City city, MilitaryType militaryType) {
        Player player = city.getPlayer();
        if (city.getRemainedTurnsToBuild() != 0) {
            return;
        }
        if (player.getGold() < militaryType.getCost()) {
            return;
        }
        city.setRemainedTurnsToBuild(militaryType.getTurn());
        city.setBuildingUnit(militaryType);
        player.setGold(player.getGold() - militaryType.getCost());
    }

    public void addUnit(Player player, Ground ground, MilitaryType militaryType) {
        if (militaryType.equals(militaryType.SETTLER)) {
            SettlerUnit settlerUnit = new SettlerUnit(ground, player, militaryType);
            player.getUnits().add(settlerUnit);
        }
        if (militaryType.equals(militaryType.WORKER)) {
            Worker worker = new Worker(ground, player, militaryType);
            player.getUnits().add(worker);
        }
        if (militaryType.getCombatType().equals("Archery") || militaryType.getCombatType().equals("Siege") || militaryType.equals(militaryType.CHARIOTARCHER)) {
            RangedUnit rangedUnit = new RangedUnit(ground, player, militaryType);
            player.getUnits().add(rangedUnit);
        }
        MeleeUnit meleeUnit = new MeleeUnit(ground, player, militaryType);
        player.getUnits().add(meleeUnit);
    }

    public void spawnUnit(City city) {
        //note: keep main ground first in range of city
        MilitaryType militaryType = city.getBuildingUnit();
        for (Ground ground : city.getRangeOfCity()) {
            if ((militaryType.getCombatType() == "Civilian" && ground.getUnMilitaryUnit() == null) ||
                    (militaryType.getCombatType() != "Civilian" && ground.getMilitaryUnit() == null)) {
                addUnit(city.getPlayer(), ground, militaryType);
                return;
            }
        }
    }
}
