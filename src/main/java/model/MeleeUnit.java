package model;

import Enum.MilitaryType;

public class MeleeUnit extends MilitaryUnit {
    public MeleeUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    @Override
    public void combat(Ground ground) {

    }
    @Override
    public void combat(City city) {

    }
}
