package com.adventofcode.input.day23;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Room4 implements Room {
    private final int number;
    Amphipod first = null, second = null, third = null, fourth = null;

    public Room4(int number) {
        this.number = number;
    }

    public Room4(int number, Amphipod first, Amphipod second, Amphipod third, Amphipod fourth) {
        this.number = number;
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    @Override
    public Room4 copy() {
        return new Room4(number, first, second, third, fourth);
    }

    @Override
    public int number() {
        return number;
    }

    @Override
    public int capacity() {
        return 4;
    }

    public Amphipod first() {
        return first;
    }

    public Amphipod second() {
        return second;
    }

    public Amphipod third() {
        return third;
    }

    public Amphipod fourth() {
        return fourth;
    }

    @Override
    public boolean canEnter(Amphipod amphipod) {
        return number == amphipod.targetRoom() && (
                isEmpty()
                        || (is3_4Taken() && second.targetRoom() == number && third.targetRoom() == number && fourth.targetRoom() == number)
                        || (isHalfTaken() && third.targetRoom() == number && fourth.targetRoom() == number)
                        || (is1_4Taken() && fourth.targetRoom() == number));
    }

    @Override
    public boolean canExit() {
        return (isFull() && (first.targetRoom() != number || (second.targetRoom() != number || third.targetRoom() != number || fourth.targetRoom() != number))
                || (is3_4Taken() && (second.targetRoom() != number || (third.targetRoom() != number || fourth.targetRoom() != number)))
                || (isHalfTaken() && (third.targetRoom() != number || fourth.targetRoom() != number))
                || (is1_4Taken() && fourth.targetRoom() != number));
    }

    @Override
    public boolean isFull() {
        return first != null && second != null && third != null && fourth != null;
    }

    @Override
    public boolean isFilledWithCorrectAmphipods() {
        return isFull() && first().targetRoom() == number() && second().targetRoom() == number()
                && third().targetRoom() == number() && fourth.targetRoom() == number();
    }

    @Override
    public List<Amphipod> getAllAmphipods() {
        return Stream.of(first, second, third, fourth).filter(Objects::nonNull).toList();
    }

    public boolean is3_4Taken() {
        return first == null && second != null && third != null && fourth != null;
    }

    public boolean isHalfTaken() {
        return first == null && second == null && third != null && fourth != null;
    }

    public boolean is1_4Taken() {
        return first == null && second == null && third == null && fourth != null;
    }

    public boolean isEmpty() {
        return first == null && second == null && third == null && fourth == null;
    }

    @Override
    public Optional<Amphipod> exitRoom() {
        if (!canExit()) {
            throw new RuntimeException("Can not exit this room");
        }
        Amphipod exiting = null;
        if (first != null) {
            exiting = first;
            first = null;
        } else if (second != null) {
            exiting = second;
            second = null;
        } else if (third != null) {
            exiting = third;
            third = null;
        } else if (fourth != null) {
            exiting = fourth;
            fourth = null;
        }
        return Optional.ofNullable(exiting);
    }

    @Override
    public void enterRoom(Amphipod amphipod) {
        if (!canEnter(amphipod)) {
            throw new RuntimeException("Can not enter this room");
        }
        if (isEmpty()) {
            fourth = amphipod;
        } else if (is1_4Taken()) {
            third = amphipod;
        } else if (isHalfTaken()) {
            second = amphipod;
        } else if (is3_4Taken()) {
            first = amphipod;
        } else {
            throw new RuntimeException("This room is full");
        }
    }

    @Override
    public int stepsToLeave() {
        if (isEmpty()) return 4;
        if (is1_4Taken()) return 3;
        if (isHalfTaken()) return 2;
        if (is3_4Taken()) return 1;
        throw new RuntimeException("Nothing leaved room");
    }

    @Override
    public int stepsToEnter() {
        if (isEmpty()) return 4;
        if (is1_4Taken()) return 3;
        if (isHalfTaken()) return 2;
        if (is3_4Taken()) return 1;
        throw new RuntimeException("Nothing leaved room");
    }
}
