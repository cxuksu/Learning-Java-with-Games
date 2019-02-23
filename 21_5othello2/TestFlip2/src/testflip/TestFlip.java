/*
 * TestFlip.java - The main class of the game TestFlip.
 */
package testflip;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TestFlip extends JFrame {
    
    public TestFlip() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new TestFlip();
    }
}
