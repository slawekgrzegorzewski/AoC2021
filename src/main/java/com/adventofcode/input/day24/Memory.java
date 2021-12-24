package com.adventofcode.input.day24;

import java.util.Objects;

public class Memory {
    private long w, x, y, z;

    public Memory() {
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public long w() {
        return w;
    }

    public void setW(long w) {
        this.w = w;
    }

    public long x() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long y() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long z() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memory memory = (Memory) o;
        return w == memory.w && x == memory.x && y == memory.y && z == memory.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(w, x, y, z);
    }

    @Override
    public String toString() {
        return "(" + w + ", " + x + ", " + y + ", " + z + '}';
    }

    public Memory copy() {
        Memory memory = new Memory();
        memory.w = w;
        memory.x = x;
        memory.y = y;
        memory.z = z;
        return memory;
    }
}
