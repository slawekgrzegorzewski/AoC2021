package com.adventofcode.input.day23;

public class MoveIn implements Move {
    private final Room to;
    private final int from;

    public MoveIn(int from, Room to) {
        this.to = to;
        this.from = from;
    }

    public int from() {
        return from;
    }

    public Room to() {
        return to;
    }
}
