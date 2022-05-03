package model;

import Enum.MilitaryType;

public class MeleeUnit extends MilitaryUnit {
    public MeleeUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    @Override
    public void combat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = ground.getUnMilitaryUnit();
        if (militaryUnit == null) {
            if (unMilitaryUnit != null) {
                unMilitaryUnit.changeOwner(this.player);
            }
            return;
        }
        double decreasedEnemyHp = (this.hp + 10) / 20 * this.militaryType.getCombatStrength();
        decreasedEnemyHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        if (militaryUnit.turnsFortified >= 2)
            decreasedEnemyHp /= 2;
        if (militaryUnit.turnsFortified == 1)
            decreasedEnemyHp *= 0.75;
        double decreasedOwnHp = (militaryUnit.hp + 10) / 20 * this.militaryType.getCombatStrength();
        this.hp -= decreasedOwnHp;
        militaryUnit.hp -= decreasedEnemyHp;
        boolean isThisDestroyed = false;
        if (this.hp <= 0.0001) {
            this.removeUnit();
            isThisDestroyed = true;
        }
        if (militaryUnit.hp <= 0.0001) {
            militaryUnit.removeUnit();
            if (!isThisDestroyed) {
                this.ground = ground;
                if (unMilitaryUnit != null) {
                    unMilitaryUnit.changeOwner(this.player);
                }
            }
        }

    }
    @Override
    public void combat(City city) {

    }
}
