package com.adventofcode.input.day24;

import java.util.Queue;

public class InputOperation implements Operation {

    private final MemoryField field;
    private final Queue<Long> input;

    public static InputOperation parse(String line, Queue<Long> input) {
        return new InputOperation(MemoryField.valueOf(line.replace("inp ", "").toUpperCase()), input);
    }

    private InputOperation(MemoryField field, Queue<Long> input) {
        this.field = field;
        this.input = input;
    }


    @Override
    public void perform(Memory m) {
        field.set(m, input.remove());
    }
}
