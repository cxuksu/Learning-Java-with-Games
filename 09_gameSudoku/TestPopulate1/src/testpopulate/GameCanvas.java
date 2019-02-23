/*
 * GameCanvas.java - A class defines the canvas for the game Sudoku.
 */
package testpopulate;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Board board;
    private BoardPopulate boardPopulate;

    public GameCanvas() {
        initComponent();
    }

    private void initComponent() {
        board = new Board();
        boardPopulate = new BoardPopulate();
        boardPopulate.setBoard(board);
        boardPopulate.populateBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        board.paintBoard(g2d);
    }
}
