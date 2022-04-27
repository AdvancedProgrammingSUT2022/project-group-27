package controller;

import model.Citizen;
import model.City;
import model.Ground;
import Enum.Message;

public class CityMenuController extends Controller{
    public Message lockCitizenToGround(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) return Message.INVALID_GROUND_NUMBER;
        else if (!city.isThisGroundInThisCityRange(ground)) return Message.GROUND_NOT_IN_CITY;
        else {
            Citizen citizen = Citizen.isAnyCitizensWithoutWork(city);
            if (citizen == null) return Message.NO_CITIZEN_WITHOUT_WORK;
            //TODO locking citizen to ground
            return Message.SUCCESS_WORK;
        }
    }
}
