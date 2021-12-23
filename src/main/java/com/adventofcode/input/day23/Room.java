package com.adventofcode.input.day23;

import java.util.List;
import java.util.Optional;

public interface Room {

    Room copy();

    int number();

    int capacity();

    boolean canEnter(Amphipod amphipod);

    boolean canExit();

    boolean isFull();

    boolean isFilledWithCorrectAmphipods();
    List<Amphipod> getAllAmphipods();

    Optional<Amphipod> exitRoom();

    void enterRoom(Amphipod amphipod);

    int stepsToLeave();

    int stepsToEnter();

}
