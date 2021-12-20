
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test {

    static Day21 day21;

    @BeforeAll
    public static void init() throws IOException {
        day21 = new Day21();
    }

    @Test
    void testPart1() {
        assertEquals(0, day21.part1());
    }

    @Test
    void testPart2() {
        assertEquals(0, day21.part2());
    }
}