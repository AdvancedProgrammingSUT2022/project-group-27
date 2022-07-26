package model;


public class Citizen {
    private boolean haveWork = false;
    private Ground ground = null;
    private final int id;

    private Citizen(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Ground getGround() {
        return ground;
    }
}
