/*
 * SymBall.java - A main class for the game Pong in applying the 
 * three-layer game structure.
 */

package symball;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class SymBall extends JFrame {

    public SymBall() {
        setTitle("Symbolic Ball");
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
        new SymBall();
    }
}
