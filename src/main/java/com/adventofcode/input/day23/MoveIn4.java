package com.adventofcode.input.day23;

public class MoveIn4 implements Move {
    private final Room4 to;
    private final int from;

    public MoveIn4(int from, Room4 to) {
        this.to = to;
        this.from = from;
    }

    public int from() {
        return from;
    }

    public Room4 to() {
        return to;
    }
}
