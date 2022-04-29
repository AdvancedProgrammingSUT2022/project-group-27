package controller;

import model.Citizen;
import model.City;
import model.Ground;
import Enum.Message;

public class CityMenuController extends Controller{
    public Message lockCitizenToGround(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        Message message = isGroundValidForCity(city, ground);
        if (message == null) {
            Citizen citizen = Citizen.isAnyCitizensWithoutWork(city);
            if (citizen == null) return Message.NO_CITIZEN_WITHOUT_WORK;
            //TODO locking citizen to ground
            message = Message.SUCCESS_WORK;
        }

        return message;
    }

    public Message removeFromWork(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        Message message = isGroundValidForCity(city, ground);
        if (message == null) {
            //TODO check if any citizens are there
            //TODO remove citizen from ground and add to withoutWork citizens
            message = Message.SUCCESS_WORK;
        }

        return message;
    }

    public Message buyGround(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        if (ground == null) return Message.INVALID_GROUND_NUMBER;
        else if (!city.isThisGroundNearThisCity(ground)) return Message.GROUND_NOT_NEAR_CITY;
        else {
            if (!city.getPlayer().haveEnoughMoney(ground.getCost())) return Message.NOT_ENOUGH_MONEY;

            city.getPlayer().giveMoneyForBuying(ground.getCost());
            city.addGroundToRangeOfCity(ground);
            return Message.SUCCESS_WORK;
        }
    }

    public Message buyThings(City city, String whatPlayerWantToBuy) {
        //TODO check validation of string and then money and then buy it for player
        return Message.SUCCESS_WORK;
    }

    private Message isGroundValidForCity(City city, Ground ground) {
        if (ground == null) return Message.INVALID_GROUND_NUMBER;
        else if (!city.isThisGroundInThisCityRange(ground)) return Message.GROUND_NOT_IN_CITY;
        else return null;
    }
}
