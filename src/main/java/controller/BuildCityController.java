package controller;

import Enum.Message;
import model.City;

public class BuildCityController extends Controller{
    public Message changeProduct(City city, String newProduction) {
        Message message = checkValidationOfUnitName(newProduction); //Maybe should add building too.
        if (message == null) {
            //TODO change product of city to new one and cancel previous one
        }

        return message;
    }

    public Message buildUnit(City city, String unitName) {
        Message message = checkValidationOfUnitName(unitName);
        if (message == null) {
            //TODO start to create it
        }

        return message;
    }

    private Message checkValidationOfUnitName(String unitName) {
        //TODO checking....
        return null; //return it instead of true validation
    }
}
