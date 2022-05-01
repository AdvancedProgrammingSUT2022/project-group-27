package model;

import Enum.MilitaryType;

public class SettlerUnit extends UnMilitaryUnit{

    public SettlerUnit(Ground ground, Player player, MilitaryType militaryUnit) {
        super(ground, player, militaryUnit);
    }
    public void buildCity(String name) {
        this.player.addCityToThisGround(this.ground);

    }
}
