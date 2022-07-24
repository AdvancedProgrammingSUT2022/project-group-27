package model;

import Enum.MilitaryType;

public class SettlerUnit extends UnMilitaryUnit{

    public SettlerUnit(Ground ground, Player player) {
        super(ground, player, MilitaryType.SETTLER);
    }
    public void buildCity(String name) {
        Player player = Player.findPlayerByUser(User.findUser(playerName));
        player.addCityToThisGround(this.ground, name);
    }
}
