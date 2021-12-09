package com.adventofcode;

import com.adventofcode.input.BinaryInput;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {

    private final List<BinaryInput> diagnosticLines;

    public static void main(String[] input) throws IOException {
        Day3 day3 = new Day3();
        System.out.println("part1 = " + day3.part1());
        System.out.println("part2 = " + day3.part2());
    }

    public Day3() throws IOException {
        this.diagnosticLines = Input.binaryLines("/day3");
    }

    int part1() {
        int wordLength = diagnosticLines.get(0).length();
        Map<Integer, Integer> zeroCountOnEachPosition = IntStream.range(0, wordLength)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> 0));
        for (BinaryInput binaryInput : diagnosticLines) {
            for (int i = 0; i < binaryInput.length(); i++) {
                zeroCountOnEachPosition
                        .compute(i, (key, oldValue) -> oldValue + (binaryInput.isZero(key) ? 1 : 0));
            }
        }
        byte[] gammaParts = new byte[wordLength];
        int threshold = diagnosticLines.size() / 2;
        for (int i = 0; i < wordLength; i++) {
            if (zeroCountOnEachPosition.get(i) > threshold) {
                gammaParts[i] = (byte) 0;
            } else {
                gammaParts[i] = (byte) 1;
            }
        }
        int gamma = new BinaryInput(gammaParts).value();
        int epsilon = new BinaryInput(gammaParts).inverse().value();
        return gamma * epsilon;
    }

    int part2() {
        int oxygenGeneratorRating =
                calculate(0, diagnosticLines, (zeros, ones) -> zeros.size() > ones.size() ? zeros : ones)
                        .get(0)
                        .value();
        int co2ScrubberRating =
                calculate(0, diagnosticLines, (zeros, ones) -> ones.size() < zeros.size() ? ones : zeros)
                        .get(0)
                        .value();
        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private List<BinaryInput> calculate(int index,
                                        List<BinaryInput> diagnosticLines,
                                        BiFunction<List<BinaryInput>, List<BinaryInput>, List<BinaryInput>> listPicker) {
        Map<Integer, List<BinaryInput>> split = diagnosticLines.stream().collect(Collectors.groupingBy(dl -> dl.isZero(index) ? 0 : 1));
        diagnosticLines = listPicker.apply(split.get(0), split.get(1));
        if (diagnosticLines.size() == 1 || index > diagnosticLines.get(0).length()) {
            return List.of(diagnosticLines.get(0));
        } else {
            return calculate(index + 1, diagnosticLines, listPicker);
        }
    }
}

