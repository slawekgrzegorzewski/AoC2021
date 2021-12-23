package com.adventofcode.input.day23;

public enum Amphipod {
    A(1, 0), B(10, 1), C(100, 2), D(1000, 3);

    private final int moveCost;
    private final int targetRoom;

    Amphipod(int moveCost, int targetRoom) {
        this.moveCost = moveCost;
        this.targetRoom = targetRoom;
    }

    public int moveCost() {
        return moveCost;
    }

    public int targetRoom() {
        return targetRoom;
    }
}
