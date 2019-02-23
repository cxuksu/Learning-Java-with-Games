/*
 * PongStruTwo.java - A main class for the game Pong in applying the 
 * three-layer game structure.
 */

package pongstru;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class PongStruTwo extends JFrame {

    public PongStruTwo() {
        setTitle("Game Pong");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ScreenPanel controlP = new ScreenPanel();
        add(controlP);
        // the controlPanel contains both gameCanvas and splashScreen
        
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new PongStruTwo();
    }
}
