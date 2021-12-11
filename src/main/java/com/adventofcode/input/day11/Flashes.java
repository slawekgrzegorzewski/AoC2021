package com.adventofcode.input.day11;

public class Flashes {
    public static Flashes EMPTY = new Flashes(0);
    private final int count;

    public static Flashes of(int count) {
        return count == 0 ? EMPTY : new Flashes(count);
    }

    Flashes(int count) {
        this.count = count;
    }

    public int count() {
        return count;
    }
}
