package model;

import Enum.MilitaryType;

public class RangedUnit extends MilitaryUnit{
    public RangedUnit(Ground ground, Player player, MilitaryType militaryType) {
        super(ground, player, militaryType);
    }

    @Override
    public void combat(Ground ground) {

    }
    @Override
    public void combat(City city) {

    }
}
