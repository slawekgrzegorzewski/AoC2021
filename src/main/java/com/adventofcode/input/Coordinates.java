package com.adventofcode.input;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Coordinates {
    private final int x, y;
    private final int width, height;

    public static List<Coordinates> walkTroughAllPoints(int width, int height) {
        return IntStream.range(0, width)
                .mapToObj(x -> IntStream.range(0, height).mapToObj(y -> new Coordinates(x, y, width, height)))
                .flatMap(a -> a)
                .collect(Collectors.toList());
    }

    public Coordinates(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int getValue(int[][] array) {
        return array[x()][y()];
    }

    public Optional<Coordinates> up() {
        if (y > 0) {
            return of(new Coordinates(x, y - 1, width, height));
        }
        return empty();
    }

    public Optional<Coordinates> down() {
        if (y < height - 1) {
            return of(new Coordinates(x, y + 1, width, height));
        }
        return empty();
    }

    public Optional<Coordinates> left() {
        if (x > 0) {
            return of(new Coordinates(x - 1, y, width, height));
        }
        return empty();
    }

    public Optional<Coordinates> right() {
        if (x < width - 1) {
            return of(new Coordinates(x + 1, y, width, height));
        }
        return empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
