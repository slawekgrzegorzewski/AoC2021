package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day18.CompoundSnailfishNumber;
import com.adventofcode.input.day18.SnailfishNumber;

import java.io.IOException;
import java.util.List;

public class Day18 {

    List<CompoundSnailfishNumber> numbers;

    public static void main(String[] input) throws IOException {
        Day18 day18 = new Day18();
        System.out.println("part1 = " + day18.part1());
        System.out.println("part2 = " + day18.part2());
    }

    public Day18() throws IOException {
        numbers = Input.day18("/day18");
    }

    long part1() {
        CompoundSnailfishNumber sum = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
//            System.out.println("sum = " + sum + "; adding = " + numbers.get(i));
            sum = sum.sum(numbers.get(i));
        }
        return sum.magnitude();
    }

    long part2() {
        System.out.println("part1() = " + part1());
        long max = 0;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if(i == j)continue;
//                System.out.println("(i, j) = (" + i + ", " + j + ")");
                long magnitude = numbers.get(i).sum(numbers.get(j)).magnitude();
                if(max < magnitude)
                    max = magnitude;
            }
        }
        return max;
    }
}

