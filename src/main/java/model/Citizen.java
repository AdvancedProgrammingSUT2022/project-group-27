package model;


public class Citizen {
    private boolean haveWork = false;
    private Ground ground = null;
    private final int id;

    private Citizen(int id) {
        this.id = id;
    }

    public static void addCitizen(City city) {
        Citizen citizen = new Citizen(city.getListOfCitizens().get(city.getListOfCitizens().size() - 1).getId() + 1);
        city.getListOfCitizens().add(citizen);
    }

    public int getId() {
        return id;
    }

    public Ground getGround() {
        return ground;
    }

    public boolean setGround(Ground ground) {
        if (ground.isWorkedOn()) return false;
        this.ground = ground;
        haveWork = true;
        ground.setWorkedOn();
        return true;
    }

    public void removeFromGround() {
        this.ground = null;
        haveWork = false;
    }

    public boolean isHaveWork() {
        return haveWork;
    }
}
