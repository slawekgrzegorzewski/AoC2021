package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day19.Pair;
import com.adventofcode.input.day19.SamePair;
import com.adventofcode.input.day22.CubeState;
import com.adventofcode.input.day22.Cuboid;
import com.adventofcode.input.day22.SameTriple;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adventofcode.input.day19.Pair.pair;
import static com.adventofcode.input.day22.CubeState.OFF;
import static com.adventofcode.input.day22.CubeState.ON;
import static com.adventofcode.input.day22.SameTriple.sameTriple;
import static java.util.Optional.empty;

public class Day22 {

    List<Pair<SameTriple<SamePair<Long>>, CubeState>> instructions;

    public static void main(String[] input) throws IOException {

        List<CubeState> a = List.of(ON, OFF, OFF, ON, OFF);
        a.stream().sorted().forEach(System.out::println);
        Day22 day22 = new Day22();
        System.out.println("part1 = " + day22.part1());
        System.out.println("part2 = " + day22.part2());
    }

    public Day22() throws IOException {
        instructions = Input.day22("/day22");
    }

    long part1() {
        Set<SameTriple<Long>> onCubes = new HashSet<>();
        for (Pair<SameTriple<SamePair<Long>>, CubeState> instruction : instructions) {
            SamePair<Long> x = instruction.first().first();
            SamePair<Long> y = instruction.first().second();
            SamePair<Long> z = instruction.first().third();
            for (long i = from(x); i <= to(x); i++) {
                for (long j = from(y); j <= to(y); j++) {
                    for (long k = from(z); k <= to(z); k++) {
                        if (instruction.second() == ON) {
                            onCubes.add(sameTriple(i, j, k));
                        } else {
                            onCubes.remove(sameTriple(i, j, k));
                        }
                    }
                }
            }
        }
        return onCubes.size();
    }

    private long to(SamePair<Long> coordinates) {
        return Math.min(50L, coordinates.second());
    }

    private long from(SamePair<Long> coordinates) {
        return Math.max(-50L, coordinates.first());
    }

    long part2() {
        List<Pair<Cuboid, CubeState>> cuboids = instructions.stream()
                .map(i -> {
                    SameTriple<SamePair<Long>> coords = i.first();
                    return pair(new Cuboid(coords.first().first(), coords.second().first(), coords.third().first(),
                                    coords.first().second(), coords.second().second(), coords.third().second()),
                            i.second());
                }).collect(Collectors.toCollection(ArrayList::new));
        return count(cuboids);
    }

    long count(List<Pair<Cuboid, CubeState>> cubes) {
        if (cubes == null || cubes.isEmpty())
            return 0;
        Pair<Cuboid, CubeState> first = cubes.remove(0);
        if (first.second() == OFF) return count(cubes);
        List<Pair<Cuboid, CubeState>> tail = cubes.stream()
                .map(c -> pair(intersect(first.first(), c.first()), (CubeState) null))
                .filter(p -> p.first().isPresent())
                .map(p -> pair(p.first().get(), p.second()))
                .collect(Collectors.toCollection(ArrayList::new));
        return volume(first.first())
                + count(cubes)
                - count(tail);
    }

    Optional<Cuboid> intersect(Cuboid xyz, Cuboid uvw) {
        long x = Math.max(xyz.fromX(), uvw.fromX());
        long X = Math.min(xyz.toX(), uvw.toX());
        long y = Math.max(xyz.fromY(), uvw.fromY());
        long Y = Math.min(xyz.toY(), uvw.toY());
        long z = Math.max(xyz.fromZ(), uvw.fromZ());
        long Z = Math.min(xyz.toZ(), uvw.toZ());
        return (x <= X && y <= Y && z <= Z) ? Cuboid.of(x, X, y, Y, z, Z) :
                empty();
    }

    long volume(Cuboid xyz) {
        return (xyz.toX() - xyz.fromX() + 1) * (xyz.toY() - xyz.fromY() + 1) * (xyz.toZ() - xyz.fromZ() + 1);
    }
}

