package com.adventofcode.input.day22;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CuboidTest {

    @Test
    void a() {
        Cuboid c = new Cuboid(10, 10, 10, 12, 12, 12);
        Cuboid c1 = new Cuboid(11, 11, 11, 13, 13, 13);
        Cuboid c2 = new Cuboid(14, 14, 14, 16, 16, 16);

        Optional<Cuboid> common = c.common(c1);
        Optional<Cuboid> common2 = c1.common(c);
        Optional<Cuboid> common3 = c.common(c2);
        Optional<Cuboid> common4 = c2.common(c);
        Optional<Cuboid> common5 = c2.common(c2);

        System.out.println();

        Map<Integer, List<String>> a = Map.of(
                1, List.of("a", "b", "c"),
                2, List.of(),
                3, List.of("1", "2", "3"),
                4, List.of());
        List<String> strings = IntStream.of(1, 2, 3, 4)
                .mapToObj(a::get)
                .flatMap(List::stream)
                .toList();

        System.out.println();
    }

}