package Enum;

import java.util.ArrayList;
import java.util.List;

public enum TechnologyType {
    AGRICULTURE(20, null),
    ANIMAL_HUSBANDRY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE))),
    ARCHERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE))),
    MINING(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE))),
    POTTERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE))),
    BRONZE_WORKING(55, new ArrayList<>(List.of(TechnologyType.MINING))),
    MASONRY(55, new ArrayList<>(List.of(TechnologyType.MINING))),
    THE_WHEEL(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY))),
    TRAPPING(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY))),
    WRITING(55, new ArrayList<>(List.of(TechnologyType.POTTERY))),
    CALENDAR(70, new ArrayList<>(List.of(TechnologyType.POTTERY))),
    CONSTRUCTION(100, new ArrayList<>(List.of(TechnologyType.MASONRY))),
    HORSEBACK_RIDING(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL))),
    MATHEMATICS(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL, TechnologyType.ARCHERY))),
    PHILOSOPHY(100, new ArrayList<>(List.of(TechnologyType.WRITING))),
    IRON_WORKING(150, new ArrayList<>(List.of(TechnologyType.BRONZE_WORKING))),
    METAL_CASTING(240, new ArrayList<>(List.of(TechnologyType.IRON_WORKING))),
    CURRENCY(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS))),
    ENGINEERING(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS, TechnologyType.CONSTRUCTION))),
    THEOLOGY(250, new ArrayList<>(List.of(TechnologyType.CALENDAR, TechnologyType.PHILOSOPHY))),
    CIVIL_SERVICE(400, new ArrayList<>(List.of(TechnologyType.PHILOSOPHY, TechnologyType.TRAPPING))),
    CHIVALRY(440, new ArrayList<>(List.of(TechnologyType.CIVIL_SERVICE, TechnologyType.HORSEBACK_RIDING, TechnologyType.CURRENCY))),
    EDUCATION(440, new ArrayList<>(List.of(TechnologyType.THEOLOGY))),
    MACHINERY(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING))),
    PHYSICS(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING, TechnologyType.METAL_CASTING))),
    STEEL(440, new ArrayList<>(List.of(TechnologyType.METAL_CASTING))),
    ACOUSTICS(650, new ArrayList<>(List.of(TechnologyType.EDUCATION))),
    BANKING(650, new ArrayList<>(List.of(TechnologyType.EDUCATION, TechnologyType.CHIVALRY))),
    PRINTING_PRESS(650, new ArrayList<>(List.of(TechnologyType.MACHINERY, TechnologyType.PHYSICS))),
    GUNPOWDER(680, new ArrayList<>(List.of(TechnologyType.PHYSICS, TechnologyType.STEEL))),
    CHEMISTRY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER))),
    ECONOMICS(900, new ArrayList<>(List.of(TechnologyType.BANKING, TechnologyType.PRINTING_PRESS))),
    METALLURGY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER))),
    ARCHAEOLOGY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS))),
    FERTILIZER(1300, new ArrayList<>(List.of(TechnologyType.CHEMISTRY))),
    MILITARY_SCIENCE(1300, new ArrayList<>(List.of(TechnologyType.ECONOMICS, TechnologyType.CHEMISTRY))),
    SCIENTIFIC_THEORY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS))),
    RIFLING(1425, new ArrayList<>(List.of(TechnologyType.METALLURGY))),
    BIOLOGY(1680, new ArrayList<>(List.of(TechnologyType.ARCHAEOLOGY, TechnologyType.SCIENTIFIC_THEORY))),
    STEAM_POWER(1680, new ArrayList<>(List.of(TechnologyType.SCIENTIFIC_THEORY, TechnologyType.MILITARY_SCIENCE))),
    DYNAMITE(1900, new ArrayList<>(List.of(TechnologyType.FERTILIZER, TechnologyType.RIFLING))),
    ELECTRICITY(1900, new ArrayList<>(List.of(TechnologyType.BIOLOGY, TechnologyType.STEAM_POWER))),
    RAILROAD(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER))),
    REPLACEABLE_PARTS(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER))),
    COMBUSTION(2200, new ArrayList<>(List.of(TechnologyType.REPLACEABLE_PARTS, TechnologyType.RAILROAD, TechnologyType.DYNAMITE))),
    RADIO(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY))),
    TELEGRAPH(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)));

    private final int cost;
    private final ArrayList<TechnologyType> prerequisites;

    TechnologyType(int cost, ArrayList<TechnologyType> prerequisites) {
        this.cost = cost;
        this.prerequisites = prerequisites;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<TechnologyType> getPrerequisites() {
        return prerequisites;
    }
}
