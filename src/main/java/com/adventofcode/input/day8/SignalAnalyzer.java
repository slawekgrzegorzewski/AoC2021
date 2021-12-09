package com.adventofcode.input.day8;

import java.util.*;
import java.util.stream.Collectors;

public class SignalAnalyzer {

    public static final Map<Integer, Integer> numberToSegmentsCount = Map.of(
            0, 6,
            1, 2,
            2, 5,
            3, 5,
            4, 4,
            5, 5,
            6, 6,
            7, 3,
            8, 7,
            9, 6
    );

    private final Map<Character, Character> mappings = new HashMap<>();

    private String signalForZero;
    private String signalForOne;
    private String signalForTwo;
    private String signalForThree;
    private String signalForFour;
    private String signalForFive;
    private String signalForSix;
    private String signalForSeven;
    private String signalForEight;
    private String signalForNine;

    public SignalAnalyzer(Signal signal) {
        mappings.clear();
        mappings.put('a', null);
        mappings.put('b', null);
        mappings.put('c', null);
        mappings.put('d', null);
        mappings.put('e', null);
        mappings.put('f', null);
        mappings.put('g', null);

        signalForOne = getSingleSignalOfLength(signal, numberToSegmentsCount.get(1));
        signalForFour = getSingleSignalOfLength(signal, numberToSegmentsCount.get(4));
        signalForSeven = getSingleSignalOfLength(signal, numberToSegmentsCount.get(7));
        signalForEight = getSingleSignalOfLength(signal, numberToSegmentsCount.get(8));

        List<String> signalsForTwoThreeFive = getSignalsOfLength(signal, numberToSegmentsCount.get(2));
        List<String> signalsForZeroSixNine = getSignalsOfLength(signal, numberToSegmentsCount.get(0));

        signalForThree = signalsForTwoThreeFive.stream().filter(s -> firstContainsFullSecond(s, signalForOne)).findFirst().orElseThrow();
        signalForSix = signalsForZeroSixNine.stream().filter(s -> !firstContainsFullSecond(s, signalForOne)).findFirst().orElseThrow();
        signalForNine = signalsForZeroSixNine.stream().filter(s -> firstContainsFullSecond(s, signalForFour)).findFirst().orElseThrow();

        signalsForTwoThreeFive.remove(signalForThree);
        signalsForZeroSixNine.remove(signalForSix);
        signalsForZeroSixNine.remove(signalForNine);

        signalForZero = signalsForZeroSixNine.get(0);
        signalForFive = signalsForTwoThreeFive.stream().filter(s -> firstMinusSecond(signalForSix, s).size() == 1).findFirst().orElseThrow();
        signalsForTwoThreeFive.remove(signalForFive);
        signalForTwo = signalsForTwoThreeFive.get(0);
    }

    public void analyze() {
        mappingFound('a', singleFirstMinusSecond(signalForSeven, signalForOne));
        mappingFound('f', singleFirstMinusSecond(signalForOne, signalForTwo));
        mappingFound('c', signalForOne.charAt(0));
        mappingFound('e', singleFirstMinusSecond(signalForTwo, signalForThree));
        mappingFound('g', singleFirstMinusSecond(signalForFive, signalForFour));
        mappingFound('d', signalForTwo.charAt(0));
        mappingFound('b', signalForFour.charAt(0));
    }

    public Map<Character, Character> mappings() {
        return mappings.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        Map.Entry::getKey
                ));
    }

    private void mappingFound(char forSegment, char mapping) {
        mappings.put(forSegment, mapping);
        signalForZero = signalForZero.replace(String.valueOf(mapping), "");
        signalForOne = signalForOne.replace(String.valueOf(mapping), "");
        signalForTwo = signalForTwo.replace(String.valueOf(mapping), "");
        signalForThree = signalForThree.replace(String.valueOf(mapping), "");
        signalForFour = signalForFour.replace(String.valueOf(mapping), "");
        signalForFive = signalForFive.replace(String.valueOf(mapping), "");
        signalForSix = signalForSix.replace(String.valueOf(mapping), "");
        signalForSeven = signalForSeven.replace(String.valueOf(mapping), "");
        signalForEight = signalForEight.replace(String.valueOf(mapping), "");
        signalForNine = signalForNine.replace(String.valueOf(mapping), "");
    }

    private String getSingleSignalOfLength(Signal signal, int length) {
        List<String> signalsOfLength = getSignalsOfLength(signal, length);
        if (signalsOfLength.size() != 1) throw notUniqueException();
        return signalsOfLength.get(0);
    }

    private List<String> getSignalsOfLength(Signal signal, int length) {
        return new ArrayList<>(Arrays.stream(signal.elements()).filter(e -> e.length() == length).toList());
    }

    private char singleFirstMinusSecond(String first, String second) {
        Set<Character> result = firstMinusSecond(first, second);
        if (result.size() != 1) throw notUniqueException();
        return result.iterator().next();
    }

    private Set<Character> firstMinusSecond(String first, String second) {
        Set<Character> firstChars = toCharSet(first);
        Set<Character> secondChars = toCharSet(second);
        firstChars.removeAll(secondChars);
        return firstChars;
    }


    private boolean firstContainsFullSecond(String first, String second) {
        return firstMinusSecond(second, first).isEmpty();
    }

    private RuntimeException notUniqueException() {
        return new RuntimeException("Not a signle result");
    }

    private Set<Character> toCharSet(String first) {
        Set<Character> result = new HashSet<>();
        for (char c : first.toCharArray()) {
            result.add(c);
        }
        return result;
    }
}
