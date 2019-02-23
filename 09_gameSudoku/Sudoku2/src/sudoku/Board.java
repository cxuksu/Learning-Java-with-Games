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
    private BoardPopulate boardPopulate;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Board() {
        sudokuBoard = new Cell[Consts.MAX_CELLS][Consts.MAX_CELLS];
        initBoard();
    }

    public void initBoard() {
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
                aCell.setDigit(0);
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
    }

    public Cell[][] getSudokuBoard() {
        return sudokuBoard;
    }
}
