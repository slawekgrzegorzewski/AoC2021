package com.adventofcode.input;

import com.adventofcode.input.bingo.BingoBoard;
import com.adventofcode.input.bingo.Game;
import com.adventofcode.input.day13.TransparentPageManual;
import com.adventofcode.input.day17.Rectangle;
import com.adventofcode.input.day18.CompoundSnailfishNumber;
import com.adventofcode.input.day18.SnailfishNumberParser;
import com.adventofcode.input.day19.BeaconPosition;
import com.adventofcode.input.day19.Pair;
import com.adventofcode.input.day19.SamePair;
import com.adventofcode.input.day5.HydrothermalVent;
import com.adventofcode.input.day8.Output;
import com.adventofcode.input.day8.Signal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.adventofcode.input.day19.Pair.pair;
import static com.adventofcode.input.day19.SamePair.samePair;

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

    public static List<HydrothermalVent> hydrothermalVents(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(" -> "))
                .map(twoPointsStrings -> new HydrothermalVent(twoPointsStrings[0], twoPointsStrings[1]))
                .toList();
    }

    public static List<LanternFish> lanternFishes(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .map(LanternFish::new)
                .toList();
    }

    public static int[] crabsPositions(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static Map<Signal, Output> signalsReads(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split("\\|"))
                .collect(Collectors.toMap(
                        elements -> Signal.parse(elements[0]),
                        elements -> Output.parse(elements[1])
                ));
    }

    public static List<String> navigationLines(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static int[][] octopuses(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.chars()
                        .mapToObj(i -> (char) i)
                        .map(String::valueOf)
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }

    public static Map<String, List<String>> cavesGraph(String resourceName) throws IOException {
        Map<String, List<String>> graph = new HashMap<>();
        getInputFromFile(resourceName)
                .stream()
                .map(line -> line.split("-"))
                .forEach(strings -> {
                    addPath(graph, strings[0], strings[1]);
                    addPath(graph, strings[1], strings[0]);
                });
        return graph;
    }

    private static void addPath(Map<String, List<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, (key) -> new ArrayList<>()).add(to);
    }

    public static TransparentPageManual transparentPageManual(String resourceName) throws IOException {
        Iterator<String> lines = getInputFromFile(resourceName).iterator();
        String line = lines.next();
        Map<Integer, List<Integer>> dotsInMap = new HashMap<>();
        while (!line.isEmpty()) {
            String[] dotString = line.split(",");
            dotsInMap.computeIfAbsent(Integer.parseInt(dotString[0]), key -> new ArrayList<>()).add(Integer.parseInt(dotString[1]));
            line = lines.next();
        }
        List<TransparentPageManual.FoldInstruction> foldInstructions = new ArrayList<>();
        while (lines.hasNext()) {
            line = lines.next();
            line = line.replace("fold along ", "");
            String[] foldInstructionsLine = line.split("=");
            foldInstructions.add(
                    new TransparentPageManual.FoldInstruction(
                            foldInstructionsLine[0].equals("x") ? TransparentPageManual.Ax.X : TransparentPageManual.Ax.Y,
                            Integer.parseInt(foldInstructionsLine[1]))
            );
        }
        int width = dotsInMap.keySet().stream().mapToInt(i -> i + 1).max().orElse(0);
        int height = dotsInMap.values().stream().mapToInt(list -> list.stream().mapToInt(i -> i + 1).max().orElse(0)).max().orElse(0);
        return new TransparentPageManual(
                dotsInMap.entrySet().stream().flatMap(e ->
                        e.getValue().stream().map(v -> new Coordinates(e.getKey(), v, width, height))
                ).toList(),
                foldInstructions
        );
    }

    public static Polymer polymers(String resourceName) throws IOException {
        List<String> inputFromFile = getInputFromFile(resourceName);
        String polymer = inputFromFile.get(0);
        Map<String, String> collect = inputFromFile.subList(2, inputFromFile.size()).stream()
                .map(line -> line.split(" -> "))
                .collect(Collectors.toMap(
                        strings -> strings[0],
                        strings -> strings[1]
                ));
        return new Polymer(polymer, collect);
    }

    public static int[][] riskMap(String resourceName) throws IOException {
        return getInputFromFile(resourceName)
                .stream()
                .map(line -> line.chars().mapToObj(i -> (char) i).map(String::valueOf).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }

    public static List<Integer> bits(String resourceName) throws IOException {
        return bitsFromString(getInputFromFile(resourceName).get(0));
    }

    public static List<Integer> bitsFromString(String line) {
        final Map<Character, List<Integer>> hexadecimalCodes = new HashMap<>();
        hexadecimalCodes.put('0', List.of(0, 0, 0, 0));
        hexadecimalCodes.put('1', List.of(0, 0, 0, 1));
        hexadecimalCodes.put('2', List.of(0, 0, 1, 0));
        hexadecimalCodes.put('3', List.of(0, 0, 1, 1));
        hexadecimalCodes.put('4', List.of(0, 1, 0, 0));
        hexadecimalCodes.put('5', List.of(0, 1, 0, 1));
        hexadecimalCodes.put('6', List.of(0, 1, 1, 0));
        hexadecimalCodes.put('7', List.of(0, 1, 1, 1));
        hexadecimalCodes.put('8', List.of(1, 0, 0, 0));
        hexadecimalCodes.put('9', List.of(1, 0, 0, 1));
        hexadecimalCodes.put('A', List.of(1, 0, 1, 0));
        hexadecimalCodes.put('B', List.of(1, 0, 1, 1));
        hexadecimalCodes.put('C', List.of(1, 1, 0, 0));
        hexadecimalCodes.put('D', List.of(1, 1, 0, 1));
        hexadecimalCodes.put('E', List.of(1, 1, 1, 0));
        hexadecimalCodes.put('F', List.of(1, 1, 1, 1));

        return line
                .chars()
                .mapToObj(i -> hexadecimalCodes.get((char) i))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static Rectangle targetArea(String resourceName) throws IOException {
        String[] coordinateParts = getInputFromFile(resourceName).get(0)
                .replace("target area: ", "")
                .split(", ");
        int[] xes = Arrays.stream(coordinateParts[0].replace("x=", "").split("\\.\\.")).mapToInt(Integer::parseInt).toArray();
        int[] ys = Arrays.stream(coordinateParts[1].replace("y=", "").split("\\.\\.")).mapToInt(Integer::parseInt).toArray();
        int minX = Math.min(xes[0], xes[1]);
        int maxX = Math.max(xes[0], xes[1]);
        int minY = Math.min(ys[0], ys[1]);
        int maxY = Math.max(ys[0], ys[1]);
        return new Rectangle(
                Coordinates.ofInfiniteSpace(minX, maxY),
                Coordinates.ofInfiniteSpace(maxX, minY)
        );
    }

    public static List<CompoundSnailfishNumber> day18(String resourceName) throws IOException {
        SnailfishNumberParser parser = new SnailfishNumberParser();
        return getInputFromFile(resourceName)
                .stream()
                .map(parser::parse)
                .map(CompoundSnailfishNumber.class::cast)
                .collect(Collectors.toList());
    }

    public static Map<Integer, List<BeaconPosition>> scannersReport(String resourceName) throws IOException {
        Map<Integer, List<BeaconPosition>> result = new HashMap<>();
        int currentScanner = 0;
        for (String line : getInputFromFile(resourceName)) {
            if (line.contains("scanner")) {
                currentScanner = Integer.parseInt(line.replace("--- scanner ", "").replace(" ---", ""));
            } else if (!line.isBlank()) {
                String[] coordinates = line.split(",");
                result.computeIfAbsent(currentScanner, (key) -> new ArrayList<>()).add(new BeaconPosition(
                        Integer.parseInt(coordinates[0]),
                        Integer.parseInt(coordinates[1]),
                        Integer.parseInt(coordinates[2])
                ));
            }
        }
        return result;
    }

    public static List<String> day20(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    public static List<String> day21(String resourceName) throws IOException {
        return getInputFromFile(resourceName);
    }

    private static List<String> getInputFromFile(String resourceName) throws IOException {
        try (InputStreamReader in = new InputStreamReader(Objects.requireNonNull(Input.class.getResourceAsStream(resourceName)));
             BufferedReader reader = new BufferedReader(in)) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static <T> List<SamePair<T>> pairs(List<T> list) {
        List<SamePair<T>> pairs = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            T from = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                T to = list.get(j);
                pairs.add(samePair(from, to));
            }
        }
        return pairs;
    }
}
