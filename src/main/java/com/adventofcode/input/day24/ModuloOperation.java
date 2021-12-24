package com.adventofcode.input.day24;

import java.util.Arrays;
import java.util.Queue;

public class ModuloOperation implements Operation {

    private final MemoryField from;
    private final MemoryField to;
    private final Long toLiteral;

    public static ModuloOperation parse(String line, Queue<Long> input) {
        String parameters[] = line.replace("mod ", "").split(" ");
        if (Arrays.stream(MemoryField.values()).anyMatch(mf -> mf.name().equals(parameters[1].toUpperCase()))) {
            return new ModuloOperation(MemoryField.valueOf(parameters[0].toUpperCase()), MemoryField.valueOf(parameters[1].toUpperCase()));
        }
        return new ModuloOperation(MemoryField.valueOf(parameters[0].toUpperCase()), Long.parseLong(parameters[1]));
    }

    private ModuloOperation(MemoryField from, MemoryField to) {
        this.from = from;
        this.to = to;
        toLiteral = null;
    }

    private ModuloOperation(MemoryField from, Long toLiteral) {
        this.from = from;
        this.to = null;
        this.toLiteral = toLiteral;
    }


    @Override
    public void perform(Memory m) {
        long sum = from.get(m) % (to == null ? toLiteral : to.get(m));
        from.set(m, sum);
    }
}
