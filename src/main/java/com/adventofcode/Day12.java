package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;

public class Day12 {

    List<String> input;

    public static void main(String[] input) throws IOException {
        Day12 day12 = new Day12();
        System.out.println("part1 = " + day12.part1());
        System.out.println("part2 = " + day12.part2());
    }

    public Day12() throws IOException {
        input = Input.navigationLines("/day12");
    }

    int part1() {
        return 0;
    }

    int part2() {
        return 0;
    }
}

