package com.adventofcode.input.day23;

public class MoveOut implements Move {
    private final Room from;
    private final int to;

    public MoveOut(Room from, int to) {
        this.from = from;
        this.to = to;
    }

    public Room from() {
        return from;
    }

    public int to() {
        return to;
    }
}
