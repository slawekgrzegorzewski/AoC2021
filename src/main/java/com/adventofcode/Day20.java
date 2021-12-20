package com.adventofcode;

import com.adventofcode.input.Coordinates;
import com.adventofcode.input.Input;
import com.adventofcode.input.day19.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.adventofcode.Utils.*;
import static com.adventofcode.input.day19.Pair.pair;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public class Day20 {

    private static final char LIGHT_PIXEL = '#';
    private static final char DARK_PIXEL = '.';

    private final String algorithm;
    private final char[][] originalImage;

    private char restOfPixels = DARK_PIXEL;

    public static void main(String[] input) throws IOException {
        Day20 day20 = new Day20();
        System.out.println("part1 = " + day20.part1());
        System.out.println("part2 = " + day20.part2());
    }

    public Day20() throws IOException {
        Pair<String, char[][]> algorithmAndImage = Input.day20("/day20");
        this.algorithm = algorithmAndImage.first();
        this.originalImage = algorithmAndImage.second();
    }

    long part1() {
        return countLightPixels(enhanceNTimes(2));
    }

    long part2() {
        return countLightPixels(enhanceNTimes(50));
    }

    private char[][] enhanceNTimes(int n) {
        char[][] enhanced = addLinesFromInfiniteSpace(originalImage, n);
        for (int i = 0; i < n; i++) {
            enhanced = enhance(enhanced);
        }
        return enhanced;
    }

    private char[][] addLinesFromInfiniteSpace(char[][] originalImage, int numberOfLines) {
        char[][] result = createBackgroundArray(originalImage, numberOfLines);
        for (int i = numberOfLines; i < numberOfLines + originalImage.length; i++) {
            System.arraycopy(originalImage[i - numberOfLines], 0, result[i], numberOfLines, originalImage.length);
        }
        return result;
    }

    private char[][] enhance(char[][] image) {
        char[][] result = createBackgroundArray(image, 0);
        Coordinates.walkTroughAllPointsHorizontally(image[0].length, image.length)
                .stream()
                .map(c -> pair(c, getIndex(image, c)))
                .forEach(pairConsumer((c, indexInAlgorithm) ->
                        c.setValue(result, algorithm.charAt(indexInAlgorithm))
                ));
        restOfPixels = algorithm.charAt(restOfPixels == DARK_PIXEL ? 0 : 511);
        return result;
    }

    private char[][] createBackgroundArray(char[][] originalImage, int numberOfLines) {
        char[][] result = new char[originalImage.length + 2 * numberOfLines][];
        for (int i = 0; i < result.length; i++) {
            result[i] = new char[originalImage[0].length + 2 * numberOfLines];
            Arrays.fill(result[i], restOfPixels);
        }
        return result;
    }

    private Integer getIndex(char[][] workingCopyOfImage, Coordinates c) {
        return indexedStream(
                List.of(
                        c.downRight(), c.down(), c.downLeft(),
                        c.right(), of(c), c.left(),
                        c.upRight(), c.up(), c.upLeft()))
                .map(pairMapper((index, coordinates) -> coordinates.orElse(null)))
                .map(valueMapper(positionValue(workingCopyOfImage)))
                .mapToInt(pairMapperToInt((index, positionNumber) -> (int) (Math.pow(2, index) * positionNumber)))
                .sum();
    }

    private Function<Coordinates, Integer> positionValue(char[][] workingCopyOfImage) {
        return coordinates -> ofNullable(coordinates)
                .map(c1 -> c1.getValue(workingCopyOfImage))
                .map(c1 -> c1 == DARK_PIXEL ? 0 : 1)
                .orElse(restOfPixels == DARK_PIXEL ? 0 : 1);
    }

    private long countLightPixels(char[][] enhanced) {
        return Coordinates.walkTroughAllPointsVertically(enhanced[0].length, enhanced.length)
                .stream()
                .filter(c -> c.getValue(enhanced) == LIGHT_PIXEL)
                .count();
    }
}

