package model;

import Enum.FeatureType;
import Enum.GroundType;
import Enum.MilitaryType;

public class MeleeUnit extends MilitaryUnit {
    public MeleeUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    @Override
    public boolean combat(Ground ground) {
        if (ground.getNumber() == this.ground.getNumber()) return false;

        MilitaryUnit militaryUnit = ground.getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = ground.getUnMilitaryUnit();
        Player player = Player.findPlayerByUser(User.findUser(playerName));

        if (militaryUnit == null) {
            if (unMilitaryUnit != null) {
                unMilitaryUnit.changeOwner(player);
            }
            return false;
        }

        Player militaryPlayer = Player.findPlayerByUser(User.findUser(militaryUnit.playerName));
        player.setInWar(Player.findPlayerByUser(User.findUser(militaryUnit.playerName)));
        militaryPlayer.setInWar(player);
        double decreasedEnemyHp = (this.hp + 10) / 20 * this.getCombatStrength();
        decreasedEnemyHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        decreasedEnemyHp *= (double) 100.0 / (ground.getFeatureType().getCombatCoefficient() + 100.0);
        if (River.doWeHaveGroundBetweenTheseTwoGrounds(this.ground, ground)) {
            decreasedEnemyHp *= 2;
            decreasedEnemyHp /= 3;
        }
        if ((this.militaryType.equals(MilitaryType.SPEARMAN) || this.militaryType.equals(MilitaryType.PIKEMAN)) && militaryUnit.militaryType.getCombatType().equals("Mounted"))
            decreasedEnemyHp *= 2;
        if (this.militaryType.equals(MilitaryType.CHARIOTARCHER) && (ground.getGroundType().equals(GroundType.HILL) || ground.getFeatureType().equals(FeatureType.FOREST) || ground.getFeatureType().equals(FeatureType.JUNGLE)))
            decreasedEnemyHp /= 2;
        if (militaryUnit.getMilitaryType().getCombatType() != "Mounted" && militaryUnit.getMilitaryType().getCombatType() != "Siege") {
            if (militaryUnit.getTurnsFortified() >= 2)
                decreasedEnemyHp /= 2;
            if (militaryUnit.getTurnsFortified() == 1)
                decreasedEnemyHp *= 0.75;
        }
        double decreasedOwnHp = (militaryUnit.getHp() + 10) / 20 * militaryUnit.getMilitaryType().getCombatStrength();
        this.hp -= decreasedOwnHp;
        militaryUnit.setHp(militaryUnit.getHp() - decreasedEnemyHp);
        boolean isThisDestroyed = false;
        if (this.hp <= 0.0001) {
            this.removeUnit();
            isThisDestroyed = true;
        }
        if (militaryUnit.getHp() <= 0.0001) {
            militaryUnit.removeUnit();
            if (!isThisDestroyed) {
                this.ground = ground;
                if (unMilitaryUnit != null) {
                    unMilitaryUnit.changeOwner(player);
                }
            }
        }
        if (this.militaryType.equals(MilitaryType.HORSEMAN) || this.militaryType.equals(MilitaryType.KNIGHT) ||
                this.militaryType.equals(MilitaryType.CAVALRY) || this.militaryType.equals(MilitaryType.LANCER) ||
                this.militaryType.equals(MilitaryType.PANZER) || this.militaryType.equals(MilitaryType.TANK))
            this.mp = 10;
        else
            this.mp = 0;

        return militaryUnit.hp <= 0.0001;
    }
    @Override
    public boolean combat(City city) {
        Player player = Player.findPlayerByUser(User.findUser(playerName));
        player.setInWar(city.getOwner());
        city.getOwner().setInWar(player);
        MilitaryUnit militaryUnit = city.getGround().getMilitaryUnit();
        UnMilitaryUnit unMilitaryUnit = city.getGround().getUnMilitaryUnit();
        double decreasedEnemyHp = (this.hp + 10) / 20 * this.getCombatStrength();
        decreasedEnemyHp *= (double) 100.0 / (ground.getGroundType().getCombatCoefficient() + 100.0);
        if (this.getMilitaryType().equals(MilitaryType.TANK)) {
            decreasedEnemyHp *= 110;
            decreasedEnemyHp /= 100;
        }
        if (River.doWeHaveGroundBetweenTheseTwoGrounds(this.ground, city.getGround())) {
            decreasedEnemyHp *= 2;
            decreasedEnemyHp /= 3;
        }
        double decreasedOwnHp = city.getCityStrength();
        if (militaryUnit != null)
            decreasedOwnHp += (militaryUnit.hp + 10) / 20 * militaryUnit.getMilitaryType().getCombatStrength() / 3;
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
            player.setGold(player.getGold() + 40);
            city.getOwner().setGold((city.getOwner().getGold() * 2) / 3);
            //ConquerCityMenu.run(city, this.player);
            return true;

        }
        if (this.militaryType.equals(MilitaryType.HORSEMAN) || this.militaryType.equals(MilitaryType.KNIGHT) ||
                this.militaryType.equals(MilitaryType.CAVALRY) || this.militaryType.equals(MilitaryType.LANCER) ||
                this.militaryType.equals(MilitaryType.PANZER) || this.militaryType.equals(MilitaryType.TANK))
            this.mp = 10;
        else
            this.mp = 0;

        return false;
    }
}
