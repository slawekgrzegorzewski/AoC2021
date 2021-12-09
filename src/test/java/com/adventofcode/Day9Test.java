
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    static Day9 day9;

    @BeforeAll
    public static void init() throws IOException {
        day9 = new Day9();
    }

    @Test
    void testPart1() {
        assertEquals(522, day9.part1());
    }

    @Test
    void testPart2() {
        assertEquals(916688, day9.part2());
    }

}