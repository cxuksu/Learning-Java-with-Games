/*
 * Breakout.java - The main class of the game Breakout.
 */
package breakout;

import javax.swing.JFrame;

public class Breakout extends JFrame {
    
    public Breakout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Breakout");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new Breakout();
    }
}
