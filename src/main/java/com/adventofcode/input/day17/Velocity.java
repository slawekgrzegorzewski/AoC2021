package com.adventofcode.input.day17;

public record Velocity(int x, int y) {

    public Velocity next() {
        return new Velocity(x > 0 ? x - 1 : 0, y - 1);
    }
}
