/*
 * Board.java - A class defines the board for the game Sudoku.
 */
package sudoku;

import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Board {

    private Cell[][] sudokuBoard;
    private CursorMark cursorMark;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Board() {
        sudokuBoard = new Cell[Consts.MAX_CELLS][Consts.MAX_CELLS];
        initBoard();
    }

    public void initBoard() { // it is called by the class BoardPopulate.java
        Cell aCell;
        int x, y; // coordinates (x, y) of one cell
        int xInit = Consts.LEFT_M;
        int yInit = Consts.TOP_M;

        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            y = yInit + row * Consts.CELL_H;
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                x = xInit + col * Consts.CELL_W;
                aCell = new Cell();
                aCell.setX(x);
                aCell.setY(y);
                sudokuBoard[row][col] = aCell;
            }
        }
    }

    public void paintBoard(Graphics2D g2d) {
        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                sudokuBoard[row][col].paintCell(g2d);
            }
        }
        cursorMark.paintCursor(g2d);
    }

    public int checkGuessed() {
        int correct = 0, inCorrect = 0;
        Cell theCell;
        for (int row = 0; row < Consts.MAX_CELLS; row++) {
            for (int col = 0; col < Consts.MAX_CELLS; col++) {
                theCell = sudokuBoard[row][col];
                if (!theCell.isVisible()) {
                    if (theCell.getGuessed() == theCell.getDigit()) {
                        theCell.setGuessCorrect(true);
                        correct++;
                    } else {
                        inCorrect++;
                    }
                }
            }
        }
        return (int) (((correct * 1.0) / (correct + inCorrect)) * 100);
    }

    public void setGuessDigit(int row, int col, int digit) {
        sudokuBoard[row][col].setGuessed(digit);
    }

    public Cell[][] getSudokuBoard() {
        return sudokuBoard;
    }

    public void setCursorMark(CursorMark cursorMark) {
        this.cursorMark = cursorMark;
        Cell aCell = getSudokuBoard()[0][0];
        this.cursorMark.setBounds(aCell.getX(), aCell.getY(), aCell.getWidth(),
                aCell.getHeight());
        this.cursorMark.setLocation((int) getSudokuBoard()[0][0].getX(),
                (int) getSudokuBoard()[0][0].getY());
    }
}
