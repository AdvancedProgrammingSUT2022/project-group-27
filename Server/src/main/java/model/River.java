package model;

import java.util.ArrayList;

public class River {
    private static final ArrayList<River> allRivers = new ArrayList<>();
    private Integer firstGround, secondGround;

    public Ground getFirstGround() {
        return Ground.getGroundByNumber(firstGround);
    }

    public Ground getSecondGround() {
        return Ground.getGroundByNumber(firstGround);
    }

    public static ArrayList<River> getAllRivers() {
        return allRivers;
    }

    public River(Ground firstGround, Ground secondGround) {
        this.firstGround = firstGround.getNumber();
        this.secondGround = secondGround.getNumber();
        allRivers.add(this);
    }

    public static boolean couldWePutRiverBetweenTheseTwoGround(Ground firstGround, Ground secondGround) {
        for (River allRiver : allRivers)
            if (allRiver.firstGround == firstGround.getNumber()
                    && allRiver.secondGround == secondGround.getNumber()) return false;
        GlobalVariables globalVariables = new GlobalVariables();
        return Ground.AreTheseTwoGroundAdjacent(firstGround,secondGround);
    }
    public static boolean doWeHaveGroundBetweenTheseTwoGrounds(Ground firstGround,Ground secondGround){
        for (River river : allRivers){
            if (river.getFirstGround().equals(firstGround) && river.getSecondGround().equals(secondGround))
                return true;
            if (river.getFirstGround().equals(secondGround) && river.getSecondGround().equals(firstGround))
                return true;
        }
        return false;
    }
}
