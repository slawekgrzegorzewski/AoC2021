package com.adventofcode.input.day19;

import java.util.Objects;

public class Pair<F, S> {

    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> pair(F first, S second) {
        return new Pair<>(first, second);
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
