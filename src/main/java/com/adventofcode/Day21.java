package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day19.SamePair;
import com.adventofcode.input.day22.DeterministicDice;
import com.adventofcode.input.day22.Dice;

import java.io.IOException;
import java.util.*;

import static com.adventofcode.input.day19.SamePair.samePair;

public class Day21 {

    Map<Integer, Integer> playersInitialPositions;

    public static void main(String[] input) throws IOException {
        Day21 day21 = new Day21();
        System.out.println("part1 = " + day21.part1());
        System.out.println("part2 = " + day21.part2());
    }

    public Day21() throws IOException {
        playersInitialPositions = Input.day21("/day21");
    }

    int part1() {
        Map<Integer, Integer> positions = new HashMap<>(playersInitialPositions);
        Map<Integer, Integer> scores = new HashMap<>();
        scores.put(1, 0);
        scores.put(2, 0);
        int player = 1;
        Dice dice = new DeterministicDice();
        while (scores.values().stream().mapToInt(i -> i).noneMatch(i -> i >= 1000)) {
            int moves = dice.roll() + dice.roll() + dice.roll();
            positions.compute(player, (player1, position) -> movePawn(moves, position));
            scores.compute(player, (player1, score) -> score + positions.get(player1));
            player = (player == 1 ? 2 : 1);
        }
        return (scores.get(1) < 1000 ? scores.get(1) : scores.get(2)) * dice.rollCount();
    }

    long part2() {
        SamePair<Long> countOfWins = play(playersInitialPositions.get(1), playersInitialPositions.get(2), 0, 0);
        return countOfWins.first() > countOfWins.second() ? countOfWins.first() : countOfWins.second();
    }


    SamePair<Long> play(int pos1, int pos2, int score1, int score2) {
        if (score2 >= 21) return samePair(0L, 1L);

        long wins1 = 0L, wins2 = 0L;
        List<SamePair<Integer>> possibleOutcomes = List.of(
                samePair(3, 1), // (1, 1, 1)
                samePair(4, 3), // (1, 1, 2), (1, 2, 1), (2, 1, 1)
                samePair(5, 6), // (1, 1, 3), (1, 3, 1), (3, 1, 1), (1, 2, 2), (2, 1, 2), (2, 2, 1)
                samePair(6, 7), // (1, 2, 3), (1, 3, 2), (2, 1, 3), (2, 3, 1), (3, 1, 2), (3, 2, 1), (2, 2, 2)
                samePair(7, 6),// (2, 2, 3), (2, 3, 2), (3, 2, 2), (1, 3, 3), (3, 1, 3), (3, 3, 1)
                samePair(8, 3),// (2, 2, 3), (2, 3, 2), (3, 2, 2)
                samePair(9, 1) //(3, 3, 3)
        );
        for (SamePair<Integer> possibleOutcome : possibleOutcomes) {
            int move = possibleOutcome.first();
            int numberOfRollsWithThatMove = possibleOutcome.second();
            int newPos1 = movePawn(move, pos1);
            SamePair<Long> winCount = play(pos2, newPos1, score2, score1 + newPos1);
            wins1 = wins1 + numberOfRollsWithThatMove * winCount.second();
            wins2 = wins2 + numberOfRollsWithThatMove * winCount.first();
        }
        return samePair(wins1, wins2);
    }

    private int movePawn(int moves, int position) {
        return (position + moves - 1) % 10 + 1;
    }
}

