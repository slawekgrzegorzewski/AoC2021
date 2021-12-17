package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;
import com.adventofcode.input.day17.Rectangle;
import com.adventofcode.input.day17.ShotResult;
import com.adventofcode.input.day17.Velocity;

import java.io.IOException;

public class Day17 {

    Rectangle rectangle;
    private Integer hits;
    private Integer maxHeight;

    public static void main(String[] input) throws IOException {
        Day17 day17 = new Day17();
        System.out.println("part1 = " + day17.part1());
        System.out.println("part2 = " + day17.part2());
    }

    public Day17() throws IOException {
        rectangle = Input.targetArea("/day17");
    }

    int part1() {
        if (maxHeight == null)
            calculateTasks();
        return maxHeight;
    }

    int part2() {
        if (hits == null)
            calculateTasks();
        return this.hits;
    }

    private void calculateTasks() {
        hits = 0;
        maxHeight = 0;
        for (int vx = -500; vx <= 500; vx++) {
            for (int vy = -500; vy <= 500; vy++) {
                ShotResult shotResult = simulateThrow(new Velocity(vx, vy));
                if (rectangle.hit(shotResult.finalPosition())) {
                    hits++;
                    if (shotResult.maxHeight() > maxHeight) {
                        maxHeight = shotResult.maxHeight();
                    }
                }
            }
        }
    }

    private ShotResult simulateThrow(Velocity v) {
        Coordinates current = Coordinates.ofInfiniteSpace(0, 0);
        int maxHeight = 0;
        Velocity lastVelocity = v;
        Velocity originalVelocity = v;
        while (!rectangle.hit(current) && !rectangle.passed(current)) {
            current = current.down(v.y()).orElseThrow().right(v.x()).orElseThrow();
            if (current.y() > maxHeight) {
                maxHeight = current.y();
            }
            lastVelocity = v;
            v = v.next();
        }
        return new ShotResult(current, maxHeight, originalVelocity, lastVelocity);
    }
}

