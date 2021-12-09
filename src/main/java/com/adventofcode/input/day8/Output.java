package com.adventofcode.input.day8;

public class Output {
    private final String[] elements;

    public static Output parse(String value) {
        return new Output(value.trim().split(" "));
    }

    private Output(String[] elements) {
        if (elements.length != 4) throw new RuntimeException("Incorrect number of signals");
        this.elements = elements;
    }

    public String[] elements() {
        return elements;
    }
}
