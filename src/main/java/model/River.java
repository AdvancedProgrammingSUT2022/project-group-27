package model;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class River {
    private static final ArrayList<River> allRivers = new ArrayList<>();
    private Ground firstGround,secondGround;

    public Ground getFirstGround() {
        return firstGround;
    }

    public Ground getSecondGround() {
        return secondGround;
    }

    public static ArrayList<River> getAllRivers() {
        return allRivers;
    }

    public River(Ground firstGround, Ground secondGround){
        this.firstGround=firstGround;
        this.secondGround=secondGround;
        allRivers.add(this);
    }
    public static boolean couldWePutRiverBetweenTheseTwoGround(Ground firstGround,Ground secondGround){
        for (River allRiver : allRivers)
            if (allRiver.firstGround.getNumber() == firstGround.getNumber()
                    && allRiver.secondGround.getNumber() == secondGround.getNumber()) return false;
        GlobalVariables globalVariables=new GlobalVariables();
        return abs(firstGround.getxLocation() - secondGround.getxLocation()) <= globalVariables.tool6Zelie
                && abs(firstGround.getyLocation() - secondGround.getyLocation()) <= globalVariables.arz6Zelie;
    }
}
