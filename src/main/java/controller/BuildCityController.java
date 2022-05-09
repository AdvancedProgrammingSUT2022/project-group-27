package controller;

import Enum.Message;
import model.City;
import model.Unit;

public class BuildCityController extends CityController{
    public Message changeConstruction(City city, String newProduction) {
        Unit unit = checkValidationOfUnitName(city, newProduction); //Maybe should add building too.
        if (unit != null) {
            city.changeConstruction(unit);
            return Message.SUCCESS_WORK;
        } else return Message.INVALID_UNIT_NAME;
    }

    public Message buildUnit(City city, String unitName) {
        if (city.getConstruction() != null) return Message.PRODUCTION_IS_ON_PROGRESS;
        Unit unit = checkValidationOfUnitName(city, unitName);
        if (unit != null) {
            unit.setTurnRemainedToCompleted((unit.getCost() + city.getProduction() - 1) / city.getProduction());
            city.getListOfUnitsInCity().add(unit);
            return Message.SUCCESS_WORK;
        } else return Message.INVALID_UNIT_NAME;
    }
}
