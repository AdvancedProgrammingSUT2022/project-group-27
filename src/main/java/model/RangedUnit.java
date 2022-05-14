package model;

import Enum.MilitaryType;

public class RangedUnit extends MilitaryUnit{
    protected boolean isReadyToRangedFight = false;

    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    public void setReadyToRangedFight(boolean readyToRangedFight) {
        isReadyToRangedFight = readyToRangedFight;
    }

    public boolean isReadyToRangedFight() {
        return isReadyToRangedFight;
    }

    @Override
    public void combat(Ground ground) {
        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        if (militaryUnit == null)
            return;

        double decreasedHp = (this.hp + 10) / 20 * this.getRangedCombatStrength();
        decreasedHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        if (militaryUnit.militaryType.getCombatType() != "Mounted" && militaryUnit.militaryType.getCombatType() != "Siege") {
            if (militaryUnit.turnsFortified >= 2)
                decreasedHp /= 2;
            if (militaryUnit.turnsFortified == 1)
                decreasedHp *= 0.75;
        }
        militaryUnit.hp -= decreasedHp;
        if (militaryUnit.hp <= 0.000001)
            militaryUnit.removeUnit();
        this.mp = 0;
    }

    @Override
    public void combat(City city) {
        double decreasedHp = (this.hp + 10) / 20 * this.getRangedCombatStrength();
        decreasedHp *= (double) 100.0 / (city.getGround().getGroundType().getCombatCoefficient() + 100.0);
        if (this.getMilitaryType().getCombatType().equals("Siege")) {
            decreasedHp *= 110;
            decreasedHp /= 100;
        }
        city.setHp(city.getHp() - decreasedHp);
        if (city.getHp() < 1)
            city.setHp(1);
        this.mp = 0;
    }
}
