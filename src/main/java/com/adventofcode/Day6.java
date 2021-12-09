package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.LanternFish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day6 {

    List<LanternFish> lanternFishes;

    public static void main(String[] input) throws IOException {
        Day6 day6 = new Day6();
        System.out.println("part1 = " + day6.part1());
        System.out.println("part2 = " + day6.part2());
    }

    public Day6() throws IOException {
        lanternFishes = Input.lanternFishes("/day6");
    }

    int part1() {
        return (int) memoryEfficientWay(80);
    }

    long part2() {
        return memoryEfficientWay(256);
    }

    private long memoryEfficientWay(int numberOfDays) {
        Map<Integer, Long> numberOfFishesOfNumberOfDaysToReproduce = lanternFishes.stream().collect(
                Collectors.groupingBy(
                        LanternFish::daysToReproduce,
                        Collectors.counting()
                )
        );
        for (int i = 0; i < 9; i++) {
            if (!numberOfFishesOfNumberOfDaysToReproduce.containsKey(i)) {
                numberOfFishesOfNumberOfDaysToReproduce.put(i, 0L);
            }
        }

        for (int i = 0; i < numberOfDays; i++) {
            long dayZeroFishes = numberOfFishesOfNumberOfDaysToReproduce.get(0);
            numberOfFishesOfNumberOfDaysToReproduce.put(0, numberOfFishesOfNumberOfDaysToReproduce.get(1));
            numberOfFishesOfNumberOfDaysToReproduce.put(1, numberOfFishesOfNumberOfDaysToReproduce.get(2));
            numberOfFishesOfNumberOfDaysToReproduce.put(2, numberOfFishesOfNumberOfDaysToReproduce.get(3));
            numberOfFishesOfNumberOfDaysToReproduce.put(3, numberOfFishesOfNumberOfDaysToReproduce.get(4));
            numberOfFishesOfNumberOfDaysToReproduce.put(4, numberOfFishesOfNumberOfDaysToReproduce.get(5));
            numberOfFishesOfNumberOfDaysToReproduce.put(5, numberOfFishesOfNumberOfDaysToReproduce.get(6));
            numberOfFishesOfNumberOfDaysToReproduce.put(6, dayZeroFishes + numberOfFishesOfNumberOfDaysToReproduce.get(7));
            numberOfFishesOfNumberOfDaysToReproduce.put(7, numberOfFishesOfNumberOfDaysToReproduce.get(8));
            numberOfFishesOfNumberOfDaysToReproduce.put(8, dayZeroFishes);
        }

        return numberOfFishesOfNumberOfDaysToReproduce.values().stream().mapToLong(i -> i).sum();
    }
}

