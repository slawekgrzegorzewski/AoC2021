package com.adventofcode.input.day19;

public class SamePair<T> extends Pair<T, T> {

    public SamePair(T first, T second) {
        super(first, second);
    }

    public static <T> SamePair<T> samePair(T first, T second) {
        return new SamePair<>(first, second);
    }

    @Override
    public String toString() {
        return "(" + first() + ", " + second() + ')';
    }
}
