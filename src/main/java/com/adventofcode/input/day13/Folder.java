package com.adventofcode.input.day13;

import com.adventofcode.input.Coordinates;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

public class Folder {
    private final char[][] page;
    private int width;
    private int height;

    public Folder(char[][] page, int width, int height) {
        this.page = page;
        this.width = width;
        this.height = height;
    }

    public void fold(TransparentPageManual.FoldInstruction foldInstruction) {
        List<Coordinates> coordinates = Coordinates.walkTroughAllPointsVertically(width, height);
        for (Coordinates coordinate : coordinates) {
            if (isAfter(coordinate, foldInstruction) && coordinate.getValue(page) == '.') {
                Optional<Coordinates> newDot = switch (foldInstruction.ax()) {
                    case X -> coordinate.left(2 * (coordinate.x() - foldInstruction.index()));
                    case Y -> coordinate.up(2 * (coordinate.y() - foldInstruction.index()));
                };
                newDot.ifPresent(c -> c.setValue(page, '.'));
            }
        }
        switch (foldInstruction.ax()) {
            case X -> width = foldInstruction.index();
            case Y -> height = foldInstruction.index();
        }
    }

    private boolean isAfter(Coordinates coordinate, TransparentPageManual.FoldInstruction foldInstruction) {
        return of(foldInstruction.ax())
                .map(ax -> ax == TransparentPageManual.Ax.X ? coordinate.x() : coordinate.y())
                .map(currentIndex -> currentIndex > foldInstruction.index())
                .orElse(false);
    }

    public void print() {
        for (int i = 0; i < height; i++) {
            for (int ii = 0; ii < width; ii++) {
                System.out.print(page[i][ii] == '.' ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }

    public int countDots() {
        return Coordinates.walkTroughAllPointsVertically(width, height)
                .stream()
                .mapToInt(c -> c.getValue(page) == '.' ? 1 :0)
                .sum();
    }
}
