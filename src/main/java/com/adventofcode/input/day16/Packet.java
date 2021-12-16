package com.adventofcode.input.day16;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.LongStream;

public interface Packet {
    static Packet parse(LinkedList<Integer> bits) {
        int version = (int) convertNFirstBitsToNumber(bits, 3);
        int type = (int) convertNFirstBitsToNumber(bits, 3);

        if (type == 4) {
            int firstBit = 1;
            LinkedList<Integer> encodedNumberBits = new LinkedList<>();
            while (firstBit == 1) {
                firstBit = bits.removeFirst();
                for (int i = 0; i < 4; i++)
                    encodedNumberBits.addLast(bits.removeFirst());
            }
            return new LiteralValuePacket(version, type, convertListToNumber(encodedNumberBits));
        } else {
            int lengthTypeId = bits.removeFirst();
            if (lengthTypeId == 0) {
                long totalLength = convertNFirstBitsToNumber(bits, 15);
                LinkedList<Integer> subPacketsBits = new LinkedList<>();
                List<Packet> packets = new ArrayList<>();
                for (long i = 0; i < totalLength; i++) {
                    subPacketsBits.addLast(bits.removeFirst());
                }
                while (subPacketsBits.size() > 3) {
                    packets.add(Packet.parse(subPacketsBits));
                }
                System.out.println("totalLength = " + totalLength);
                return OperatorPacket.of(version, type, packets);
            } else {
                long totalSubPackets = convertNFirstBitsToNumber(bits, 11);
                List<Packet> packets = LongStream.range(0, totalSubPackets)
                        .mapToObj(i -> Packet.parse(bits))
                        .toList();
                System.out.println("totalSubPackets = " + totalSubPackets);
                return OperatorPacket.of(version, type, packets);
            }
        }
    }

    private static long convertListToNumber(LinkedList<Integer> encodedNumberBits) {
        return convertNFirstBitsToNumber(encodedNumberBits, encodedNumberBits.size());
    }

    private static long convertNFirstBitsToNumber(LinkedList<Integer> bits, int n) {
        long value = 0;
        for (int i = n - 1; i >= 0; i--)
            value += bits.removeFirst() * Math.pow(2, i);
        return value;
    }


    int getVersion();

    int getType();

    long getValue();
}
