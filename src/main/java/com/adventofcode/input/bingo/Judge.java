package com.adventofcode.input.bingo;

public interface Judge {

    void afterRound(int number);

    void lastNumber();

    boolean gameEnded();

    int winingScore();
}
