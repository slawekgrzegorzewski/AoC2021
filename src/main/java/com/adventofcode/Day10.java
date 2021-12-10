package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.Verdict;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day10 {

    List<String> navigationLines;
    private final Map<Character, Integer> incorrectChunksScore = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137
    );
    private final Map<Character, Integer> lackingChunksScore = Map.of(
            ')', 1,
            ']', 2,
            '}', 3,
            '>', 4
    );

    public static void main(String[] input) throws IOException {
        Day10 day10 = new Day10();
        System.out.println("part1 = " + day10.part1());
        System.out.println("part2 = " + day10.part2());
    }

    public Day10() throws IOException {
        navigationLines = Input.navigationLines("/day10");
    }

    long part1() {
        return navigationLines.stream()
                .map(this::checkLine)
                .filter(verdict -> verdict.lineType() == Verdict.LineType.INCORRECT)
                .mapToLong(Verdict::score)
                .sum();
    }

    long part2() {
        long[] sortedScores = navigationLines.stream()
                .map(this::checkLine)
                .filter(verdict -> verdict.lineType() == Verdict.LineType.INCOMPLETE)
                .mapToLong(Verdict::score)
                .sorted()
                .toArray();
        return sortedScores[sortedScores.length / 2];
    }

    private Verdict checkLine(String line) {
        LinkedList<Character> openingChunks = new LinkedList<>();
        for (char c : line.toCharArray()) {
            switch (c) {
                case '(', '{', '[', '<' -> openingChunks.add(c);
                default -> {
                    Character openingBracket = openingChunks.removeLast();
                    if (getClosingBracketFor(openingBracket) != c)
                        return new Verdict(Verdict.LineType.INCORRECT, incorrectChunksScore.get(c));
                }
            }
        }
        long score = 0;
        while (!openingChunks.isEmpty()) {
            Character lackingClosingBracket = getClosingBracketFor(openingChunks.removeLast());
            score = 5 * score + lackingChunksScore.get(lackingClosingBracket);
        }
        return new Verdict(score == 0 ? Verdict.LineType.CORRECT : Verdict.LineType.INCOMPLETE, score);
    }

    private char getClosingBracketFor(char c) {
        return switch (c) {
            case '(' -> ')';
            case '{' -> '}';
            case '[' -> ']';
            case '<' -> '>';
            default -> throw new IllegalStateException("Not a opening bracket: " + c);
        };
    }
}

