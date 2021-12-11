package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;

public class Day13 {

    List<String> input;

    public static void main(String[] input) throws IOException {
        Day13 day13 = new Day13();
        System.out.println("part1 = " + day13.part1());
        System.out.println("part2 = " + day13.part2());
    }

    public Day13() throws IOException {
        input = Input.navigationLines("/day12");
    }

    int part1() {
        return 0;
    }

    int part2() {
        return 0;
    }
}

