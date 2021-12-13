package com.adventofcode.input.day13;

import com.adventofcode.input.Coordinates;

import java.util.List;

public record TransparentPageManual(List<Coordinates> dots,
                                    List<FoldInstruction> foldInstructions) {

    public enum Ax {
        X, Y
    }

    public record FoldInstruction(Ax ax, int index) {

    }

}
