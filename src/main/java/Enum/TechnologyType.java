package Enum;

import java.util.ArrayList;
import java.util.List;

public enum TechnologyType {
    AGRICULTURE(20, new ArrayList<>(),3),
    ANIMAL_HUSBANDRY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3),
    ARCHERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3),
    MINING(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3),
    POTTERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3),
    BRONZE_WORKING(55, new ArrayList<>(List.of(TechnologyType.MINING)),3),
    MASONRY(55, new ArrayList<>(List.of(TechnologyType.MINING)),3),
    THE_WHEEL(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)),3),
    TRAPPING(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)),3),
    WRITING(55, new ArrayList<>(List.of(TechnologyType.POTTERY)),3),
    CALENDAR(70, new ArrayList<>(List.of(TechnologyType.POTTERY)),3),
    CONSTRUCTION(100, new ArrayList<>(List.of(TechnologyType.MASONRY)),3),
    HORSEBACK_RIDING(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL)),3),
    MATHEMATICS(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL, TechnologyType.ARCHERY)),3),
    PHILOSOPHY(100, new ArrayList<>(List.of(TechnologyType.WRITING)),3),
    IRON_WORKING(150, new ArrayList<>(List.of(TechnologyType.BRONZE_WORKING)),3),
    METAL_CASTING(240, new ArrayList<>(List.of(TechnologyType.IRON_WORKING)),3),
    CURRENCY(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS)),3),
    ENGINEERING(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS, TechnologyType.CONSTRUCTION)),3),
    THEOLOGY(250, new ArrayList<>(List.of(TechnologyType.CALENDAR, TechnologyType.PHILOSOPHY)),3),
    CIVIL_SERVICE(400, new ArrayList<>(List.of(TechnologyType.PHILOSOPHY, TechnologyType.TRAPPING)),3),
    CHIVALRY(440, new ArrayList<>(List.of(TechnologyType.CIVIL_SERVICE, TechnologyType.HORSEBACK_RIDING, TechnologyType.CURRENCY)),3),
    EDUCATION(440, new ArrayList<>(List.of(TechnologyType.THEOLOGY)),3),
    MACHINERY(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING)),3),
    PHYSICS(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING, TechnologyType.METAL_CASTING)),3),
    STEEL(440, new ArrayList<>(List.of(TechnologyType.METAL_CASTING)),3),
    ACOUSTICS(650, new ArrayList<>(List.of(TechnologyType.EDUCATION)),3),
    BANKING(650, new ArrayList<>(List.of(TechnologyType.EDUCATION, TechnologyType.CHIVALRY)),3),
    PRINTING_PRESS(650, new ArrayList<>(List.of(TechnologyType.MACHINERY, TechnologyType.PHYSICS)),3),
    GUNPOWDER(680, new ArrayList<>(List.of(TechnologyType.PHYSICS, TechnologyType.STEEL)),3),
    CHEMISTRY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)),3),
    ECONOMICS(900, new ArrayList<>(List.of(TechnologyType.BANKING, TechnologyType.PRINTING_PRESS)),3),
    METALLURGY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)),3),
    ARCHAEOLOGY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)),3),
    FERTILIZER(1300, new ArrayList<>(List.of(TechnologyType.CHEMISTRY)),3),
    MILITARY_SCIENCE(1300, new ArrayList<>(List.of(TechnologyType.ECONOMICS, TechnologyType.CHEMISTRY)),3),
    SCIENTIFIC_THEORY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)),3),
    RIFLING(1425, new ArrayList<>(List.of(TechnologyType.METALLURGY)),3),
    BIOLOGY(1680, new ArrayList<>(List.of(TechnologyType.ARCHAEOLOGY, TechnologyType.SCIENTIFIC_THEORY)),3),
    STEAM_POWER(1680, new ArrayList<>(List.of(TechnologyType.SCIENTIFIC_THEORY, TechnologyType.MILITARY_SCIENCE)),3),
    DYNAMITE(1900, new ArrayList<>(List.of(TechnologyType.FERTILIZER, TechnologyType.RIFLING)),3),
    ELECTRICITY(1900, new ArrayList<>(List.of(TechnologyType.BIOLOGY, TechnologyType.STEAM_POWER)),3),
    RAILROAD(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)),3),
    REPLACEABLE_PARTS(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)),3),
    COMBUSTION(2200, new ArrayList<>(List.of(TechnologyType.REPLACEABLE_PARTS, TechnologyType.RAILROAD, TechnologyType.DYNAMITE)),3),
    RADIO(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)),3),
    TELEGRAPH(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)),3);

    private final int cost;
    private transient final ArrayList<TechnologyType> prerequisites;
    private final int time;

    TechnologyType(int cost, ArrayList<TechnologyType> prerequisites,int time) {
        this.cost = cost;
        this.prerequisites = prerequisites;
        this.time=time;
    }

    public int getCost() {
        return cost;
    }

    public ArrayList<TechnologyType> getPrerequisites() {
        return prerequisites;
    }

    public int getTime() {
        return time;
    }
}
