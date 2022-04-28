package model;

public abstract class UnMilitaryUnit extends Unit {
    public UnMilitaryUnit(Ground ground, Player player) {
        this.player = player;
        this.ground = ground;
    }

}
