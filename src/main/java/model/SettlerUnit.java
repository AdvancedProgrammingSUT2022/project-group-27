package model;

import Enum.MilitaryType;

public class SettlerUnit extends UnMilitaryUnit{

    public SettlerUnit(Ground ground, Player player) {
        super(ground, player, MilitaryType.SETTLER);
    }
    public void buildCity(String name) {
        this.player.addCityToThisGround(this.ground, name);
    }
}
