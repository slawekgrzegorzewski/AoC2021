package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;
import com.adventofcode.input.day11.Flashes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.adventofcode.input.day11.Flashes.EMPTY;

public class Day11 {

    private final List<Coordinates> octopusesIterator = Coordinates.walkTroughAllPointsVertically(10, 10);
    private final int[][] octopusesSource;
    private int[][] octopuses;

    public static void main(String[] input) throws IOException {
        Day11 day11 = new Day11();
        System.out.println("part1 = " + day11.part1());
        System.out.println("part2 = " + day11.part2());
    }

    public Day11() throws IOException {
        octopusesSource = Input.octopuses("/day11");
    }

    private void createWorkData() {
        octopuses = new int[10][10];
        Coordinates.walkTroughAllPointsVertically(10, 10)
                .forEach(c ->
                        c.setValue(octopuses, c.getValue(octopusesSource))
                );
    }

    int part1() {
        createWorkData();
        return IntStream.range(0, 100)
                .map(i -> dayPass())
                .sum();
    }

    int part2() {
        createWorkData();
        int steps = 1;
        while (dayPass() < 100) steps++;
        return steps;
    }

    private int dayPass() {
        octopusesIterator.forEach(this::increaseEnergyLevel);
        return Stream.<Flashes>iterate(null, f -> f != EMPTY, f -> this.flash())
                .filter(Objects::nonNull)
                .mapToInt(Flashes::count)
                .sum();
    }

    private Flashes flash() {
        int sum = octopusesIterator
                .stream()
                .mapToInt(c -> {
                    if (c.getValue(octopuses) > 9) {
                        propagateFlash(c);
                        resetEnergyLevel(c);
                        return 1;
                    }
                    return 0;
                }).sum();
        return Flashes.of(sum);
    }

    private void propagateFlash(Coordinates c) {
        c.adjacentCellsWithDiagonals()
                .stream()
                .filter(adjacentC -> adjacentC.getValue(octopuses) > 0)
                .forEach(this::increaseEnergyLevel);
    }

    private void resetEnergyLevel(Coordinates c) {
        c.setValue(octopuses, 0);
    }

    private void increaseEnergyLevel(Coordinates c) {
        c.setValue(octopuses, c.getValue(octopuses) + 1);
    }
}

