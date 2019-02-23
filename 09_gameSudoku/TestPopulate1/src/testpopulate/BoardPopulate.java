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
    private Board board;

    public BoardPopulate() {
        random = new Random();
    }

    public void populateBoard() {
        int aDigit = 0;
        boolean stop = false;
        int count = 0;

        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                while (!stop) {
                    aDigit = Math.abs(random.nextInt() % 9) + 1;
                    if (validRow(aDigit, row, col)) {
                        stop = true;
                    } 
                }
                insertDigit(aDigit, row, col);
                stop = false;
            }
        }
    }

    private boolean validRow(int aValue, int row, int col) {
        Cell[][] aBoard = board.getSudokuBoard();
        for (int i = 0; i < Consts.MAX_CELLS; i++) {
            if (aBoard[row][i].getDigit() == aValue) {  
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
    }
}
