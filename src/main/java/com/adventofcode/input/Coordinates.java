package com.adventofcode.input;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public record Coordinates(int x, int y, int width, int height, int minX, int minY) {

    public Coordinates(int x, int y, int width, int height) {
        this(x, y, width, height, 0, 0);
    }

    public static List<Coordinates> walkTroughAllPoints(int width, int height) {
        return IntStream.range(0, width)
                .mapToObj(x -> IntStream.range(0, height).mapToObj(y -> new Coordinates(x, y, width, height)))
                .flatMap(a -> a)
                .collect(Collectors.toList());
    }

    public static Coordinates ofInfiniteSpace(int x, int y) {
        return new Coordinates(x, y, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public int getValue(int[][] array) {
        return array[y()][x()];
    }

    public void setValue(int[][] array, int value) {
        array[y()][x()] = value;
    }

    public char getValue(char[][] array) {
        return array[y()][x()];
    }

    public void setValue(char[][] array, char value) {
        array[y()][x()] = value;
    }

    public Optional<Coordinates> up() {
        return up(1);
    }

    public Optional<Coordinates> up(int count) {
        if (count < 0) {
            return down(-count);
        }
        if (y - count >= minY) {
            return of(new Coordinates(x, y - count, width, height, minX, minY));
        }
        return empty();
    }

    public Optional<Coordinates> down() {
        return down(1);
    }

    public Optional<Coordinates> down(int count) {
        if (count < 0) {
            return up(-count);
        }
        if (y < height - count) {
            return of(new Coordinates(x, y + count, width, height, minX, minY));
        }
        return empty();
    }

    public Optional<Coordinates> left() {
        return left(1);
    }

    public Optional<Coordinates> left(int count) {
        if (count < 0) {
            return right(-count);
        }
        if (x - count >= minX) {
            return of(new Coordinates(x - count, y, width, height, minX, minY));
        }
        return empty();
    }

    public Optional<Coordinates> right() {
        return right(1);
    }

    public Optional<Coordinates> right(int count) {
        if (count < 0) {
            return left(-count);
        }
        if (x < width - count) {
            return of(new Coordinates(x + count, y, width, height, minX, minY));
        }
        return empty();
    }

    public List<Coordinates> adjacentCellsWithDiagonals() {
        return Stream.of(this.up().flatMap(Coordinates::left),
                        this.up(),
                        this.up().flatMap(Coordinates::right),
                        this.left(),
                        this.right(),
                        this.down().flatMap(Coordinates::left),
                        this.down().flatMap(Coordinates::right),
                        this.down())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public List<Coordinates> adjacentCellsWithoutDiagonals() {
        return Stream.of(this.up(),
                        this.left(),
                        this.right(),
                        this.down())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
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
