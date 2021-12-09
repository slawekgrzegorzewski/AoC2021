package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.bingo.Game;
import com.adventofcode.input.bingo.LastMarkedBoardWins;

import java.io.IOException;

public class Day4 {

    private final Game game;

    public static void main(String[] input) throws IOException {
        Day4 day4 = new Day4();
        System.out.println("part1 = " + day4.part1());
        System.out.println("part2 = " + day4.part2());
    }

    public Day4() throws IOException {
        game = Input.game("/day4");
    }

    int part1() {
        return game.play();
    }

    int part2() {
        game.setJudge(new LastMarkedBoardWins(game));
        return game.play();
    }
}

