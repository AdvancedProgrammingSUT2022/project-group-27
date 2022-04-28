package model;

import Enum.MilitaryType;

public abstract class MilitaryUnit extends Unit {
    protected MilitaryType militaryType;
    public MilitaryUnit(Ground ground, Player player, MilitaryType militaryType) {
        this.player = player;
        this.ground = ground;
        this.militaryType = militaryType;
    }

    public abstract void combat(Ground ground);
    public abstract void combat(City city);
    /// agha yadet bashe vaghti sakhtish set player ham bokonim



}
