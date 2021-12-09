package com.adventofcode.input.bingo;

public class BingoBoardElement {
    private final int number;
    private boolean marked = false;

    public BingoBoardElement(int number) {
        this.number = number;
    }

    public void checkNumber(int number) {
        if (!this.marked) {
            this.marked = number == this.number;
        }
    }

    public int number() {
        return number;
    }

    public boolean marked() {
        return marked;
    }
}
