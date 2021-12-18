package com.adventofcode.input.day18;

import java.util.LinkedList;
import java.util.stream.Stream;

public class CompoundSnailfishNumber extends SnailfishNumber {
    private SnailfishNumber left;
    private SnailfishNumber right;
    private final LinkedList<RegularSnailfishNumber> orderOfNumbers = new LinkedList<>();

    public CompoundSnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
        super(null);
        this.left = left;
        this.right = right;
        this.left.setParent(this);
        this.right.setParent(this);
    }

    public LinkedList<RegularSnailfishNumber> orderOfNumbers() {
        if (this.orderOfNumbers.isEmpty())
            recreateOrderOfRegularNumbers();
        return orderOfNumbers;
    }

    public void recreateOrderOfRegularNumbers() {
        this.orderOfNumbers.clear();
        addNodeToOrder(this);
    }

    private void addNodeToOrder(SnailfishNumber number) {
        if (number instanceof RegularSnailfishNumber) {
            orderOfNumbers.add((RegularSnailfishNumber) number);
        } else {
            CompoundSnailfishNumber compoundSnailfishNumber = (CompoundSnailfishNumber) number;
            Stream.of(
                            compoundSnailfishNumber.left,
                            compoundSnailfishNumber.right
                    )
                    .filter(node -> node instanceof CompoundSnailfishNumber)
                    .map(CompoundSnailfishNumber.class::cast)
                    .forEach(CompoundSnailfishNumber::recreateOrderOfRegularNumbers);
            addNodeToOrder(compoundSnailfishNumber.left);
            addNodeToOrder(compoundSnailfishNumber.right);
        }
    }

    public CompoundSnailfishNumber sum(CompoundSnailfishNumber other) {
        CompoundSnailfishNumber copyOfThis = this.copy();
        CompoundSnailfishNumber copyOfOther = other.copy();
        CompoundSnailfishNumber compoundSnailfishNumber = new CompoundSnailfishNumber(copyOfThis, copyOfOther);
        this.parent = compoundSnailfishNumber;
        copyOfOther.setParent(compoundSnailfishNumber);
        new SnailfishNumberReducer(compoundSnailfishNumber).reduce();
        return compoundSnailfishNumber;
    }

    public SnailfishNumber left() {
        return left;
    }

    public void setLeft(SnailfishNumber left) {
        this.left = left;
    }

    public SnailfishNumber right() {
        return right;
    }

    public void setRight(SnailfishNumber right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left + "," + right + "]";
    }

    @Override
    public long magnitude() {
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    @Override
    public CompoundSnailfishNumber copy() {
        CompoundSnailfishNumber copy = new CompoundSnailfishNumber(
                left.copy(),
                right.copy()
        );
        copy.parent = parent;
        if (parent == null) copy.recreateOrderOfRegularNumbers();
        return copy;
    }
}
