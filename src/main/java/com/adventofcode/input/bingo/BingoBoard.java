package com.adventofcode.input.bingo;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public class BingoBoard {
    BingoBoardElement[][] boardElements = new BingoBoardElement[][]{
            new BingoBoardElement[5],
            new BingoBoardElement[5],
            new BingoBoardElement[5],
            new BingoBoardElement[5],
            new BingoBoardElement[5]
    };

    public BingoBoard(int[][] elements) {
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 5; ii++) {
                boardElements[i][ii] = new BingoBoardElement(elements[i][ii]);
            }
        }
    }

    public void nextNumber(int number) {
        visitAllElements(boardElement -> boardElement.checkNumber(number));
    }

    public boolean isWon() {
        return IntStream.range(0, 5).anyMatch(i -> checkRow(i) || checkColumn(i));
    }

    private boolean checkRow(int rowIndex) {
        return IntStream.range(0, 5).allMatch(i -> boardElements[rowIndex][i].marked());
    }

    private boolean checkColumn(int columnIndex) {
        return IntStream.range(0, 5).allMatch(i -> boardElements[i][columnIndex].marked());
    }

    public int score(int lastNumber) {
        NotMarkedElementsSummator sumOfNotMarked = new NotMarkedElementsSummator();
        visitAllElements(sumOfNotMarked);
        return sumOfNotMarked.sum() * lastNumber;
    }

    private void visitAllElements(Consumer<BingoBoardElement> consumer) {
        for (int i = 0; i < 5; i++) {
            for (int ii = 0; ii < 5; ii++) {
                consumer.accept(boardElements[i][ii]);
            }
        }
    }

    private static class NotMarkedElementsSummator implements Consumer<BingoBoardElement> {
        int sum = 0;

        public int sum() {
            return sum;
        }

        @Override
        public void accept(BingoBoardElement boardElement) {
            if (!boardElement.marked()) {
                sum += boardElement.number();
            }
        }
    }
}
