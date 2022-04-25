package model;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class River {
    public static ArrayList<River> allRivers=new ArrayList<>();
    private Ground firstGround,secondGround;

    public Ground getFirstGround() {
        return firstGround;
    }

    public Ground getSecondGround() {
        return secondGround;
    }

    public River(Ground firstGround, Ground secondGround){
        this.firstGround=firstGround;
        this.secondGround=secondGround;
        allRivers.add(this);
    }
    public static boolean couldWePutRiverBetweenTheseTwoGround(Ground firstGround,Ground secondGround){
        for (int i=0;i<allRivers.size();i++) if (allRivers.get(i).firstGround.number==firstGround.number
                && allRivers.get(i).secondGround.number==secondGround.number) return false;
        GlobalVariables globalVariables=new GlobalVariables();
        if (abs(firstGround.getxLocation()-secondGround.getxLocation())<=globalVariables.tool6Zelie
                && abs(firstGround.getyLocation()-secondGround.getyLocation())<=globalVariables.arz6Zelie) return true;
        return false;
    }
}
