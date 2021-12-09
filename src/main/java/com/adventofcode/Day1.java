package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;

public class Day1 {
    private final int[] measurements;

    public static void main(String[] input) throws IOException {
        Day1 day1 = new Day1();
        System.out.println("part1 = " + day1.part1());
        System.out.println("part2 = " + day1.part2());
    }

    public Day1() throws IOException {
        this.measurements = Input.ints("/day1");
    }

    int part1() {
        return countIncreasingMeasurements(1);
    }

    int part2() {
        return countIncreasingMeasurements(3);
    }

    private int countIncreasingMeasurements(int windowSize) {
        int count = 0;
        int window = 0;
        for (int i = 0; i < windowSize; i++) {
            window += measurements[i];
        }
        for (int i = windowSize; i < measurements.length; i++) {
            int newWindow = window + measurements[i] - measurements[i - windowSize];
            if (newWindow > window) {
                count++;
            }
            window = newWindow;
        }
        return count;
    }
}


