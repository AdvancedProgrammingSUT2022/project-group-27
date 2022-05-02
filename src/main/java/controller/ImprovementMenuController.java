package controller;

import model.Player;
import model.Unit;
import model.Worker;

public class ImprovementMenuController {
    public boolean haveWorker(Player player, int groundNumber) {
        for (Unit unit: player.getUnits()){
            if (unit.getGround().getNumber() == groundNumber && unit instanceof Worker) return true;
        }

        return false;
    }
}
