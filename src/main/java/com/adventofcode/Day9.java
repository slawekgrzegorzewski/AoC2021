package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {

    private final int[][] heightMap;

    public static void main(String[] input) throws IOException {
        Day9 day9 = new Day9();
        System.out.println("part1 = " + day9.part1());
        System.out.println("part2 = " + day9.part2());
    }

    public Day9() throws IOException {
        heightMap = Input.heightMap("/day9");
    }

    int part1() {
        return lowPoints().stream()
                .mapToInt(c -> c.getValue(heightMap))
                .map(height -> height + 1)
                .sum();
    }

    int part2() {
        List<Set<Coordinates>> basins = new ArrayList<>();
        return lowPoints()
                .stream()
                .map(lowPoint -> expandBasin(lowPoint, new HashSet<>()))
                .mapToInt(set -> -set.size())
                .sorted()
                .limit(3)
                .map(i -> -i)
                .reduce(1, (left, right) -> left * right);
    }

    private Set<Coordinates> expandBasin(Coordinates forPoint, Set<Coordinates> basin) {
        int currentHeight = forPoint.getValue(heightMap);
        basin.add(forPoint);
        Stream.of(forPoint.up(), forPoint.down(), forPoint.left(), forPoint.right())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(c -> shouldIncludeIntoBasin(currentHeight, c))
                .forEach(c -> expandBasin(c, basin));
        return basin;
    }

    private boolean shouldIncludeIntoBasin(int currentHeight, Coordinates c) {
        int checkingHeight = c.getValue(heightMap);
        return checkingHeight > currentHeight && checkingHeight < 9;
    }

    private List<Coordinates> lowPoints() {
        return Coordinates.walkTroughAllPointsVertically(heightMap.length, heightMap[0].length)
                .stream()
                .filter(this::isLowPoint)
                .collect(Collectors.toList());
    }

    private boolean isLowPoint(Coordinates coordinates) {
        int pointValue = coordinates.getValue(heightMap);
        return coordinates.adjacentCellsWithoutDiagonals()
                .stream()
                .mapToInt(c -> c.getValue(heightMap))
                .allMatch(value -> value > pointValue);
    }
}

