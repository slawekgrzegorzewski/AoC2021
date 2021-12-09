package com.adventofcode.input.bingo;

public class FirstMarkedBoardWins implements Judge {

    private final Game game;
    private BingoBoard winningBoard;
    private int winningNumber;

    public FirstMarkedBoardWins(Game game) {
        this.game = game;
    }

    @Override
    public void afterRound(int number) {
        if (!gameEnded()) {
            game.boards.stream().filter(BingoBoard::isWon).findFirst()
                    .ifPresent(bingoBoard -> {
                        this.winningBoard = bingoBoard;
                        this.winningNumber = number;
                    });
        }
    }

    @Override
    public void lastNumber() {

    }

    @Override
    public boolean gameEnded() {
        return winningBoard != null;
    }

    @Override
    public int winingScore() {
        return winningBoard.score(winningNumber);
    }
}
