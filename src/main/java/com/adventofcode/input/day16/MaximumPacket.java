package com.adventofcode.input.day16;

import java.util.List;

public class MaximumPacket extends OperatorPacket {

    public MaximumPacket(int version, int type, List<Packet> subPackets) {
        super(version, type, subPackets);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).max().orElseThrow();
    }
}
