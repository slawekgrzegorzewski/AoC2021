package com.adventofcode.input.day16;

import java.util.List;

public class ProductPacket extends OperatorPacket {

    public ProductPacket(int version, int type, List<Packet> subPackets) {
        super(version, type, subPackets);
    }

    @Override
    public long getValue() {
        return getSubPackets().stream().mapToLong(Packet::getValue).reduce(1, (left, right) -> left * right);
    }
}
