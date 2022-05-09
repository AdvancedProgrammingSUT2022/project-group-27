package Enum;

import java.util.ArrayList;
import java.util.List;

public enum MilitaryType {
    WORKER(70, "Civilian", 0, 0, 0, 2,
            new ArrayList<StrategicResource>(), null),
    SETTLER(89, "Civilian", 0, 0, 0, 2,
            new ArrayList<StrategicResource>(), null),
    ARCHER(70, "Archery", 4, 6, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.ARCHERY),
    CHARIOTARCHER(60, "Mounted", 3, 6, 2, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.THE_WHEEL),
    SCOUT(25, "Recon", 4, 0, 0, 2,
            new ArrayList<StrategicResource>(), null),
    SPEARMAN(50, "Melee", 7, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.BRONZE_WORKING),
    WARRIOR(40, "Melee", 6, 0, 0, 2,
            new ArrayList<StrategicResource>(), null),
    CATAPULT(100, "Siege", 4, 14, 2, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.MATHEMATICS),
    HORSEMAN(80, "Mounted", 12, 0, 0, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.HORSEBACK_RIDING),
    SWORDSMAN(80, "Melee", 11, 0, 0, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.IRON_WORKING),
    CROSSBOWMAN(7, 120, "Archery", 6, 12, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.MACHINERY),
    KNIGHT(8, 150, "Mounted", 18, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.CHIVALRY),
    LONGSWORDSMAN(8, 150, "Melee", 18, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.STEEL),
    PIKEMAN(7, 100, "Melee", 10, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.CIVIL_SERVICE),
    TREBUCHET(8, 170, "Siege", 6, 20, 2, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.PHYSICS),
    CANON(9, 250, "Siege", 10, 26, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.CHEMISTRY),
    CAVALRY(9, 260, "Mounted", 25, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.MILITARY_SCIENCE),
    LANCER(8, 220, "Mounted", 22, 0, 0, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.METALLURGY),
    MUSKETMAN(5, 120, "Gunpowder", 16, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.GUNPOWDER),
    RIFLEMAN(7, 200, "Gunpowder", 25, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.RIFLING),
    ANTITANKGUN(9, 300, "Gunpowder", 32, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.REPLACEABLE_PARTS),
    ARTILLERY(10, 420, "Siege", 16, 32, 3, 2,
            new ArrayList<StrategicResource>(), TechnologyType.DYNAMITE),
    INFANTRY(10, 300, "Gunpowder", 36, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.REPLACEABLE_PARTS),
    PANZER(10, 450, "Armored", 60, 0, 0, 5,
            new ArrayList<StrategicResource>(), TechnologyType.COMBUSTION),
    TANK(9, 450, "Armored", 50, 0, 0, 4,
            new ArrayList<StrategicResource>(), TechnologyType.COMBUSTION);

    private final int turn, cost, combatStrength, rangedCombatStrength, range, movement;
    private final String combatType;
    private final ArrayList<StrategicResource> strategicResources;
    private final TechnologyType technologyTypes;


    MilitaryType(int turn, int cost, String combatType, int combatStrength, int rangedCombatStrength, int range
            , int movement, ArrayList<StrategicResource> strategicResources, TechnologyType technologyTypes) {
        this.turn = turn;
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.strategicResources = strategicResources;
        this.technologyTypes = technologyTypes;
    }

    public int getTurn() {
        return turn;
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

    public TechnologyType getTechnologyTypes() {
        return technologyTypes;
    }
}
