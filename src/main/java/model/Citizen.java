package model;


public class Citizen {
    private boolean haveWork = false;
    private Ground ground = null;

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
