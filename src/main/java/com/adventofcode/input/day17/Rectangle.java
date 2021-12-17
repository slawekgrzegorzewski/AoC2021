package com.adventofcode.input.day17;

import com.adventofcode.input.Coordinates;

public record Rectangle(Coordinates upperLeft, Coordinates bottomRight) {

    public boolean hit(Coordinates node) {
        return reachedHorizontally(node) && reachedVertically(node) && !passed(node);
    }

    public boolean passed(Coordinates node) {
        return passedHorizontally(node) || passedVertically(node);
    }

    public boolean reachedVertically(Coordinates node) {
        return upperLeft.y() >= node.y();
    }

    public boolean passedVertically(Coordinates node) {
        return bottomRight.y() > node.y();
    }

    public boolean passedHorizontally(Coordinates node) {
        return bottomRight.x() < node.x();
    }

    public boolean reachedHorizontally(Coordinates node) {
        return upperLeft.x() <= node.x();
    }
}
