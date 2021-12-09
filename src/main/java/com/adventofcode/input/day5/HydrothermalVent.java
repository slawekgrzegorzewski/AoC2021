package com.adventofcode.input.day5;

import java.util.ArrayList;
import java.util.List;

public class HydrothermalVent {
    private final Point from, to;

    public HydrothermalVent(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public HydrothermalVent(String fromString, String toString) {
        String[] from = fromString.split(",");
        String[] to = toString.split(",");
        this.from = new Point(Integer.parseInt(from[0]), Integer.parseInt(from[1]));
        this.to = new Point(Integer.parseInt(to[0]), Integer.parseInt(to[1]));
    }

    public Point from() {
        return from;
    }

    public Point to() {
        return to;
    }

    public boolean isHorizontal() {
        return from.x() == to.x();
    }

    public boolean isVertical() {
        return from.y() == to.y();
    }

    public boolean isDiagonal() {
        return Math.abs(from.y() - to.y()) == Math.abs(from.x() - to.x());
    }

    public List<Point> allPointsOnPath() {
        List<Point> line = new ArrayList<>();
        line.add(from);
        Point next = from;
        do {
            next = nextPointTowardTo(next);
            line.add(next);
        } while (!to.equals(next));
        return line;
    }

    private Point nextPointTowardTo(Point from) {
        if (isHorizontal()) {
            if (from.y() > to.y()) {
                return from.up();
            }
            if (from.y() < to.y()) {
                return from.down();
            }
            return to;
        }
        if (isVertical()) {
            if (from.x() > to.x()) {
                return from.left();
            }
            if (from.x() < to.x()) {
                return from.right();
            }
            return to;
        }
        if (isDiagonal()) {
            if (from.x() > to.x()) {
                from = from.left();
            }
            if (from.x() < to.x()) {
                from = from.right();
            }
            if (from.y() > to.y()) {
                from = from.up();
            }
            if (from.y() < to.y()) {
                from = from.down();
            }
            return from;
        }
        return to;
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}
