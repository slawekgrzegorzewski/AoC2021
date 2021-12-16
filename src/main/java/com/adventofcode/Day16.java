package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day16.OperatorPacket;
import com.adventofcode.input.day16.Packet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Day16 {

    private final Packet from;

    public static void main(String[] input) throws IOException {
        Day16 day16 = new Day16();
        System.out.println("part1 = " + day16.part1());
        System.out.println("part2 = " + day16.part2());
    }

    public Day16() throws IOException {
        this(new LinkedList<>(Input.bits("/day16")));
    }

    public Day16(List<Integer> bits) {
        from = Packet.parse(new LinkedList<>(bits));
    }

    int part1() {
        return sumVersions(from);
    }

    long part2() {
        return from.getValue();
    }

    int sumVersions(Packet p) {
        if (p instanceof OperatorPacket) {
            return ((OperatorPacket) p).getSubPackets()
                    .stream()
                    .mapToInt(this::sumVersions)
                    .sum() + p.getVersion();
        } else {
            return p.getVersion();
        }
    }
}

