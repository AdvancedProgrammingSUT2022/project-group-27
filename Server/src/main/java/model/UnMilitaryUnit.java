package model;

import Enum.MilitaryType;

public abstract class UnMilitaryUnit extends Unit {
    public UnMilitaryUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player.getUser().getUsername(), militaryType);
    }
}
