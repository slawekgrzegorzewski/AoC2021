package com.adventofcode.input.day18;

import static java.util.Optional.ofNullable;

public abstract class SnailfishNumber {

    protected CompoundSnailfishNumber parent;

    public SnailfishNumber(CompoundSnailfishNumber parent) {
        this.parent = parent;
    }

    public void setParent(CompoundSnailfishNumber parent) {
        this.parent = parent;
    }

    public CompoundSnailfishNumber getRoot() {
        return ofNullable(this.parent)
                .map(SnailfishNumber::getRoot)
                .orElseGet(() -> (CompoundSnailfishNumber) this);
    }

    public abstract long magnitude();

    public abstract SnailfishNumber copy();
}
