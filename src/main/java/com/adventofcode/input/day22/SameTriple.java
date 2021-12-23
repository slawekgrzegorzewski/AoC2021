package com.adventofcode.input.day22;

public class SameTriple<T> extends Triple<T, T, T> {

    public SameTriple(T first, T second, T third) {
        super(first, second, third);
    }

    public static <T> SameTriple<T> sameTriple(T first, T second, T third) {
        return new SameTriple<>(first, second, third);
    }
}
