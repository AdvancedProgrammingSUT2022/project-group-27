package controller;

import model.Player;
import model.Unit;

public class UnitController {
    public void deleteUnit(Unit unit) {
        Player player = unit.getPlayer();
        player.setGold(player.getGold() + unit.getCost() / 10);
        unit.removeUnit();
    }

    
}
