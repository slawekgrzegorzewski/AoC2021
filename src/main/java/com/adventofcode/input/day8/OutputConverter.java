package com.adventofcode.input.day8;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OutputConverter {
    private final Map<Character, Character> mappings;
    private final Set<Character> zero = Set.of('a', 'b', 'c', 'e', 'f', 'g');
    private final Set<Character> one = Set.of('c', 'f');
    private final Set<Character> two = Set.of('a', 'c', 'd', 'e', 'g');
    private final Set<Character> three = Set.of('a', 'c', 'd', 'f', 'g');
    private final Set<Character> four = Set.of('b', 'c', 'd', 'f');
    private final Set<Character> five = Set.of('a', 'b', 'd', 'f', 'g');
    private final Set<Character> six = Set.of('a', 'b', 'd', 'e', 'f', 'g');
    private final Set<Character> seven = Set.of('a', 'c', 'f');
    private final Set<Character> eight = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g');
    private final Set<Character> nine = Set.of('a', 'b', 'c', 'd', 'f', 'g');

    public OutputConverter(Map<Character, Character> mappings) {
        this.mappings = mappings;
    }

    public int convert(Output output) {
        return toNumber(output.elements()[0]) * 1_000
                + toNumber(output.elements()[1]) * 100
                + toNumber(output.elements()[2]) * 10
                + toNumber(output.elements()[3]);
    }

    private int toNumber(String element) {
        Set<Character> characters = translateToRealSegments(element);
        if (characters.equals(zero)) return 0;
        if (characters.equals(one)) return 1;
        if (characters.equals(two)) return 2;
        if (characters.equals(three)) return 3;
        if (characters.equals(four)) return 4;
        if (characters.equals(five)) return 5;
        if (characters.equals(six)) return 6;
        if (characters.equals(seven)) return 7;
        if (characters.equals(eight)) return 8;
        if (characters.equals(nine)) return 9;
        throw new RuntimeException("Not a number");
    }

    private Set<Character> translateToRealSegments(String element) {
        return element.chars().mapToObj(i -> mappings.get((char)i)).collect(Collectors.toSet());
    }
}
