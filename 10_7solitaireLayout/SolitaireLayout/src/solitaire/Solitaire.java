/*
 * Solitaire.java - The main class of the game Agnes
 */
package solitaire;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Solitaire extends JFrame {

    public Solitaire() {
        setTitle("Game Solitaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Solitaire();
    }
}
