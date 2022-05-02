package controller;

import Enum.MilitaryType;
import model.*;

public class CityController extends Controller{
    protected Unit checkValidationOfUnitName(City city, String unitName) {
        MilitaryType unitType = null;
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (city.getPlayer().doWeHaveThisTechnology(militaryType.getTechnologyTypes().get(0))) { //TODO change technology type to object not arraylist
                if (militaryType.getCombatType().equals(unitName)) {
                    unitType = militaryType;
                    break;
                }
            }
        }

        if (unitType == null) return null;
        if (unitType == MilitaryType.WORKER) return new Worker(city.getGround(), city.getPlayer(), unitType);
        if (unitType == MilitaryType.SETTLER) return new SettlerUnit(city.getGround(), city.getPlayer(), unitType);
        if (unitType.getCombatType().matches("Archery|Mounted|Siege")) return new RangedUnit(city.getGround(),
                city.getPlayer(), unitType);
        if (unitType.getCombatType().matches("Recon|Melee|Gunpowder|Armored")) return new MeleeUnit(city.getGround(),
                city.getPlayer(), unitType);
        return null;
    }
}
