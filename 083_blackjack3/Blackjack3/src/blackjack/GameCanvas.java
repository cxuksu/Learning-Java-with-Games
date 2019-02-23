/*
 * GameCanvas.java -- A class defines game canvas.
 */
package blackjack;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Player dealer;
    private Player gambler;
    private Deck deck;
    private Board board;
    private PlayerPanel playerPanel;

    public GameCanvas() {
        setLayout(new BorderLayout());
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        board = new Board();
        playerPanel = new PlayerPanel();
        playerPanel.setBoard(board);

        initPlayer();
        add(board, BorderLayout.CENTER);
        add(playerPanel, BorderLayout.SOUTH);
    }

    public void initPlayer() {
        dealer = new Player();
        dealer.initPlayer();
        dealer.setName("dealer");
        dealer.setDeck(deck);

        gambler = new Player();
        gambler.initPlayer();
        gambler.setName("gambler");
        gambler.setDeck(deck);

        board.setDealer(dealer);
        board.setGambler(gambler);
        playerPanel.setDealer(dealer);
        playerPanel.setGambler(gambler);
    }
}
