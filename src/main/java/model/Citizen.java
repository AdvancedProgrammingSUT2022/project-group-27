package model;

import java.util.ArrayList;

public class Citizen {
    private boolean haveWork = false;

    public static Citizen isAnyCitizensWithoutWork(City city) {
        for (Citizen citizen: city.getListOfCitizens()) {
            if (!citizen.haveWork) return citizen;
        }

        return null;
    }

    public static ArrayList<Citizen> withoutWorkCitizens(City city) {
        ArrayList<Citizen> listOfWithoutWork = new ArrayList<>();
        for (Citizen citizen: city.getListOfCitizens()) {
            if (!citizen.haveWork) listOfWithoutWork.add(citizen);
        }

        return listOfWithoutWork;
    }
}
