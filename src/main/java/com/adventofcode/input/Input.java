package com.adventofcode.input;

import com.adventofcode.input.bingo.BingoBoard;
import com.adventofcode.input.bingo.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Input {

    public static int[] ints(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static List<Instruction> instructions(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(String::trim)
                .map(line -> line.split(" "))
                .map(lineElements -> new Instruction(lineElements[0], Integer.parseInt(lineElements[1])))
                .collect(Collectors.toList());
    }

    public static List<BinaryInput> binaryLines(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(String::trim)
                .map(BinaryInput::new)
                .collect(Collectors.toList());
    }

    public static int[][] heightMap(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.chars().mapToObj(sign -> Character.toString((char) sign)).mapToInt(Integer::valueOf).toArray())
                .toArray(int[][]::new);
    }

    public static Game game(String resourceName) throws IOException {
        List<String> inputFromFile = getInputFromFile(resourceName);
        List<Integer> numbers = Arrays.stream(inputFromFile.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<BingoBoard> boards = new ArrayList<>();
        for (int i = 2; i < inputFromFile.size(); i += 6) {
            int[][] boardElements = IntStream.range(i, i + 5)
                    .mapToObj(inputFromFile::get)
                    .map(line -> line.split(" "))
                    .map(stringNumbers -> Stream.of(stringNumbers).map(String::trim).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray())
                    .toArray(int[][]::new);
            boards.add(new BingoBoard(boardElements));
        }
        return new Game(numbers, boards);
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Input.class.getResourceAsStream(resourceName));
             BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }

}