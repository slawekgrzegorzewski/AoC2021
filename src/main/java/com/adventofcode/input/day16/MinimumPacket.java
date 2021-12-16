package com.adventofcode.input.day16;

import java.util.List;

public class MinimumPacket extends OperatorPacket {

    public MinimumPacket(int version, int type, List<Packet> subPackets) {
        super(version, type, subPackets);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).min().orElseThrow();
    }
}
