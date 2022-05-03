package model;

import Enum.MilitaryType;

public class RangedUnit extends MilitaryUnit{
    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    public void rangedCombat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = ground.getUnMilitaryUnit();
        if (militaryUnit == null && unMilitaryUnit == null)
            return;
        if (militaryUnit == null) {

        }
    }
}
