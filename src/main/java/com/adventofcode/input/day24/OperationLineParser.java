package com.adventofcode.input.day24;

import java.util.Queue;

public class OperationLineParser {
    private final Queue<Long> input;

    public OperationLineParser(Queue<Long> input) {
        this.input = input;
    }

    public Operation parse(String line) {
        if (line.startsWith("inp")) {
            return InputOperation.parse(line, input);
        }
        if (line.startsWith("add")) {
            return AddOperation.parse(line, input);
        }
        if (line.startsWith("mul")) {
            return MultiplyOperation.parse(line, input);
        }
        if (line.startsWith("div")) {
            return DivideOperation.parse(line, input);
        }
        if (line.startsWith("mod")) {
            return ModuloOperation.parse(line, input);
        }
        if (line.startsWith("eql")) {
            return EqualOperation.parse(line, input);
        }
        throw new IllegalStateException();
    }
}
