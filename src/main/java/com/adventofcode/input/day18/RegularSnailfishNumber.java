package com.adventofcode.input.day18;

public class RegularSnailfishNumber extends SnailfishNumber {

    private final long value;

    public RegularSnailfishNumber(long value) {
        this(value, null);
    }

    public RegularSnailfishNumber(long value, CompoundSnailfishNumber parent) {
        super(parent);
        this.value = value;
    }

    public RegularSnailfishNumber add(RegularSnailfishNumber right) {
        return new RegularSnailfishNumber(value + right.value);
    }

    public long value() {
        return value;
    }

    @Override
    public long magnitude() {
        return value;
    }

    @Override
    public SnailfishNumber copy() {
        return new RegularSnailfishNumber(value, parent);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
