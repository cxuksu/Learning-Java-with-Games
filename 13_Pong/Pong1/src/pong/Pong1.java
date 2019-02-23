/*
 * Pong1.java - The main class of the game Pong1. 
 */
package pong;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Pong1 extends JFrame {

    private PongCanvas aCanvas;

    public Pong1() {
        setTitle("Game Pong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);

        aCanvas = new PongCanvas();
        add(aCanvas);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Pong1();
    }
}
