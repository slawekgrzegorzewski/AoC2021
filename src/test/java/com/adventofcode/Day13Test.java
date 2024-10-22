
package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    static Day13 day13;

    @BeforeAll
    public static void init() throws IOException {
        day13 = new Day13();
    }

    @Test
    void testPart1() {
        assertEquals(724, day13.part1());
    }
}