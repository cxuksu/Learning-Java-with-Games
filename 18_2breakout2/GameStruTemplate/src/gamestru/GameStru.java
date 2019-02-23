/*
 * GameStru.java - The main class of the game GameStru.
 */
package gamestru;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class GameStru extends JFrame {
    
    public GameStru() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameStru();
    }
}
