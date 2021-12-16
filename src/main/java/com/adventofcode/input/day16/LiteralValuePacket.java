package com.adventofcode.input.day16;

public class LiteralValuePacket implements Packet {
    private final int version;
    private final int type;
    private final long value;

    public LiteralValuePacket(int version, int type, long value) {
        this.version = version;
        this.type = type;
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public long getValue() {
        return value;
    }
}
