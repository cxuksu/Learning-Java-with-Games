/*
 * BoardPopulate.java - A class for populating data with the board.
 */
package testpopulate;

import java.util.Random;

/**
 *
 * @author cxu
 */
public class BoardPopulate {

    private final Random random;
    private Cell[][] validBoard;
    private final int[] alreadyTested;
    private boolean allTested;
    private Board board;

    public BoardPopulate() {
        random = new Random();
        alreadyTested = new int[9];
    }

    public void populateBoard() {
        int aDigit = 0;
        boolean stop;

        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                initAry(alreadyTested);
                allTested = false;
                stop = false;
                while (!stop) { // the loop may fully fill alreadyTested ary
                    aDigit = Math.abs(random.nextInt() % 9) + 1;
                    if ((isNewDigit(aDigit, alreadyTested)) && (!allTested)) {
                        if ((validRow(aDigit, row, col))
                                && (validColumn(aDigit, row, col))) {
                            stop = true;
                        }
                    } else if (allTested) {
                        stop = true;
                    }
                }
                if (!allTested) {
                    insertDigit(aDigit, row, col);
                } else {
                    board.initBoard();
                    row = 0;
                    col = -1;
                }
            }
        }
    }

    private boolean isNewDigit(int aValue, int[] ary) {
        if (ary[ary.length - 1] != 0) {
            allTested = true;
            return false;
        }
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == aValue) {
                return false;
            } else if (ary[i] == 0) {
                ary[i] = aValue;
                break;
            }
        }
        return true;
    }

    private boolean validRow(int aValue, int row, int col) {
        for (int i = 0; i < col; i++) { // check digits before the column
            if (validBoard[row][i].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private boolean validColumn(int aValue, int row, int col) {
        for (int i = 0; i < row; i++) { // the remaining digits are 0s
            if (validBoard[i][col].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private void insertDigit(int aValue, int row, int col) {
        board.getSudokuBoard()[row][col].setDigit(aValue);
    }

    public void initAry(int[] ary) {
        for (int i = 0; i < Consts.MAX_CELLS; i++) {
            ary[i] = 0;
        }
    }

    public void setBoard(Board board) {
        this.board = board;
        validBoard = this.board.getSudokuBoard();
    }
}
