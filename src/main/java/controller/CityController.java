package controller;

import Enum.*;
import model.*;

public class CityController extends Controller{
    protected Unit checkValidationOfUnitName(City city, String unitName) {
        MilitaryType unitType = null;
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (canWeHaveThisUnitType(militaryType, city)) {
                if (militaryType.name().equals(unitName)) {
                    if (militaryType.name().equals("SETTLER") && city.getOwner().getHappiness() < 0)
                        break;
                    unitType = militaryType;
                    break;
                }
            }
        }

        if (unitType == null) return null;
        if (unitType == MilitaryType.WORKER) return new Worker(city.getGround(), city.getOwner());
        if (unitType == MilitaryType.SETTLER) return new SettlerUnit(city.getGround(), city.getOwner());
        if (unitType.getCombatType().matches("Archery|Mounted|Siege")) return new RangedUnit(city.getGround(),
                city.getOwner(), unitType);
        if (unitType.getCombatType().matches("Recon|Melee|Gunpowder|Armored")) return new MeleeUnit(city.getGround(),
                city.getOwner(), unitType);
        return null;
    }

    public boolean canWeHaveThisUnitType(MilitaryType militaryType, City city) {
        return city.getOwner().doWeHaveThisTechnology(militaryType.getTechnologyTypes()) &&
                (militaryType.getStrategicResources().size() == 0 ||
                        city.getOwner().hasStrategicResource(militaryType.getStrategicResources().get(0)));
    }

    public boolean canWeHaveThisBuildingType(BuildingsType buildingsType, City city) {
        return city.getOwner().doWeHaveThisTechnology(buildingsType.getTechnologyType());
    }

    protected Building checkValidationOfBuildingName(City city, String buildingName) {
        BuildingsType building = null;
        for (BuildingsType buildingsType: BuildingsType.values()) {
            if (canWeHaveThisBuildingType(buildingsType, city) && shouldBreakByNotes(city, buildingsType)) {
                if (buildingsType.name().equals(buildingName)) {
                    building = buildingsType;
                    break;
                }
            }
        }

        if (building != null) return new Building(building, city);
        else return null;
    }

    private boolean shouldBreakByNotes(City city, BuildingsType buildingsType) {
        //Handling notes
        if (buildingsType.name().equals(BuildingsType.STOCK_EXCHANGE.name()) &&
                !(city.doWeHaveThisBuilding(BuildingsType.BANK) || city.doWeHaveThisBuilding(BuildingsType.SATRAPS_COURT))) return false;

        if (buildingsType.name().equals(BuildingsType.MILITARY_BASE.name()) && !(city.doWeHaveThisBuilding(BuildingsType.CASTLE))) return false;

        if (buildingsType.name().equals(BuildingsType.FACTORY.name()) && !(city.doWeHaveThisStrategicResource(StrategicResource.COAL))) return false;

        if (buildingsType.name().equals(BuildingsType.BROADCAST_TOWER.name()) && !(city.doWeHaveThisBuilding(BuildingsType.MUSEUM))) return false;

        if (buildingsType.name().equals(BuildingsType.ARSENAL.name()) && !(city.doWeHaveThisBuilding(BuildingsType.MILITARY_ACADEMY))) return false;

        if (buildingsType.name().equals(BuildingsType.WINDMILL.name()) && city.getGround().getGroundType().equals(GroundType.HILL)) return false;

        if (buildingsType.name().equals(BuildingsType.THEATER.name()) && !(city.doWeHaveThisBuilding(BuildingsType.COLOSSEUM))) return false;

        if (buildingsType.name().equals(BuildingsType.SATRAPS_COURT.name()) && !(city.doWeHaveThisBuilding(BuildingsType.MARKET))) return false;

        if (buildingsType.name().equals(BuildingsType.PUBLIC_SCHOOL.name()) && !(city.doWeHaveThisBuilding(BuildingsType.UNIVERSITY))) return false;

        if (buildingsType.name().equals(BuildingsType.OPERA_HOUSE.name()) &&
                !(city.doWeHaveThisBuilding(BuildingsType.TEMPLE) && city.doWeHaveThisBuilding(BuildingsType.BURIAL_TOMB))) return false;

        if (buildingsType.name().equals(BuildingsType.MUSEUM.name()) && !(city.doWeHaveThisBuilding(BuildingsType.OPERA_HOUSE))) return false;

        if (buildingsType.name().equals(BuildingsType.MILITARY_ACADEMY.name()) && !(city.doWeHaveThisBuilding(BuildingsType.BARRACKS))) return false;

        if (buildingsType.name().equals(BuildingsType.BANK.name()) && !(city.doWeHaveThisBuilding(BuildingsType.MARKET))) return false;

        if (buildingsType.name().equals(BuildingsType.UNIVERSITY.name()) && !(city.doWeHaveThisBuilding(BuildingsType.LIBRARY))) return false;

        if (buildingsType.name().equals(BuildingsType.GARDEN.name()) && !(city.getGround().containRiver())) return false;

        if (buildingsType.name().equals(BuildingsType.FORGE.name()) && !(city.doWeHaveThisStrategicResource(StrategicResource.IRON))) return false;

        if (buildingsType.name().equals(BuildingsType.CASTLE.name()) && !(city.doWeHaveThisBuilding(BuildingsType.WALLS))) return false;

        if (buildingsType.name().equals(BuildingsType.TEMPLE.name()) && !(city.doWeHaveThisBuilding(BuildingsType.MONUMENT))) return false;

        if (buildingsType.name().equals(BuildingsType.STABLE.name()) && !(city.doWeHaveThisStrategicResource(StrategicResource.HORSE))) return false;

        if (buildingsType.name().equals(BuildingsType.CIRCUS.name()) &&
                !(city.doWeHaveThisStrategicResource(StrategicResource.HORSE) || city.doWeHaveThisLuxuryResource(LuxuryResource.IVORY))) return false;

        if (buildingsType.name().equals(BuildingsType.ARMORY.name()) && !(city.doWeHaveThisBuilding(BuildingsType.BARRACKS))) return false;

        if (buildingsType.name().equals(BuildingsType.WATER_MILL.name()) && !(city.getGround().containRiver())) return false;
        return true;
    }
}
