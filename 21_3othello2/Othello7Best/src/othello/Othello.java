/*
 * Othello.java - The main class of the game Othello.
 */
package othello;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Othello extends JFrame {

    public Othello() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Othello");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);

        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Othello();
    }
}
