
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    static Day6 day6;

    @BeforeAll
    public static void init() throws IOException {
        day6 = new Day6();
    }

    @Test
    void testPart1() {
        assertEquals(359344, day6.part1());
    }

    @Test
    void testPart2() {
        assertEquals(1629570219571L, day6.part2());
    }
}