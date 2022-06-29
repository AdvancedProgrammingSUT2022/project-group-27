package controller;

import Enum.*;
import model.*;

public class CityController extends Controller{
    protected Unit checkValidationOfUnitName(City city, String unitName) {
        MilitaryType unitType = null;
        for (MilitaryType militaryType: MilitaryType.values()) {
            if (canWeHaveThisUnitType(militaryType, city)) {
                if (militaryType.name().equals(unitName)) {
                    if (militaryType.name().equals("SETTLER") && city.getPlayer().getHappiness() < 0)
                        break;
                    unitType = militaryType;
                    break;
                }
            }
        }

        if (unitType == null) return null;
        if (unitType == MilitaryType.WORKER) return new Worker(city.getGround(), city.getPlayer());
        if (unitType == MilitaryType.SETTLER) return new SettlerUnit(city.getGround(), city.getPlayer());
        if (unitType.getCombatType().matches("Archery|Mounted|Siege")) return new RangedUnit(city.getGround(),
                city.getPlayer(), unitType);
        if (unitType.getCombatType().matches("Recon|Melee|Gunpowder|Armored")) return new MeleeUnit(city.getGround(),
                city.getPlayer(), unitType);
        return null;
    }

    public boolean canWeHaveThisUnitType(MilitaryType militaryType, City city) {
        return city.getPlayer().doWeHaveThisTechnology(militaryType.getTechnologyTypes()) &&
                (militaryType.getStrategicResources().size() == 0 ||
                        city.getPlayer().hasStrategicResource(militaryType.getStrategicResources().get(0)));
    }

    public boolean canWeHaveThisBuildingType(BuildingsType buildingsType, City city) {
        return city.getPlayer().doWeHaveThisTechnology(buildingsType.getTechnologyType());
    }

    protected Building checkValidationOfBuildingName(City city, String buildingName) {
        BuildingsType building = null;
        for (BuildingsType buildingsType: BuildingsType.values()) {
            if (canWeHaveThisBuildingType(buildingsType, city)) {
                if (buildingsType.name().equals(buildingName)) {
                    building = buildingsType;
                    break;
                }
            }
        }

        if (building != null) return new Building(building, city);
        else return null;
    }
}
