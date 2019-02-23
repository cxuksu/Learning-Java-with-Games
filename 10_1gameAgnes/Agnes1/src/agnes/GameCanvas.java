/*
 * GameCanvas.java - A class defines a canvas for the game.
 */
package agnes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Deck deck;
    private Card aCard;

    public GameCanvas() {
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < 5; i++) { // deal 5 cards for testing
            aCard = deck.dealCard();
            aCard.setX(Consts.COL1_X + i * (Consts.CARD_W + Consts.CARD_GAP_X));
            aCard.setY(Consts.COL1_Y);
            aCard.setFaceUp(true);
            aCard.paintCard(g2d);
        }
    }
}
