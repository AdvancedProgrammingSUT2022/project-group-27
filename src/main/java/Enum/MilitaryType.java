package Enum;

import java.util.ArrayList;
import java.util.List;

public enum MilitaryType {
    ARCHER(70, "Archery", 4, 6, 2, 2,  new ArrayList<StrategicResource>(),  new ArrayList<TechnologyType>(List.of(TechnologyType.ARCHERY))),
    CHARIOTARCHER(60, "Mounted", 3, 6, 2, 4,  new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)),  new ArrayList<TechnologyType>(List.of(TechnologyType.THE_WHEEL))),
    SCOUT(25, "Recond", 4, 0, 0, 2, new ArrayList<StrategicResource>(),  new ArrayList<TechnologyType>()),
    SPEARMAN(50, "Melee", 7, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.BRONZE_WORKING))),
    WARRIOR(40, "Melee", 6, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>()),
    CATAPULT(100, "Siege", 4, 14, 2, 2, new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), new ArrayList<TechnologyType>(List.of(TechnologyType.MATHEMATICS))),
    HORSEMAN(80, "Mounted", 12, 0, 0, 4, new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), new ArrayList<TechnologyType>(List.of(TechnologyType.HORSEBACK_RIDING))),
    SWORDSMAN(80, "Melee", 11, 0, 0, 2, new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), new ArrayList<TechnologyType>(List.of(TechnologyType.IRON_WORKING))),
    CROSSBOWMAN(120, "Archery", 6, 12, 2, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.MACHINERY))),
    KNIGHT(150, "Mounted", 18, 0, 0, 3, new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), new ArrayList<TechnologyType>(List.of(TechnologyType.CHIVALRY))),
    LONGSWORDSMAN(150, "Melee", 18, 0, 0, 3, new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), new ArrayList<TechnologyType>(List.of(TechnologyType.STEEL))),
    PIKEMAN(100, "Melee", 10, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.CIVIL_SERVICE))),
    TREBUCHET(170, "Siege", 6, 20, 2, 2, new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), new ArrayList<TechnologyType>(List.of(TechnologyType.PHYSICS))),
    CANON(250, "Siege", 10, 26, 2, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.CHEMISTRY))),
    CAVALRY(260, "Mounted", 25, 0, 0, 3, new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), new ArrayList<TechnologyType>(List.of(TechnologyType.MILITARY_SCIENCE))),
    LANCER(220, "Mounted", 22, 0, 0, 4, new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), new ArrayList<TechnologyType>(List.of(TechnologyType.METALLURGY))),
    MUSKETMAN(120, "Gunpowder", 16, 0, 0, 2,new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.GUNPOWDER))),
    RIFLEMAN(200, "Gunpowder", 25, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.RIFLING))),
    ANTITANKGUN(300, "Gunpowder", 32, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.REPLACEABLE_PARTS))),
    ARTILLERY(420, "Siege", 16, 32, 3, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.DYNAMITE))),
    INFANTRY(300, "Gunpowder", 36, 0, 0, 2, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.REPLACEABLE_PARTS))),
    PANZER(450, "Armored", 60, 0, 0, 5, new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.COMBUSTION))),
    TANK(450, "Armored", 50, 0, 0, 4,new ArrayList<StrategicResource>(), new ArrayList<TechnologyType>(List.of(TechnologyType.COMBUSTION)));

    private final int cost, combatStrength, rangedCombatStrength, range, movement;
    private final String combatType;
    private final ArrayList<StrategicResource> strategicResources;
    private final ArrayList<TechnologyType> technologyTypes;


    MilitaryType(int cost, String combatType, int combatStrength, int rangedCombatStrength, int range, int movement, ArrayList<StrategicResource> strategicResources, ArrayList<TechnologyType> technologyTypes) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.strategicResources = strategicResources;
        this.technologyTypes = technologyTypes;
    }

    public int getCost() {
        return cost;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public int getRange() {
        return range;
    }

    public int getMovement() {
        return movement;
    }

    public String getCombatType() {
        return combatType;
    }

    public ArrayList<StrategicResource> getStrategicResources() {
        return strategicResources;
    }

    public ArrayList<TechnologyType> getTechnologyTypes() {
        return technologyTypes;
    }
}
