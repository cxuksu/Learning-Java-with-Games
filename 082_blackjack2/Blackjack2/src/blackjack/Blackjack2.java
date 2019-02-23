/*
 * Blackjack2 - The main class of the game Blackjack.
 */
package blackjack;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Blackjack2 extends JFrame {

    public Blackjack2() {
        setTitle("Game Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, 
                Consts.TOP_BAR_HEIGHT+Consts.BOARD_H+Consts.PLAYER_PANEL_H);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Blackjack2();
    }
    
}
