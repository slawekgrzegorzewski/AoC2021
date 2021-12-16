
package com.adventofcode;

import com.adventofcode.input.Input;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

    static Day16 day16;

    @BeforeAll
    public static void init() throws IOException {
        day16 = new Day16();
    }

    @Test
    void testPart1() {
        assertEquals(977, day16.part1());
    }

    @Test
    void testPart2() {
        assertEquals(101501020883L, day16.part2());
    }

    @ParameterizedTest
    @CsvSource({
            "C200B40A82,3", "04005AC33890,54", "880086C3E88112,7", "CE00C43D881120,9", "D8005AC2A8F0,1", "F600BC2D8F,0", "9C005AC2F8F0,0"
    })
    void testExamples(String input, int expectedValue) {
        Day16 testObject = new Day16(Input.bitsFromString(input));
        assertEquals(expectedValue, testObject.part2());
    }
}