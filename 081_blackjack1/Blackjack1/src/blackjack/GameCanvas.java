/*
 * GameCanvas.java - A class defines the canvas of the game.
 */
package blackjack;

import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Deck deck;
    private Card aCard;
    private Board board;
    private PlayerPanel playerPanel;

    public GameCanvas() {
        initComponent();
    }

    private void initComponent() {
        board = new Board();
        playerPanel = new PlayerPanel();
        add(board);
        add(playerPanel);
        
        deck = new Deck();
        aCard = deck.dealCard();
        aCard.setX(50);
        aCard.setY(50);
        aCard.setFaceUp(true);
        board.setaCard(aCard);
    }
}
