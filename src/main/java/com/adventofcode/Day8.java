package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day8.Output;
import com.adventofcode.input.day8.OutputConverter;
import com.adventofcode.input.day8.Signal;
import com.adventofcode.input.day8.SignalAnalyzer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Day8 {

    Map<Signal, Output> signalsReads;

    public static void main(String[] input) throws IOException {
        Day8 day8 = new Day8();
        System.out.println("part1 = " + day8.part1());
        System.out.println("part2 = " + day8.part2());
    }

    public Day8() throws IOException {
        signalsReads = Input.signalsReads("/day8");
    }

    long part1() {
        return signalsReads.values().stream()
                .map(Output::elements)
                .flatMap(Arrays::stream)
                .filter(element -> isForUniqueNumber(element, 1) || isForUniqueNumber(element, 4) || isForUniqueNumber(element, 7) || isForUniqueNumber(element, 8))
                .count();
    }

    private boolean isForUniqueNumber(String element, int number) {
        if (number != 1 && number != 4 && number != 7 && number != 8) return false;
        return element.length() == SignalAnalyzer.numberToSegmentsCount.get(number);
    }

    int part2() {
        return signalsReads.entrySet()
                .stream()
                .mapToInt(entry -> {
                    SignalAnalyzer signalAnalyzer = new SignalAnalyzer(entry.getKey());
                    signalAnalyzer.analyze();
                    Map<Character, Character> mappings = signalAnalyzer.mappings();
                    return new OutputConverter(mappings).convert(entry.getValue());
                })
                .sum();
    }

    int exampleInput() {
        Signal s = Signal.parse("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab");
        Output o = Output.parse("cdfeb fcadb cdfeb cdbaf");
        SignalAnalyzer signalAnalyzer = new SignalAnalyzer(s);
        signalAnalyzer.analyze();
        Map<Character, Character> mappings = signalAnalyzer.mappings();
        return new OutputConverter(mappings).convert(o);
    }
}

