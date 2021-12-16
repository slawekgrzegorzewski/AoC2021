package com.adventofcode.input.day16;

import java.util.List;

public abstract class OperatorPacket implements Packet {
    private final int version;
    private final int type;
    private final List<Packet> subPackets;

    public OperatorPacket(int version, int type, List<Packet> subPackets) {
        this.version = version;
        this.type = type;
        this.subPackets = subPackets;
    }

    public static Packet of(int version, int type, List<Packet> packets) {
        return switch (type) {
            case 0 -> new SumPacket(version, type, packets);
            case 1 -> new ProductPacket(version, type, packets);
            case 2 -> new MinimumPacket(version, type, packets);
            case 3 -> new MaximumPacket(version, type, packets);
            case 5 -> new GreaterThanPacket(version, type, packets);
            case 6 -> new LessThanPacket(version, type, packets);
            case 7 -> new EqualToPacket(version, type, packets);
            default -> throw new RuntimeException("Not know type " + type);
        };
    }

    public int getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public List<Packet> getSubPackets() {
        return subPackets;
    }
}
