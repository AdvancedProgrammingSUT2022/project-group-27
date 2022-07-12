package Enum;

import controller.ProfileController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

public enum MilitaryType {
    WORKER(70, "Civilian", 0, 0, 0, 2,
            new ArrayList<StrategicResource>(), null, GlobalVariables.getImagePattern("/images/units/WORKER.png")),
    SETTLER(89, "Civilian", 0, 0, 0, 2,
            new ArrayList<StrategicResource>(), null, new ImagePattern(new Image(ProfileController.class.getResource(GlobalVariables.SETTLER).toExternalForm()))),
    ARCHER(70, "Archery", 4, 6, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.ARCHERY, GlobalVariables.getImagePattern("/images/units/ARCHER.png")),
    CHARIOTARCHER(60, "Mounted", 3, 6, 2, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.THE_WHEEL, GlobalVariables.getImagePattern("/images/units/CHARIOT_ARCHER.png")),
    SCOUT(25, "Recon", 4, 0, 0, 2,
            new ArrayList<StrategicResource>(), null, GlobalVariables.getImagePattern("/images/units/SCOUT.png")),
    SPEARMAN(50, "Melee", 7, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.BRONZE_WORKING, GlobalVariables.getImagePattern("/images/units/SPEARMAN.png")),
    WARRIOR(40, "Melee", 6, 0, 0, 2,
            new ArrayList<StrategicResource>(), null, GlobalVariables.getImagePattern("/images/units/SETTLER.png")),
    CATAPULT(100, "Siege", 4, 14, 2, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.MATHEMATICS, GlobalVariables.getImagePattern("/images/units/CATAPULT.png")),
    HORSEMAN(80, "Mounted", 12, 0, 0, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.HORSEBACK_RIDING, GlobalVariables.getImagePattern("/images/units/HORSEMAN.png")),
    SWORDSMAN(80, "Melee", 11, 0, 0, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.IRON_WORKING, GlobalVariables.getImagePattern("/images/units/WORKER.png")),
    CROSSBOWMAN(120, "Archery", 6, 12, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.MACHINERY, GlobalVariables.getImagePattern("/images/units/CROSSBOWMAN.png")),
    KNIGHT(150, "Mounted", 18, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.CHIVALRY, GlobalVariables.getImagePattern("/images/units/KNIGHT.png")),
    LONGSWORDSMAN(150, "Melee", 18, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.STEEL, GlobalVariables.getImagePattern("/images/units/LONGSWORDSMAN.png")),
    PIKEMAN(100, "Melee", 10, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.CIVIL_SERVICE, GlobalVariables.getImagePattern("/images/units/PIKEMAN.png")),
    TREBUCHET(170, "Siege", 6, 20, 2, 2,
            new ArrayList<StrategicResource>(List.of(StrategicResource.IRON)), TechnologyType.PHYSICS, GlobalVariables.getImagePattern("/images/units/TREBUCHET.png")),
    CANON(250, "Siege", 10, 26, 2, 2,
            new ArrayList<StrategicResource>(), TechnologyType.CHEMISTRY, GlobalVariables.getImagePattern("/images/units/CANNON.png")),
    CAVALRY(260, "Mounted", 25, 0, 0, 3,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.MILITARY_SCIENCE, GlobalVariables.getImagePattern("/images/units/CAVALRY.png")),
    LANCER(220, "Mounted", 22, 0, 0, 4,
            new ArrayList<StrategicResource>(List.of(StrategicResource.HORSE)), TechnologyType.METALLURGY, GlobalVariables.getImagePattern("/images/units/LANCER.png")),
    MUSKETMAN(120, "Gunpowder", 16, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.GUNPOWDER, GlobalVariables.getImagePattern("/images/units/MUSKETMAN.png")),
    RIFLEMAN(200, "Gunpowder", 25, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.RIFLING, GlobalVariables.getImagePattern("/images/units/RIFLEMAN.png")),
    ANTITANKGUN(300, "Gunpowder", 32, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.REPLACEABLE_PARTS, GlobalVariables.getImagePattern("/images/units/ANTITANKGUN.png")),
    ARTILLERY(420, "Siege", 16, 32, 3, 2,
            new ArrayList<StrategicResource>(), TechnologyType.DYNAMITE, GlobalVariables.getImagePattern("/images/units/ARTILLERY.png")),
    INFANTRY(300, "Gunpowder", 36, 0, 0, 2,
            new ArrayList<StrategicResource>(), TechnologyType.REPLACEABLE_PARTS, GlobalVariables.getImagePattern("/images/units/INFANTRY.png")),
    PANZER(450, "Armored", 60, 0, 0, 5,
            new ArrayList<StrategicResource>(), TechnologyType.COMBUSTION, GlobalVariables.getImagePattern("/images/units/PANZER.png")),
    TANK(450, "Armored", 50, 0, 0, 4,
            new ArrayList<StrategicResource>(), TechnologyType.COMBUSTION, GlobalVariables.getImagePattern("/images/units/TANK.png"));

    private final int cost, combatStrength, rangedCombatStrength, range, movement;
    private final String combatType;
    private final ArrayList<StrategicResource> strategicResources;
    private final TechnologyType technologyTypes;
    private final ImagePattern image;


    MilitaryType(int cost, String combatType, int combatStrength, int rangedCombatStrength, int range
            , int movement, ArrayList<StrategicResource> strategicResources, TechnologyType technologyTypes, ImagePattern image) {
        this.cost = cost;
        this.combatType = combatType;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.strategicResources = strategicResources;
        this.technologyTypes = technologyTypes;
        this.image = image;
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

    public ImagePattern getImage() {
        return image;
    }
}
