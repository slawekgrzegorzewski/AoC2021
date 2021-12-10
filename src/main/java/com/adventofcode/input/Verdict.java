package com.adventofcode.input;

public class Verdict {
    public enum LineType {
        INCORRECT, INCOMPLETE, CORRECT

    }
    private final LineType lineType;
    private final long score;

    public Verdict(LineType lineType, long score) {
        this.lineType = lineType;
        this.score = score;
    }

    public LineType lineType() {
        return lineType;
    }

    public long score() {
        return score;
    }
}
