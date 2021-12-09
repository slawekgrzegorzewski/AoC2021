package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.function.Function;

public class Day7 {

    int[] crabsPositions;

    public static void main(String[] input) throws IOException {
        Day7 day7 = new Day7();
        System.out.println("part1 = " + day7.part1());
        System.out.println("part2 = " + day7.part2());
    }

    public Day7() throws IOException {
        crabsPositions = Input.crabsPositions("/day7");
    }

    int part1() {
        return findOptimalPosition(this::costOfMovingToPosition);
    }

    private int costOfMovingToPosition(int position) {
        return Arrays.stream(crabsPositions)
                .map(p -> Math.abs(p - position))
                .sum();
    }

    int part2() {
        return findOptimalPosition(this::costOfMovingToPosition2);
    }

    private int costOfMovingToPosition2(int position) {
        return Arrays.stream(crabsPositions)
                .map(p -> {
                    int N = Math.abs(p - position);
                    return (1 + N) * N / 2;
                })
                .sum();
    }

    private int findOptimalPosition(Function<Integer, Integer> fuelConsumptionCalculation) {
        IntSummaryStatistics crabsPositionsStatistics = Arrays.stream(crabsPositions).summaryStatistics();
        int fuelCost = Integer.MAX_VALUE;
        for (int i = crabsPositionsStatistics.getMin(); i <= crabsPositionsStatistics.getMax(); i++) {
            int currentCost = fuelConsumptionCalculation.apply(i);
            if (fuelCost > currentCost) {
                fuelCost = currentCost;
            }
        }
        return fuelCost;
    }
}

