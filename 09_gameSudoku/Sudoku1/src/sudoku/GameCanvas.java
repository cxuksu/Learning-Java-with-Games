/*
 * GameCanvas.java - A class defines the canvas for the game Sudoku.
 */
package sudoku;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Board board;

    public GameCanvas() {
        initComponent();
    }

    private void initComponent() {
        board = new Board();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        board.paintBoard(g2d);
    }
}
