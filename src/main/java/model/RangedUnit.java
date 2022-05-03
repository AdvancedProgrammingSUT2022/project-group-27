package model;

import Enum.MilitaryType;

public class RangedUnit extends MilitaryUnit{
    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    public void rangedCombat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = ground.getUnMilitaryUnit();
        double decreasedHp = (this.hp + 10) / 20 * this.militaryType.getRangedCombatStrength();
        decreasedHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        if (militaryUnit.turnsFortified >= 2)
            decreasedHp /= 2;
        if (militaryUnit.turnsFortified == 1)
            decreasedHp *= 0.75;
        militaryUnit.hp -= decreasedHp;
        if (militaryUnit.hp <= 0.000001)
            militaryUnit.removeUnit();
    }
}
