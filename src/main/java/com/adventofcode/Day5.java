package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day5.HydrothermalVent;
import com.adventofcode.input.day5.Point;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day5 {

    List<HydrothermalVent> hydrothermalVents;

    public static void main(String[] input) throws IOException {
        Day5 day5 = new Day5();
        System.out.println("part1 = " + day5.part1());
        System.out.println("part2 = " + day5.part2());
    }

    public Day5() throws IOException {
        hydrothermalVents = Input.hydrothermalVents("/day5");
    }

    int part1() {
        return countCrossingPoints(hydrothermalVent -> hydrothermalVent.isVertical() || hydrothermalVent.isHorizontal());
    }

    int part2() {
        return countCrossingPoints(hydrothermalVent -> hydrothermalVent.isVertical() || hydrothermalVent.isHorizontal() || hydrothermalVent.isDiagonal());
    }

    private int countCrossingPoints(Predicate<HydrothermalVent> filter) {
        Map<Point, Long> numberOfLinesCrossingPoint = hydrothermalVents.stream()
                .filter(filter)
                .flatMap(hydrothermalVent -> hydrothermalVent.allPointsOnPath().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        return (int) numberOfLinesCrossingPoint.values().stream().mapToLong(l -> l).filter(l -> l > 1L).count();
    }
}

