package model;

import Enum.MilitaryType;

public abstract class MilitaryUnit extends Unit {
    public MilitaryUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player.getUser().getUsername(), militaryType);
    }

    public abstract void combat(Ground ground);
    public abstract void combat(City city);
    /// agha yadet bashe vaghti sakhtish set player ham bokonim



}
