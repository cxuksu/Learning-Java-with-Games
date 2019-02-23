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
    private CardLayout cardLayout;

    public GameCanvas() {
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        cardLayout = new CardLayout();
        cardLayout.setDeck(deck);
        cardLayout.initLayout();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        cardLayout.paintLayout(g2d);
    }
}
