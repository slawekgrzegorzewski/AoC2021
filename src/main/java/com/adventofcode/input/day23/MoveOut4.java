package com.adventofcode.input.day23;

public class MoveOut4 implements Move {
    private final Room4 from;
    private final int to;

    public MoveOut4(Room4 from, int to) {
        this.from = from;
        this.to = to;
    }

    public Room4 from() {
        return from;
    }

    public int to() {
        return to;
    }
}
