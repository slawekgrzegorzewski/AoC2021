package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Arrays;

public class Day25 {

    char[][] seaCucumbersMap;

    public static void main(String[] input) throws IOException {
        Day25 day25 = new Day25();
        System.out.println("part1 = " + day25.part1());
        System.out.println("part2 = " + day25.part2());
    }

    public Day25() throws IOException {
        seaCucumbersMap = Input.seaCucumbersMap("/day25");
    }

    int part1() {
        char[][] seaCucumbersMap = copyOfSeaCucumbersMap();
        int steps = 0;
        int movesInStep;
        do {
            movesInStep = step(seaCucumbersMap);
            steps++;
        } while (movesInStep > 0);
        return steps;
    }

    int part2() {
        return 0;
    }

    private int step(char[][] map) {
        int height = map.length;
        int width = map[0].length;
        int moves = 0;
        for (int h = 0; h < height; h++) {
            boolean possibleToWrap = map[h][0] == '.';
            for (int w = 0; w < width; w++) {
                if (map[h][w] != '>') continue;
                int nextCell = w + 1 == width ? 0 : w + 1;
                boolean nextCellFree = nextCell == 0 ? possibleToWrap : map[h][nextCell] == '.';
                if (nextCellFree) {
                    moves++;
                    map[h][w] = '.';
                    map[h][nextCell] = '>';
                    w = nextCell == 0 ? width : nextCell;
                }
            }
        }

        for (int w = 0; w < width; w++) {
            boolean possibleToWrap = map[0][w] == '.';
            for (int h = 0; h < height; h++) {
                if (map[h][w] != 'v') continue;
                int nextCell = h + 1 == height ? 0 : h + 1;
                boolean nextCellFree = nextCell == 0 ? possibleToWrap : map[nextCell][w] == '.';
                if (nextCellFree) {
                    moves++;
                    map[h][w] = '.';
                    map[nextCell][w] = 'v';
                    h = nextCell == 0 ? height : nextCell;
                }
            }
        }
        return moves;
    }

    private char[][] copyOfSeaCucumbersMap() {
        return copy(seaCucumbersMap);
    }

    private char[][] copy(char[][] seaCucumbersMap) {
        char[][] workingCopy = new char[seaCucumbersMap.length][];
        for (int i = 0; i < seaCucumbersMap.length; i++) {
            workingCopy[i] = Arrays.copyOf(seaCucumbersMap[i], seaCucumbersMap[i].length);
        }
        return workingCopy;
    }

    private void print(char[][] map) {
        print(map, 0);
    }

    private void print(char[][] map, int steps) {
        if (steps == 0)
            System.out.println("Initial state:");
        else
            System.out.println("After " + steps + " step" + (steps > 1 ? "s:" : ":"));
        for (char[] chars : map) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        System.out.println();
    }
}

