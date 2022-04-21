package model;

import java.util.Objects;

public class Pair {
    public int firstInt, secondInt;

    public Pair(int firstInt, int secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair pair = (Pair) o;
        return firstInt == pair.firstInt && secondInt == pair.secondInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstInt, secondInt);
    }
}
