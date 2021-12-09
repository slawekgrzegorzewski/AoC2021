package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.Instruction;

import java.io.IOException;
import java.util.List;

public class Day2 {
    private final List<Instruction> instructions;

    public static void main(String[] input) throws IOException {
        Day2 day2 = new Day2();
        System.out.println("part1 = " + day2.part1());
        System.out.println("part2 = " + day2.part2());
    }

    public Day2() throws IOException {
        this.instructions = Input.instructions("/day2");
    }

    int part1() {
        int horizontalPosition = 0;
        int depth = 0;
        for (Instruction instruction : instructions) {
            switch (instruction.command()) {
                case "forward" -> horizontalPosition += instruction.argument();
                case "down" -> depth += instruction.argument();
                case "up" -> depth -= instruction.argument();
                default -> throw new IllegalStateException("Unexpected value: " + instruction);
            }
        }
        return horizontalPosition * depth;
    }

    int part2() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;
        for (Instruction instruction : instructions) {
            switch (instruction.command()) {
                case "forward" -> {
                    horizontalPosition += instruction.argument();
                    depth += aim * instruction.argument();
                }
                case "down" -> aim += instruction.argument();
                case "up" -> aim -= instruction.argument();
                default -> throw new IllegalStateException("Unexpected value: " + instruction);
            }
        }
        return horizontalPosition * depth;
    }
}

