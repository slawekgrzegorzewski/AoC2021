package com.adventofcode.input.day24;

import java.util.Arrays;
import java.util.Queue;

public class EqualOperation implements Operation {

    private final MemoryField from;
    private final MemoryField to;
    private final Long toLiteral;

    public static EqualOperation parse(String line, Queue<Long> input) {
        String parameters[] = line.replace("eql ", "").split(" ");
        if (Arrays.stream(MemoryField.values()).anyMatch(mf -> mf.name().equals(parameters[1].toUpperCase()))) {
            return new EqualOperation(MemoryField.valueOf(parameters[0].toUpperCase()), MemoryField.valueOf(parameters[1].toUpperCase()));
        }
        return new EqualOperation(MemoryField.valueOf(parameters[0].toUpperCase()), Long.parseLong(parameters[1]));
    }

    private EqualOperation(MemoryField from, MemoryField to) {
        this.from = from;
        this.to = to;
        toLiteral = null;
    }

    private EqualOperation(MemoryField from, Long toLiteral) {
        this.from = from;
        this.to = null;
        this.toLiteral = toLiteral;
    }


    @Override
    public void perform(Memory m) {
        long firstValue = from.get(m);
        long secondValue = to == null ? toLiteral : to.get(m);
        from.set(m, firstValue == secondValue ? 1L : 0L);
    }
}
