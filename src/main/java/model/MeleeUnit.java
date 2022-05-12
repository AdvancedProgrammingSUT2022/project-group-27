package model;

import Enum.MilitaryType;
import view.game.ConquerCityMenu;

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
        if (militaryUnit.militaryType.getCombatType() != "Mounted" && militaryUnit.militaryType.getCombatType() != "Siege") {
            if (militaryUnit.turnsFortified >= 2)
                decreasedEnemyHp /= 2;
            if (militaryUnit.turnsFortified == 1)
                decreasedEnemyHp *= 0.75;
        }
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
        if (this.militaryType.equals(MilitaryType.HORSEMAN) || this.militaryType.equals(MilitaryType.KNIGHT) || this.militaryType.equals(MilitaryType.CAVALRY) || this.militaryType.equals(MilitaryType.LANCER) || this.militaryType.equals(MilitaryType.PANZER) || this.militaryType.equals(MilitaryType.TANK))
            mp = 10;

    }
    @Override
    public void combat(City city) {
        MilitaryUnit militaryUnit = city.getGround().getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = city.getGround().getUnMilitaryUnit();
        double decreasedEnemyHp = (this.hp + 10) / 20 * this.militaryType.getCombatStrength();
        decreasedEnemyHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        double decreasedOwnHp = city.getCityStrength();
        if (militaryUnit != null)
            decreasedOwnHp += (militaryUnit.hp + 10) / 20 * this.militaryType.getCombatStrength() / 3;
        this.hp -= decreasedOwnHp;
        city.setHp(city.getHp() - decreasedEnemyHp);
        boolean isThisDestroyed = false;
        if (this.hp <= 0.0001) {
            this.removeUnit();
            isThisDestroyed = true;
        }
        if (city.getHp() <= 0.0001) {
            if (militaryUnit != null)
                militaryUnit.removeUnit();
            if (unMilitaryUnit != null)
                unMilitaryUnit.removeUnit();
            if (!isThisDestroyed)
                this.ground = city.getGround();
            ConquerCityMenu.run(city);

        }
        if (this.militaryType.equals(MilitaryType.HORSEMAN) || this.militaryType.equals(MilitaryType.KNIGHT) || this.militaryType.equals(MilitaryType.CAVALRY) || this.militaryType.equals(MilitaryType.LANCER) || this.militaryType.equals(MilitaryType.PANZER) || this.militaryType.equals(MilitaryType.TANK))
            mp = 10;

    }
}
