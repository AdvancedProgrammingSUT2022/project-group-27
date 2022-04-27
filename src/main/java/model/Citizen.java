package model;

public class Citizen {
    private boolean dontHaveWork = false;

    public static Citizen isAnyCitizensWithoutWork(City city) {
        for (Citizen citizen: city.getListOfCitizens()) {
            if (!citizen.dontHaveWork) return citizen;
        }

        return null;
    }
}
