package com.adventofcode.input.day23;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Room2 implements Room {
    private final int number;
    Amphipod first = null, second = null;

    public Room2(int number) {
        this.number = number;
    }

    public Room2(int number, Amphipod first, Amphipod second) {
        this.number = number;
        this.first = first;
        this.second = second;
    }

    public Room2 copy() {
        return new Room2(number, first, second);
    }

    public int number() {
        return number;
    }

    @Override
    public int capacity() {
        return 2;
    }

    public Amphipod first() {
        return first;
    }

    public Amphipod second() {
        return second;
    }

    public boolean canEnter(Amphipod amphipod) {
        return number == amphipod.targetRoom() && (isEmpty() || (isHalfTaken() && second.targetRoom() == number));
    }

    public boolean canExit() {
        return (isFull() && (first.targetRoom() != number || second.targetRoom() != number)) || (isHalfTaken() && second.targetRoom() != number);
    }

    public boolean isFull() {
        return first != null && second != null;
    }

    @Override
    public boolean isFilledWithCorrectAmphipods() {
        return isFull() && first().targetRoom() == number() && second().targetRoom() == number();
    }

    @Override
    public List<Amphipod> getAllAmphipods() {
        return Stream.of(first, second).filter(Objects::nonNull).toList();
    }

    public boolean isHalfTaken() {
        return first == null && second != null;
    }

    public boolean isEmpty() {
        return first == null && second == null;
    }

    public Optional<Amphipod> exitRoom() {
        if(!canExit()){
            throw new RuntimeException("Can not exit this room");
        }
        Amphipod exiting = null;
        if (first != null) {
            exiting = first;
            first = null;
        } else if (second != null) {
            exiting = second;
            second = null;
        }
        return Optional.ofNullable(exiting);
    }

    public void enterRoom(Amphipod amphipod) {
        if(!canEnter(amphipod)){
            throw new RuntimeException("Can not enter this room");
        }
        if (isEmpty()) {
            second = amphipod;
        } else if (isHalfTaken()) {
            first = amphipod;
        } else {
            throw new RuntimeException("This room is full");
        }
    }

    @Override
    public int stepsToLeave() {
        return isEmpty() ? 2 : 1;
    }

    @Override
    public int stepsToEnter() {
        return isEmpty() ? 2 : 1;
    }
}
