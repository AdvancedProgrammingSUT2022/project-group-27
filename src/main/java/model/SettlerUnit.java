package model;

public class SettlerUnit extends UnMilitaryUnit{

    public SettlerUnit(Ground ground, Player player) {
        super(ground, player);
    }
    public void buildCity(String name) {
        this.player.addCityToThisGround(this.ground);

    }
}
