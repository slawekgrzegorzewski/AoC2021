package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.Polymer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {

    Polymer polymer;

    public static void main(String[] input) throws IOException {
        Day14 day14 = new Day14();
        System.out.println("part1 = " + day14.part1());
        System.out.println("part2 = " + day14.part2());
    }

    public Day14() throws IOException {
        polymer = Input.polymers("/day14");
    }

    long part1() {
        return task(10);
    }

    long part2() {
        return task(40);
    }

    private long task(int steps) {
        Map<String, Long> pairsCount = new HashMap<>();
        char[] chars = polymer.initialPolymer().toCharArray();
        for (int i = 1; i < chars.length; i++) {
            String pair = String.valueOf(chars[i - 1]) + chars[i];
            pairsCount.compute(pair, (p, count) -> count == null ? 1 : count + 1);
        }
        for (int i = 0; i < steps; i++) {
            pairsCount = step(pairsCount);
        }
        Map<String, Long> collect = pairsCount.entrySet()
                .stream()
                .flatMap(stringLongEntry -> Stream.of(
                        new AbstractMap.SimpleEntry<>(String.valueOf(stringLongEntry.getKey().charAt(0)), stringLongEntry.getValue()),
                        new AbstractMap.SimpleEntry<>(String.valueOf(stringLongEntry.getKey().charAt(1)), stringLongEntry.getValue())
                ))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.summingLong(AbstractMap.SimpleEntry::getValue)
                ));
        LongSummaryStatistics longSummaryStatistics = collect.values().stream().mapToLong(l -> l).summaryStatistics();
        return (longSummaryStatistics.getMax() - longSummaryStatistics.getMin())/2;
    }

    private Map<String, Long> step(Map<String, Long> pairsCount) {
        Map<String, Long> newPairsCount = new HashMap<>();
        pairsCount.keySet()
                .forEach(pair -> {
                    String insertion = polymer.insertions().get(pair);
                    if (insertion != null) {
                        addCountToNewMap(newPairsCount, pair.charAt(0) + insertion, pairsCount.get(pair));
                        addCountToNewMap(newPairsCount, insertion + pair.charAt(1), pairsCount.get(pair));
                    } else {
                        addCountToNewMap(newPairsCount, pair, pairsCount.get(pair));
                    }
                });
        return newPairsCount;
    }

    private void addCountToNewMap(Map<String, Long> toMap, String toKey, Long countToAdd) {
        toMap.compute(toKey, (p, count) -> (count == null ? 0 : count) + countToAdd);
    }
}

