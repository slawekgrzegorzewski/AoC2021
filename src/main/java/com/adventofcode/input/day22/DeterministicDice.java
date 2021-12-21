package com.adventofcode.input.day22;

public class DeterministicDice implements Dice {

    int current;
    int rollCount;

    @Override
    public int roll() {
        current++;
        rollCount++;
        if (current > 100) current = 1;
        return current;
    }

    @Override
    public int rollCount() {
        return rollCount;
    }
}
