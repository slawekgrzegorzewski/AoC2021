package com.adventofcode.input.day18;

public class SnailfishNumberParser {

    public SnailfishNumber parse(String input) {
        if (input.startsWith("[") && input.endsWith("]")) {
            return parseCompound(input);
        } else {
            return new RegularSnailfishNumber(Integer.parseInt(input));
        }
    }

    private SnailfishNumber parseCompound(String input) {
        input = input.substring(1, input.length() - 1);
        char[] chars = input.toCharArray();
        int splitIndex = findIndexOfZeroLevelSplit(chars);
        return new CompoundSnailfishNumber(
                parse(input.substring(0, splitIndex)),
                parse(input.substring(splitIndex + 1))
        );
    }

    private int findIndexOfZeroLevelSplit(char[] chars) {
        return findIndexOfNextComma(chars, findIndexOfEndOfFirstElementOfZeroLevel(chars));
    }

    private int findIndexOfEndOfFirstElementOfZeroLevel(char[] chars) {
        int splitIndex = -1;
        int nestLevel = 0;
        do {
            splitIndex++;
            if (chars[splitIndex] == '[') nestLevel++;
            if (chars[splitIndex] == ']') nestLevel--;
        } while (nestLevel > 0);
        return splitIndex;
    }

    private int findIndexOfNextComma(char[] chars, int splitIndex) {
        while (splitIndex < chars.length && chars[splitIndex] != ',')
            splitIndex++;
        return splitIndex;
    }
}
