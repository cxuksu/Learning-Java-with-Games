/*
 * Blackjack1.java - The main class of the game Blackjack1.
 */
package blackjack;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Blackjack1 extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Blackjack1() {
        setTitle("Game Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH,
                Consts.TOP_BAR_HEIGHT + Consts.BOARD_H + Consts.PLAYER_PANEL_H);

        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new Blackjack1();
    }
}
