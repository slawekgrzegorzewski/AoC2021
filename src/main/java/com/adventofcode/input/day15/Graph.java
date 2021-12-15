package com.adventofcode.input.day15;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Node findNode(String name) {
        return nodes.stream().filter(n -> n.getName().equals(name)).findAny().orElse(null);
    }
}