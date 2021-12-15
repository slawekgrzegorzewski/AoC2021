package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;
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
    // 1 2 3 4 5
    // 2 3 4 5 6
    // 3 4 5 6 7
    // 4 5 6 7 8
    // 5 6 7 8 9

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
        Map<String, Node> nodes = Coordinates.walkTroughAllPoints(w, h)
                .stream()
                .map(n -> new Node(String.valueOf(nodeIndex(n))))
                .collect(Collectors.toMap(
                        Node::getName,
                        n -> n
                ));
        Coordinates.walkTroughAllPoints(w, h)
                .forEach(n -> {
                    Stream.of(n.up(), n.down(), n.left(), n.right())
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(n1 -> {
                                Node source = nodes.get(String.valueOf(nodeIndex(n)));
                                Node destination = nodes.get(String.valueOf(nodeIndex(n1)));
                                source.addDestination(destination, n1.getValue(rm));
                            });
                });
        Graph g = new Graph();
        nodes.values().forEach(g::addNode);
        Node fromNode = nodes.get(String.valueOf(nodeIndex(new Coordinates(0, 0, w, h))));
        String lastNodeName = String.valueOf(nodeIndex(new Coordinates(w - 1, h - 1, w, h)));
        Graph graph = calculateShortestPathFromSource(g, fromNode);
        Node lastNode = graph.findNode(lastNodeName);
        return lastNode.getDistance();
    }

    public Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair :
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode,
                                          Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private int nodeIndex(Coordinates n) {
        return n.y() * n.width() + n.x();
    }
}

