/*
 * PongExtension.java - A main class for the game Pong in applying the 
 * three-layer game structure.
 */

package pongextension;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class PongExtension extends JFrame {

    public PongExtension() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pong Extension");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT); 
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PongExtension();
    }
}
