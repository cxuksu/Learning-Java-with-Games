/*
 * WormGame5.java - A main class for the game Pong in applying the 
 * three-layer game structure.
 */

package wormgame;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class WormGame5 extends JFrame {

    public WormGame5() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Pong");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT); 
        
        ControlPanel controlP = new ControlPanel();
        add(controlP);
        
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WormGame5();
    }
}
