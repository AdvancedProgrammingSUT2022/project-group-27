package controller;

import model.City;
import model.Player;
import model.Unit;
import Enum.MilitaryType;

public class UnitController {
    public void deleteUnit(Unit unit) {
        Player player = unit.getPlayer();
        player.setGold(player.getGold() + unit.getCost() / 10);
        unit.removeUnit();
    }

    public void addUnit(City city, MilitaryType militaryType) {
        if (city.getRemainedTurnsToBuild() != 0) {
            return;
        }
        city.setRemainedTurnsToBuild(militaryType.getTurn());
        city.setBuildingUnit(militaryType);
    }

    public void spawnUnit(City city) {
        MilitaryType militaryType = city.getBuildingUnit();
    }
}
