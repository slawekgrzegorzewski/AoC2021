package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;
import com.adventofcode.input.day15.DijkstraShortestPath;
import com.adventofcode.input.day15.Graph;
import com.adventofcode.input.day15.Node;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 {

    int[][] riskMap;
    int width, height;

    public static void main(String[] input) throws IOException {
        Day15 day15 = new Day15();
        System.out.println("part1 = " + day15.part1());
        System.out.println("part2 = " + day15.part2());
    }

    public Day15() throws IOException {
        riskMap = Input.riskMap("/day15");
        height = riskMap.length;
        for (int[] ints : riskMap) {
            if (ints.length > width) width = ints.length;
        }
    }

    int part1() {
        return findShortestPath(this.width, this.height, this.riskMap);
    }

    int part2() {
        Map<Integer, Integer[][]> mapOfMultipliedArrays = new HashMap<>();
        for (int i = 1; i < 10; i++) {
            mapOfMultipliedArrays.put(i, addToArray(i - 1, riskMap));
        }
        int[][] newRiskMap = new int[height * 5][width * 5];
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 5; ii++) {
                copyAtPosition(i * height, ii * width, newRiskMap, mapOfMultipliedArrays.get(i + ii + 1));
            }
        }
        return findShortestPath(this.width * 5, this.height * 5, newRiskMap);
    }

    private void copyAtPosition(int i, int ii, int[][] newRiskMap, Integer[][] integers) {
        for (int iprim = i; iprim < i + integers.length; iprim++) {
            for (int iiprim = ii; iiprim < ii + integers[0].length; iiprim++) {
                newRiskMap[iprim][iiprim] = integers[iprim - i][iiprim - ii];
            }
        }
    }

    private Integer[][] addToArray(int factor, int[][] array) {
        Integer[][] result = new Integer[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int ii = 0; ii < array[i].length; ii++) {
                int value = factor + array[i][ii];
                result[i][ii] = value > 9 ? value - 9 : value;
            }
        }
        return result;
    }

    private Integer findShortestPath(int w, int h, int[][] rm) {
        Map<String, Node> nodes = Coordinates.walkTroughAllPointsVertically(w, h)
                .stream()
                .map(n -> new Node(String.valueOf(nodeIndex(n))))
                .collect(Collectors.toMap(
                        Node::getName,
                        n -> n
                ));
        Coordinates.walkTroughAllPointsVertically(w, h)
                .forEach(n -> Stream.of(n.up(), n.down(), n.left(), n.right())
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(n1 -> {
                            Node source = nodes.get(String.valueOf(nodeIndex(n)));
                            Node destination = nodes.get(String.valueOf(nodeIndex(n1)));
                            source.addDestination(destination, n1.getValue(rm));
                        }));
        Graph g = new Graph();
        nodes.values().forEach(g::addNode);
        Node fromNode = nodes.get(String.valueOf(nodeIndex(new Coordinates(0, 0, w, h))));
        String lastNodeName = String.valueOf(nodeIndex(new Coordinates(w - 1, h - 1, w, h)));
        new DijkstraShortestPath().calculate(fromNode);
        Node lastNode = g.findNode(lastNodeName);
        return lastNode.getDistance();
    }

    private int nodeIndex(Coordinates n) {
        return n.y() * n.width() + n.x();
    }
}

