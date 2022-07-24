package Enum;

import javafx.scene.paint.ImagePattern;
import model.GlobalVariables;

public enum BuildingsType {
    BARRACKS(80, 1, TechnologyType.BRONZE_WORKING, GlobalVariables.getImagePattern("/images/buildings/BARRACKS.png")),
    GRANARY(100, 1, TechnologyType.POTTERY, GlobalVariables.getImagePattern("/images/buildings/GRANARY.png")),
    LIBRARY(80, 1, TechnologyType.WRITING, GlobalVariables.getImagePattern("/images/buildings/LIBRARY.png")),
    MONUMENT(60, 1, null, GlobalVariables.getImagePattern("/images/buildings/MONUMENT.png")),
    WALLS(100, 1, TechnologyType.MASONRY, GlobalVariables.getImagePattern("/images/buildings/WALLS.png")),
    WATER_MILL(120, 2, TechnologyType.THE_WHEEL, GlobalVariables.getImagePattern("/images/buildings/WATER_MILL.png")),
    ARMORY(130, 3, TechnologyType.IRON_WORKING, GlobalVariables.getImagePattern("/images/buildings/ARMORY.png")),
    BURIAL_TOMB(120, 0, TechnologyType.PHILOSOPHY, GlobalVariables.getImagePattern("/images/buildings/BURIAL_TOMB.png")),
    CIRCUS(150, 3, TechnologyType.HORSEBACK_RIDING, GlobalVariables.getImagePattern("/images/buildings/CIRCUS.png")),
    COLOSSEUM(150, 3, TechnologyType.CONSTRUCTION, GlobalVariables.getImagePattern("/images/buildings/COLOSSEUM.png")),
    COURTHOUSE(200, 5, TechnologyType.MATHEMATICS, GlobalVariables.getImagePattern("/images/buildings/COURTHOUSE.png")),
    STABLE(100, 1, TechnologyType.HORSEBACK_RIDING, GlobalVariables.getImagePattern("/images/buildings/STABLE.png")),
    TEMPLE(120, 2, TechnologyType.PHILOSOPHY, GlobalVariables.getImagePattern("/images/buildings/TEMPLE.png")),
    CASTLE(200, 3, TechnologyType.CHIVALRY, GlobalVariables.getImagePattern("/images/buildings/CASTLE.png")),
    FORGE(150, 2, TechnologyType.METAL_CASTING, GlobalVariables.getImagePattern("/images/buildings/FORGE.png")),
    GARDEN(120, 2, TechnologyType.THEOLOGY, GlobalVariables.getImagePattern("/images/buildings/GARDEN.png")),
    MARKET(120, 0, TechnologyType.CURRENCY, GlobalVariables.getImagePattern("/images/buildings/MARKET.png")),
    MINT(120, 0, TechnologyType.CURRENCY, GlobalVariables.getImagePattern("/images/buildings/MINT.png")),
    MONASTERY(120, 2, TechnologyType.THEOLOGY, GlobalVariables.getImagePattern("/images/buildings/MONASTERY.png")),
    UNIVERSITY(200, 3, TechnologyType.EDUCATION, GlobalVariables.getImagePattern("/images/buildings/UNIVERSITY.png")),
    WORKSHOP(100, 2, TechnologyType.METAL_CASTING, GlobalVariables.getImagePattern("/images/buildings/WORKSHOP.png")),
    BANK(220, 0, TechnologyType.BANKING, GlobalVariables.getImagePattern("/images/buildings/BANK.png")),
    MILITARY_ACADEMY(350, 3, TechnologyType.MILITARY_SCIENCE, GlobalVariables.getImagePattern("/images/buildings/MILITARY_ACADEMY.png")),
    MUSEUM(350, 3, TechnologyType.ARCHAEOLOGY, GlobalVariables.getImagePattern("/images/buildings/MUSEUM.png")),
    OPERA_HOUSE(220, 3, TechnologyType.ACOUSTICS, GlobalVariables.getImagePattern("/images/buildings/OPERA_HOUSE.png")),
    PUBLIC_SCHOOL(350, 3, TechnologyType.SCIENTIFIC_THEORY, GlobalVariables.getImagePattern("/images/buildings/PUBLIC_SCHOOL.png")),
    SATRAPS_COURT(220, 0, TechnologyType.BANKING, GlobalVariables.getImagePattern("/images/buildings/SATRAPS_COURT.png")),
    THEATER(300, 5, TechnologyType.PRINTING_PRESS, GlobalVariables.getImagePattern("/images/buildings/THEATER.png")),
    WINDMILL(180, 2, TechnologyType.ECONOMICS, GlobalVariables.getImagePattern("/images/buildings/WINDMILL.png")),
    ARSENAL(350, 3, TechnologyType.RAILROAD, GlobalVariables.getImagePattern("/images/buildings/ARSENAL.png")),
    BROADCAST_TOWER(600, 3, TechnologyType.RADIO, GlobalVariables.getImagePattern("/images/buildings/BROADCAST_TOWER.png")),
    FACTORY(300, 3, TechnologyType.STEAM_POWER, GlobalVariables.getImagePattern("/images/buildings/FACTORY.png")),
    HOSPITAL(400, 2, TechnologyType.BIOLOGY, GlobalVariables.getImagePattern("/images/buildings/HOSPITAL.png")),
    MILITARY_BASE(450, 4, TechnologyType.TELEGRAPH, GlobalVariables.getImagePattern("/images/buildings/MILITARY_BASE.png")),
    STOCK_EXCHANGE(650, 0, TechnologyType.ELECTRICITY, GlobalVariables.getImagePattern("/images/buildings/STOCK_EXCHANGE.png"));

    private final int cost;
    private final int maintenance;
    private final TechnologyType technologyType;
    private final ImagePattern image;

    BuildingsType(int cost, int maintenance, TechnologyType technologyType, ImagePattern image) {
        this.cost = cost;
        this.maintenance = maintenance;
        this.technologyType = technologyType;
        this.image = image;
    }

    public ImagePattern getImage() {
        return image;
    }

    public TechnologyType getTechnologyType() {
        return technologyType;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }
}
