package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day19.Pair;
import com.adventofcode.input.day24.Memory;
import com.adventofcode.input.day24.Operation;
import com.adventofcode.input.day24.OperationLineParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day24 {

    List<String> program;

    int[] A = new int[]{1, 1, 1, 26, 1, 26, 26, 1, 1, 1, 26, 26, 26, 26};
    int[] B = new int[]{14, 15, 13, -10, 14, -3, -14, 12, 14, 12, -6, -6, -2, -9};
    int[] C = new int[]{8, 11, 2, 11, 1, 5, 10, 6, 1, 11, 9, 14, 11, 2};

    public static void main(String[] input) throws IOException {
        Day24 day24 = new Day24();
        System.out.println("part1 = " + day24.part1());
        System.out.println("part2 = " + day24.part2());
    }

    public Day24() throws IOException {
        program = Input.program("/day24");
    }

    long part1() {
        for (long l = 99_999_999_999_999L; l > 99_999_999_999_999L; l--) {
            Queue<Long> input = createInput(l);
            if (input == null) continue;
            Memory m = new Memory();
            program.stream().map(new OperationLineParser(input)::parse).forEach(o -> {
                o.perform(m);
            });
            if (m.z() == 0) return l;
        }
        long maxNumber = getMaxNumber();
        Queue<Long> maxInput = createInput(maxNumber);
        long z = 0;
        for (int i = 0; i < 14; i++) {
            z = step(maxInput.remove(), z, i);
        }
        if (z == 0) {
            return maxNumber;
        }
        return 0L;
    }

    long part2() {
        long minNumber = getMinNumber();
        Queue<Long> maxInput = createInput(minNumber);
        long z = 0;
        for (int i = 0; i < 14; i++) {
            z = step(maxInput.remove(), z, i);
        }
        if (z == 0) {
            return minNumber;
        }
        return 0L;
    }

    long step(long input, long z, int i) {
        long paramA = A[i], paramB = B[i], paramC = C[i];
        System.out.println(
                String.format("z%d = (z%d %% 26 + %d == input%d ? 0 : 1) * (z%d / %d * 25 + input%d + %d) + z%d / %d",
                        i, i-1, paramB, i, i-1, paramA, i, paramC, i-1, paramA)
        );
        return (z % 26L + paramB == input ? 0L : 1L) * (z / paramA * 25L + input + paramC) + z / paramA;
    }

    void step2(long input, Memory m, int A, int B, int C) {
        long w = m.w();
        long x = m.x();
        long y = m.y();
        long z = m.z();

        w = input;
        x = x * 0;
        x = x + z;
        x = x % 26;
        z = z / A;
        x = x + B;
        x = (x == w) ? 1 : 0;
        x = (x == 0) ? 1 : 0;
        y = y * 0;
        y = y + 25;
        y = y * x;
        y = y + 1;
        z = z * y;
        y = y * 0;
        y = y + w;
        y = y + C;
        y = y * x;
        z = z + y;

        m.setW(w);
        m.setX(x);
        m.setY(y);
        m.setZ(z);
    }

    private Queue<Long> createInput(long number) {
        Queue<Long> input = new LinkedList<>();
        List<Long> buffer = new ArrayList<>();
        while (number > 0) {
            buffer.add(number % 10);
            number = number / 10;
        }
        for (int i = buffer.size() - 1; i >= 0; i--) {
            Long value = buffer.get(i);
            if (value == 0) return null;
            input.add(value);
        }
        return input;
    }

    private long getMinNumber() {
        long input0 = 2;
        long input1 = 4;
        long input2 = 9;
        long input3 = input2 - 8;
        long input4 = 3;
        long input5 = input4 - 2;
        long input6 = input1 - 3;
        long input7 = 1;
        long input8 = 6;
        long input9 = 1;
        long input10 = input9 + 5;
        long input11 = input8 - 5;
        long input12 = input7 + 4;
        long input13 = input0 - 1;
        return input0 * 10_000_000_000_000L
                + input1 * 1_000_000_000_000L
                + input2 * 100_000_000_000L
                + input3 * 10_000_000_000L
                + input4 * 1_000_000_000L
                + input5 * 100_000_000L
                + input6 * 10_000_000L
                + input7 * 1_000_000L
                + input8 * 100_000L
                + input9 * 10_000L
                + input10 * 1_000L
                + input11 * 100L
                + input12 * 10L
                + input13 * 1L;
    }

    private long getMaxNumber() {
        long input0 = 9;
        long input1 = 9;
        long input2 = 9;
        long input3 = input2 - 8;
        long input4 = 9;
        long input5 = input4 - 2;
        long input6 = input1 - 3;
        long input7 = 5;
        long input8 = 9;
        long input9 = 4;
        long input10 = input9 + 5;
        long input11 = input8 - 5;
        long input12 = input7 + 4;
        long input13 = input0 - 1;

        return input0 * 10_000_000_000_000L
                + input1 * 1_000_000_000_000L
                + input2 * 100_000_000_000L
                + input3 * 10_000_000_000L
                + input4 * 1_000_000_000L
                + input5 * 100_000_000L
                + input6 * 10_000_000L
                + input7 * 1_000_000L
                + input8 * 100_000L
                + input9 * 10_000L
                + input10 * 1_000L
                + input11 * 100L
                + input12 * 10L
                + input13 * 1L;
    }

    private void debug(List<Pair<String, Operation>> progarmToOptimize) {
        System.out.println("| |w|x|y|z|");
        System.out.println("|-----|-----|-----|-----|-----|");
        System.out.println("| |0|0|0|0|");
        Memory m = new Memory();
        for (Pair<String, Operation> stringOperationPair : progarmToOptimize) {
            stringOperationPair.second().perform(m);
            if (stringOperationPair.first().startsWith("inp")) {
                System.out.println("| | | | | |");
            }
            System.out.printf("|%s|%d|%d|%d|%d|%n", stringOperationPair.first(), m.w(), m.x(), m.y(), m.z());
        }
    }
}

