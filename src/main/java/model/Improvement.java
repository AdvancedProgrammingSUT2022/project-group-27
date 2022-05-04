package model;
import Enum.ImprovementType;
public class Improvement {
    private ImprovementType improvementType;
    private int turnRemained;
    public Improvement(ImprovementType improvementType,int turnRemained){
        this.improvementType=improvementType;
        this.turnRemained=turnRemained;
    }

    public ImprovementType getImprovementType() {
        return improvementType;
    }

    public void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    public void setTurnRemained(int turnRemained) {
        this.turnRemained = turnRemained;
    }
    public void decreaseAmount(int amount){
        this.turnRemained-=amount;
    }

    public int getTurnRemained() {
        return turnRemained;
    }
}
