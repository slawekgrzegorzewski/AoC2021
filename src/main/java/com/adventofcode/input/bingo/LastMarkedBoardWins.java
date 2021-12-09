package com.adventofcode.input.bingo;

import java.util.List;
import java.util.stream.Collectors;

public class LastMarkedBoardWins implements Judge {

    private final Game game;
    private BingoBoard winningBoard;
    private int winningNumber;
    private boolean numbersReachedOut = false;

    public LastMarkedBoardWins(Game game) {
        this.game = game;
    }

    @Override
    public void afterRound(int number) {
        List<BingoBoard> winningBoards = game.boards.stream()
                .filter(BingoBoard::isWon)
                .toList();
        for (BingoBoard bingoBoard : winningBoards) {
            this.winningBoard = bingoBoard;
            this.winningNumber = number;
            this.game.boards.remove(this.winningBoard);
        }
    }

    @Override
    public void lastNumber() {
        numbersReachedOut = true;
    }

    @Override
    public boolean gameEnded() {
        return numbersReachedOut && winningBoard != null;
    }

    @Override
    public int winingScore() {
        return winningBoard.score(winningNumber);
    }
}
