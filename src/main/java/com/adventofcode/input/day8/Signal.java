package com.adventofcode.input.day8;

public class Signal {
    private final String[] elements;

    public static Signal parse(String value) {
        return new Signal(value.trim().split(" "));
    }

    private Signal(String[] elements) {
        if (elements.length != 10) throw new RuntimeException("Incorrect number of signals");
        this.elements = elements;
    }

    public String[] elements() {
        return elements;
    }
}
