package model;

import Enum.MilitaryType;

public abstract class MilitaryUnit extends Unit {
    protected boolean isReadyToFight = false;
    protected boolean isReadyToRangedFight = false;

    public MilitaryUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
        this.combatStrength = militaryType.getCombatStrength();
        this.rangedStrength = militaryType.getRangedCombatStrength();
        this.rangeOfCombat = militaryType.getRange();
    }

    public void setReadyToFight(boolean readyToFight) {
        isReadyToFight = readyToFight;
    }

    public void setReadyToRangedFight(boolean readyToRangedFight) {
        isReadyToRangedFight = readyToRangedFight;
    }

    public boolean isReadyToFight() {
        return isReadyToFight;
    }

    public boolean isReadyToRangedFight() {
        return isReadyToRangedFight;
    }

    public abstract void combat(Ground ground);
    public abstract void combat(City city);
    /// agha yadet bashe vaghti sakhtish set player ham bokonim



}
