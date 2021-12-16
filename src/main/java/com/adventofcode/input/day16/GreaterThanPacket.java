package com.adventofcode.input.day16;

import java.util.List;

public class GreaterThanPacket extends OperatorPacket {

    public GreaterThanPacket(int version, int type, List<Packet> subPackets) {
        super(version, type, subPackets);
    }

    @Override
    public long getValue() {
        if(getSubPackets().size() != 2){
            throw new RuntimeException("This packet type expects to have exactly two subpackets");
        }
        return (getSubPackets().get(0).getValue() > getSubPackets().get(1).getValue()) ? 1 : 0;
    }
}
