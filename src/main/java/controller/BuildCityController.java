package controller;

import Enum.Message;
import model.City;
import model.Unit;

public class BuildCityController extends CityController{
    public Message changeProduct(City city, String newProduction) {
        Unit unit = checkValidationOfUnitName(city, newProduction); //Maybe should add building too.
        if (unit != null) {
            //TODO change product of city to new one and cancel previous one
            return Message.SUCCESS_WORK;
        } else return Message.INVALID_UNIT_NAME;
    }

    public Message buildUnit(City city, String unitName) {
        Unit unit = checkValidationOfUnitName(city, unitName);
        if (unit != null) {
            if (city.getGold() < unit.getCost()) return Message.NOT_ENOUGH_MONEY;
            city.getListOfUnitsInCity().add(unit);
            return Message.SUCCESS_WORK;
        } else return Message.INVALID_UNIT_NAME;
    }
}
