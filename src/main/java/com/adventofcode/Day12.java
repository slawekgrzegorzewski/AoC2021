package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day12 {

    Map<String, List<String>> graph;

    public static void main(String[] input) throws IOException {
        Day12 day12 = new Day12();
        System.out.println("part1 = " + day12.part1());
        System.out.println("part2 = " + day12.part2());
    }

    public Day12() throws IOException {
        graph = Input.cavesGraph("/day12");
    }

    int part1() {
        return findPaths(new ArrayList<>(), "start", (currentNode, localCopyOfHops) -> isUpperCase(currentNode) || !localCopyOfHops.contains(currentNode)).size();
    }

    int part2() {
        return findPaths(new ArrayList<>(), "start", (currentNode, localCopyOfHops) -> {
            if (isUpperCase(currentNode) || !localCopyOfHops.contains(currentNode))
                return true;

            Map<String, Long> numberOfOccurrences = localCopyOfHops
                    .stream()
                    .filter(hop -> !isUpperCase(hop))
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));
            if (currentNode.equals("start") && numberOfOccurrences.get("start") > 0) return false;
            if (currentNode.equals("end") && numberOfOccurrences.get("end") > 0) return false;
            return numberOfOccurrences.values().stream().noneMatch(v -> v > 1);
        }).size();
    }

    private List<List<String>> findPaths(List<String> hops, String currentNode, BiPredicate<String, List<String>> nodeValidityPredicate) {
        List<String> localCopyOfHops = new ArrayList<>(hops);
        if (nodeValidityPredicate.test(currentNode, localCopyOfHops)) {
            localCopyOfHops.add(currentNode);
            if (currentNode.equals("end")) {
                return List.of(localCopyOfHops);
            }
        } else {
            return List.of();
        }
        return graph.computeIfAbsent(currentNode, key -> new ArrayList<>())
                .stream()
                .flatMap(nextHop -> findPaths(localCopyOfHops, nextHop, nodeValidityPredicate).stream())
                .filter(l -> l.size() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isUpperCase(String string) {
        return string.chars().allMatch(i -> Character.isUpperCase((char) i));
    }
}

