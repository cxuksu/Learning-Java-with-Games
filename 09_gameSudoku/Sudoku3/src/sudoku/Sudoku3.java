/*
 * Sudoku3.java - The main class of the game Sudoku.
 */
package sudoku;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Sudoku3 extends JFrame {

    public Sudoku3() {
        setTitle("Game Sudoku");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Sudoku3();
    }
}
