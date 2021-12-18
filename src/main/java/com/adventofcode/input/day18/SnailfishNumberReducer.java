package com.adventofcode.input.day18;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.Optional.of;

public class SnailfishNumberReducer {

    private final CompoundSnailfishNumber number;

    public SnailfishNumberReducer(CompoundSnailfishNumber number) {
        this.number = number;
    }

    public void reduce() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            shouldContinue = leftmostAtFourthLevel(number, 0)
                    .stream()
                    .peek(this::explode)
                    .findAny()
                    .isPresent();
            if (!shouldContinue) {
                shouldContinue = getFirstFromLeftNumberToSplit()
                        .stream()
                        .peek(this::splitMe)
                        .findAny()
                        .isPresent();
            }
        }
    }

    private void explode(CompoundSnailfishNumber number) {
        if (number.parent == null)
            throw new RuntimeException("You only can explode nested number");
        explodeMe(number);
    }

    private void explodeMe(CompoundSnailfishNumber number) {
        explodeChild(number.left(), i -> i - 1);
        explodeChild(number.right(), i -> i + 1);
        RegularSnailfishNumber newNumber = new RegularSnailfishNumber(0);
        newNumber.parent = number.parent;
        if (number == number.parent.left()) number.parent.setLeft(newNumber);
        if (number == number.parent.right()) number.parent.setRight(newNumber);
        number.getRoot().recreateOrderOfRegularNumbers();
    }

    private void splitMe(RegularSnailfishNumber regularSnailfishNumber) {
        CompoundSnailfishNumber newNumber = new CompoundSnailfishNumber(
                new RegularSnailfishNumber(regularSnailfishNumber.value() / 2),
                new RegularSnailfishNumber(regularSnailfishNumber.value() / 2 + regularSnailfishNumber.value() % 2)
        );
        newNumber.parent = regularSnailfishNumber.parent;
        if (regularSnailfishNumber.parent.left() == regularSnailfishNumber)
            regularSnailfishNumber.parent.setLeft(newNumber);
        if (regularSnailfishNumber.parent.right() == regularSnailfishNumber)
            regularSnailfishNumber.parent.setRight(newNumber);
        regularSnailfishNumber.getRoot().recreateOrderOfRegularNumbers();
    }

    private Optional<RegularSnailfishNumber> getFirstFromLeftNumberToSplit() {
        return number.getRoot().orderOfNumbers()
                .stream()
                .filter(r -> r.value() >= 10)
                .findFirst();
    }

    private Optional<CompoundSnailfishNumber> leftmostAtFourthLevel(CompoundSnailfishNumber number, int level) {
        Optional<CompoundSnailfishNumber> result = Optional.empty();
        if (level == 4) result = of(number);
        level++;
        if (result.isEmpty() && number.left() instanceof CompoundSnailfishNumber) {
            result = leftmostAtFourthLevel((CompoundSnailfishNumber) number.left(), level);
        }
        if (result.isEmpty() && number.right() instanceof CompoundSnailfishNumber) {
            result = leftmostAtFourthLevel((CompoundSnailfishNumber) number.right(), level);
        }
        return result;
    }

    private void explodeChild(SnailfishNumber child, Function<Integer, Integer> indexChanger) {
        CompoundSnailfishNumber root = number.getRoot();
        if (child instanceof RegularSnailfishNumber) {
            int index = indexChanger.apply(root.orderOfNumbers().indexOf(child));
            if (index >= 0 && index < root.orderOfNumbers().size()) {
                addValueToRegular(root.orderOfNumbers().get(index), (RegularSnailfishNumber) child);
            }
        }
    }

    private void addValueToRegular(RegularSnailfishNumber regularSnailfishNumber, RegularSnailfishNumber left) {
        CompoundSnailfishNumber parent = regularSnailfishNumber.parent;
        RegularSnailfishNumber sum = regularSnailfishNumber.add(left);
        sum.parent = parent;
        if (parent.left() == regularSnailfishNumber) {
            parent.setLeft(sum);
        }
        if (parent.right() == regularSnailfishNumber) {
            parent.setRight(sum);
        }
    }
}
