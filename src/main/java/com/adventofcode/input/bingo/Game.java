package com.adventofcode.input.bingo;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Integer> numbers = new ArrayList<>();
    final List<BingoBoard> boards = new ArrayList<>();
    private Judge judge;

    public Game(List<Integer> numbers, List<BingoBoard> boards) {
        this.numbers.addAll(numbers);
        this.boards.addAll(boards);
        judge = new FirstMarkedBoardWins(this);
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public int play() {
        for (Integer number : numbers) {
            boards.forEach(board -> board.nextNumber(number));
            judge.afterRound(number);
            if (judge.gameEnded()) {
                return judge.winingScore();
            }
        }
        judge.lastNumber();
        if (judge.gameEnded()) {
            return judge.winingScore();
        }
        throw new RuntimeException("No wining board");
    }
}
