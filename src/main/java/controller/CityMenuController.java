package controller;

import model.Citizen;
import model.City;
import model.Ground;
import Enum.Message;
import model.Unit;

import java.util.ArrayList;

public class CityMenuController extends CityController{
    public Message lockCitizenToGround(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        Message message = isGroundValidForCity(city, ground);
        if (message == null) {
            ArrayList<Citizen> listOfWithoutWork = city.withoutWorkCitizens();
            if (listOfWithoutWork.size() == 0) return Message.NO_CITIZEN_WITHOUT_WORK;

            boolean canSet = listOfWithoutWork.get(0).setGround(ground);
            if (!canSet) return Message.IS_WORK_GROUND;
            message = Message.SUCCESS_WORK;
        }

        return message;
    }

    public Message removeFromWork(City city, int groundNumber) {
        Ground ground = Ground.getGroundByNumber(groundNumber);
        Message message = isGroundValidForCity(city, ground);
        if (message == null) {
            Citizen citizen = city.isAnyoneWorkOnGround(ground);
            if (citizen == null) return Message.NO_CITIZEN_ON_GROUND;
            citizen.removeFromGround();
            message = Message.SUCCESS_WORK;
        }

        return message;
    }

    public Message buyGround(City city, int groundNumber) {
        if (groundNumber == -1) return Message.BACKING_TO_PREVIOUS_MENU;

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
        Unit unit = checkValidationOfUnitName(city, whatPlayerWantToBuy);
        if (unit != null) {
            if (city.getPlayer().getGold() < unit.getCost()) return Message.NOT_ENOUGH_MONEY;

            city.getPlayer().giveMoneyForBuying(unit.getCost());
            city.getListOfUnitsInCity().add(unit);
            return Message.SUCCESS_WORK;
        } else return Message.INVALID_UNIT_NAME;
    }

    private Message isGroundValidForCity(City city, Ground ground) {
        if (ground == null) return Message.INVALID_GROUND_NUMBER;
        else if (!city.isThisGroundInThisCityRange(ground)) return Message.GROUND_NOT_IN_CITY;
        else return null;
    }
}
