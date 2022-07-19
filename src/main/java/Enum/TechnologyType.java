package Enum;

import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;

import java.util.ArrayList;
import java.util.List;

public enum TechnologyType {
    AGRICULTURE(20, new ArrayList<>(),3, GlobalVariables.getImagePattern("/technology/agriculture.png")),
    ANIMAL_HUSBANDRY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3, GlobalVariables.getImagePattern("/technology/animal_husbandry.png")),
    ARCHERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3, GlobalVariables.getImagePattern("/technology/archery.png")),
    MINING(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3, GlobalVariables.getImagePattern("/technology/mining.png")),
    POTTERY(35, new ArrayList<>(List.of(TechnologyType.AGRICULTURE)),3, GlobalVariables.getImagePattern("/technology/pottery.png")),
    BRONZE_WORKING(55, new ArrayList<>(List.of(TechnologyType.MINING)),3, GlobalVariables.getImagePattern("/technology/bronze_working.png")),
    MASONRY(55, new ArrayList<>(List.of(TechnologyType.MINING)),3, GlobalVariables.getImagePattern("/technology/masonry.png")),
    THE_WHEEL(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)),3, GlobalVariables.getImagePattern("/technology/the_wheel.png")),
    TRAPPING(55, new ArrayList<>(List.of(TechnologyType.ANIMAL_HUSBANDRY)),3, GlobalVariables.getImagePattern("/technology/trapping.png")),
    WRITING(55, new ArrayList<>(List.of(TechnologyType.POTTERY)),3, GlobalVariables.getImagePattern("/technology/writing.png")),
    CALENDAR(70, new ArrayList<>(List.of(TechnologyType.POTTERY)),3, GlobalVariables.getImagePattern("/technology/calendar.png")),
    CONSTRUCTION(100, new ArrayList<>(List.of(TechnologyType.MASONRY)),3, GlobalVariables.getImagePattern("/technology/construction.png")),
    HORSEBACK_RIDING(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL)),3, GlobalVariables.getImagePattern("/technology/horseback_riding.png")),
    MATHEMATICS(100, new ArrayList<>(List.of(TechnologyType.THE_WHEEL, TechnologyType.ARCHERY)),3, GlobalVariables.getImagePattern("/technology/mathematics.png")),
    PHILOSOPHY(100, new ArrayList<>(List.of(TechnologyType.WRITING)),3, GlobalVariables.getImagePattern("/technology/philosophy.png")),
    IRON_WORKING(150, new ArrayList<>(List.of(TechnologyType.BRONZE_WORKING)),3, GlobalVariables.getImagePattern("/technology/iron_working.png")),
    METAL_CASTING(240, new ArrayList<>(List.of(TechnologyType.IRON_WORKING)),3, GlobalVariables.getImagePattern("/technology/metal_casting.png")),
    CURRENCY(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS)),3, GlobalVariables.getImagePattern("/technology/currency.png")),
    ENGINEERING(250, new ArrayList<>(List.of(TechnologyType.MATHEMATICS, TechnologyType.CONSTRUCTION)),3, GlobalVariables.getImagePattern("/technology/engineering.png")),
    THEOLOGY(250, new ArrayList<>(List.of(TechnologyType.CALENDAR, TechnologyType.PHILOSOPHY)),3, GlobalVariables.getImagePattern("/technology/theology.png")),
    CIVIL_SERVICE(400, new ArrayList<>(List.of(TechnologyType.PHILOSOPHY, TechnologyType.TRAPPING)),3, GlobalVariables.getImagePattern("/technology/civil_service.png")),
    CHIVALRY(440, new ArrayList<>(List.of(TechnologyType.CIVIL_SERVICE, TechnologyType.HORSEBACK_RIDING, TechnologyType.CURRENCY)),3, GlobalVariables.getImagePattern("/technology/chivalry.png")),
    EDUCATION(440, new ArrayList<>(List.of(TechnologyType.THEOLOGY)),3, GlobalVariables.getImagePattern("/technology/education.png")),
    MACHINERY(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING)),3, GlobalVariables.getImagePattern("/technology/machinery.png")),
    PHYSICS(440, new ArrayList<>(List.of(TechnologyType.ENGINEERING, TechnologyType.METAL_CASTING)),3, GlobalVariables.getImagePattern("/technology/physics.png")),
    STEEL(440, new ArrayList<>(List.of(TechnologyType.METAL_CASTING)),3, GlobalVariables.getImagePattern("/technology/steel.png")),
    ACOUSTICS(650, new ArrayList<>(List.of(TechnologyType.EDUCATION)),3, GlobalVariables.getImagePattern("/technology/acoustics.png")),
    BANKING(650, new ArrayList<>(List.of(TechnologyType.EDUCATION, TechnologyType.CHIVALRY)),3, GlobalVariables.getImagePattern("/technology/banking.png")),
    PRINTING_PRESS(650, new ArrayList<>(List.of(TechnologyType.MACHINERY, TechnologyType.PHYSICS)),3, GlobalVariables.getImagePattern("/technology/printing-press.png")),
    GUNPOWDER(680, new ArrayList<>(List.of(TechnologyType.PHYSICS, TechnologyType.STEEL)),3, GlobalVariables.getImagePattern("/technology/gunpowder.png")),
    CHEMISTRY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)),3, GlobalVariables.getImagePattern("/technology/chemistry.png")),
    ECONOMICS(900, new ArrayList<>(List.of(TechnologyType.BANKING, TechnologyType.PRINTING_PRESS)),3, GlobalVariables.getImagePattern("/technology/economics.png")),
    METALLURGY(900, new ArrayList<>(List.of(TechnologyType.GUNPOWDER)),3, GlobalVariables.getImagePattern("/technology/metallurgy.png")),
    ARCHAEOLOGY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)),3, GlobalVariables.getImagePattern("/technology/archaeology.png")),
    FERTILIZER(1300, new ArrayList<>(List.of(TechnologyType.CHEMISTRY)),3, GlobalVariables.getImagePattern("/technology/fertilizer.png")),
    MILITARY_SCIENCE(1300, new ArrayList<>(List.of(TechnologyType.ECONOMICS, TechnologyType.CHEMISTRY)),3, GlobalVariables.getImagePattern("/technology/military_science.png")),
    SCIENTIFIC_THEORY(1300, new ArrayList<>(List.of(TechnologyType.ACOUSTICS)),3, GlobalVariables.getImagePattern("/technology/scientific_theory.png")),
    RIFLING(1425, new ArrayList<>(List.of(TechnologyType.METALLURGY)),3, GlobalVariables.getImagePattern("/technology/rifling.png")),
    BIOLOGY(1680, new ArrayList<>(List.of(TechnologyType.ARCHAEOLOGY, TechnologyType.SCIENTIFIC_THEORY)),3, GlobalVariables.getImagePattern("/technology/biology.png")),
    STEAM_POWER(1680, new ArrayList<>(List.of(TechnologyType.SCIENTIFIC_THEORY, TechnologyType.MILITARY_SCIENCE)),3, GlobalVariables.getImagePattern("/technology/steam_power.png")),
    DYNAMITE(1900, new ArrayList<>(List.of(TechnologyType.FERTILIZER, TechnologyType.RIFLING)),3, GlobalVariables.getImagePattern("/technology/dynamite.png")),
    ELECTRICITY(1900, new ArrayList<>(List.of(TechnologyType.BIOLOGY, TechnologyType.STEAM_POWER)),3, GlobalVariables.getImagePattern("/technology/electricity.png")),
    RAILROAD(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)),3, GlobalVariables.getImagePattern("/technology/railroad.png")),
    REPLACEABLE_PARTS(1900, new ArrayList<>(List.of(TechnologyType.STEAM_POWER)),3, GlobalVariables.getImagePattern("/technology/replaceable_parts.png")),
    COMBUSTION(2200, new ArrayList<>(List.of(TechnologyType.REPLACEABLE_PARTS, TechnologyType.RAILROAD, TechnologyType.DYNAMITE)),3, GlobalVariables.getImagePattern("/technology/combustion.png")),
    RADIO(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)),3, GlobalVariables.getImagePattern("/technology/radio.png")),
    TELEGRAPH(2200, new ArrayList<>(List.of(TechnologyType.ELECTRICITY)),3, GlobalVariables.getImagePattern("/technology/telegraph.png"));

    private final int cost;
    private transient final ArrayList<TechnologyType> prerequisites;
    private final int time;
    private final ImagePattern image;

    TechnologyType(int cost, ArrayList<TechnologyType> prerequisites,int time, ImagePattern image) {
        this.cost = cost;
        this.prerequisites = prerequisites;
        this.time=time;
        this.image = image;
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
