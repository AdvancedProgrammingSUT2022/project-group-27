package model;

import Enum.MilitaryType;

public class RangedUnit extends MilitaryUnit{
    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    @Override
    public void combat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
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
    @Override
    public void combat(City city) {

    }
}
