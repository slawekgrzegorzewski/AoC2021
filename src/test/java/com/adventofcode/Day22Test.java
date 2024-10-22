
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day22Test {

    static Day22 day22;

    @BeforeAll
    public static void init() throws IOException {
        day22 = new Day22();
    }

    @Test
    void testPart1() {
        assertEquals(545118, day22.part1());
    }

    @Test
    void testPart2() {
        assertEquals(1227298136842375L, day22.part2());
    }
}