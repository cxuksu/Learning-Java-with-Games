/*
 * WormGame.java - The main class of the game WormGame.
 */
package worm;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class WormGame extends JFrame {
    
    public WormGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new WormGame();
    }
}
