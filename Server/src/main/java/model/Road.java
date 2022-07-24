package model;

public class Road {
    private int turn=3;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void decreaseTurn(int amount){
        this.turn-=amount;
    }
}
