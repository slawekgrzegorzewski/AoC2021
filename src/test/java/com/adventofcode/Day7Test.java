
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    static Day7 day7;

    @BeforeAll
    public static void init() throws IOException {
        day7 = new Day7();
    }

    @Test
    void testPart1() {
        assertEquals(340987, day7.part1());
    }

    @Test
    void testPart2() {
        assertEquals(96987874, day7.part2());
    }
}