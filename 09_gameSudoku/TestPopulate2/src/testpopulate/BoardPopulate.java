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

    private Random random;
    private Cell[][] validBoard;
    private Board board;

    public BoardPopulate() {
        random = new Random();
    }

    public void populateBoard() {
        int aDigit = 0;
        boolean stop;
        int count = 0;
        boolean forceBreak = false;

        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                stop = false;
                while (!stop) {
                    aDigit = Math.abs(random.nextInt() % 9) + 1;
                    if ((validRow(aDigit, row, col))
                            && (validColumn(aDigit, row, col))) {
                        stop = true;
                        count = 0;
                    } else {
                        count++;
                        if (count >= 5000) {
                            stop = true;
                            forceBreak = true;
                        }
                    }
                }
                if (!forceBreak) {
                    insertDigit(aDigit, row, col);
                } else {
                    break;
                }
            }
        }
    }

    private boolean validRow(int aValue, int row, int col) {
        for (int i = 0; i < Consts.MAX_CELLS; i++) {
            if (validBoard[row][i].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private boolean validColumn(int aValue, int row, int col) {
        for (int i = 0; i < Consts.MAX_CELLS; i++) {
            if (validBoard[i][col].getDigit() == aValue) {
                return false;
            }
        }
        return true;
    }

    private void insertDigit(int aValue, int row, int col) {
        board.getSudokuBoard()[row][col].setDigit(aValue);
    }

    public void setBoard(Board board) {
        this.board = board;
        validBoard = this.board.getSudokuBoard();
    }
}
