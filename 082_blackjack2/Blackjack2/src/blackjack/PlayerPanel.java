/*
 * PlayerPanel.java - A class defines the player's panel.
 */
package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private Player dealer;
    private Player gambler;
    private Board board;

    private JButton newGameBtn;
    private JButton hitBtn;
    private JButton standBtn;

    public PlayerPanel() {
        initPlayerPanel();
    }

    private void initPlayerPanel() {
        ActionListener myActionListener = new MyActionListener();

        newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(myActionListener);
        newGameBtn.setEnabled(false);
        add(newGameBtn);
        hitBtn = new JButton("Hit");
        hitBtn.addActionListener(myActionListener);
        add(hitBtn);
        standBtn = new JButton("Stand");
        standBtn.addActionListener(myActionListener);
        add(standBtn);

        initGame();
    }

    private void initGame() {
        //newGame.setEnabled(true) cannot be included for the first time
        newGameBtn.setEnabled(true);
        hitBtn.setEnabled(false);
        standBtn.setEnabled(false);
    }

    private void clickedNewGame() {
        board.setNewGameFlag(true);
        newGameBtn.setEnabled(false);
        hitBtn.setEnabled(true);
        standBtn.setEnabled(true);
    }

    private void dealerHitStand() {
        // dealer: hit or stand
        if (dealer.getHandValue2() == 0) { // if no Ace
            if (dealer.getHandValue1() <= 16) { // hit
                while (dealer.getHandValue1() <= 16) {
                    dealer.assignHand(true, Consts.DEALER_Y);
                }
            } else if (dealer.getHandValue1() >= 17) { // stand
            }
        }
        if (dealer.getHandValue2() != 0) { // if has Ace
            if (dealer.getHandValue1() == 21) { // based on value1
                // Blackjack, stand
            } else if (dealer.getHandValue1() <= 16) { // hit
                while (dealer.getHandValue1() <= 16) {
                    dealer.assignHand(true, Consts.DEALER_Y);
                }
            } else if (dealer.getHandValue1() >= 17) { // stand
            }
            if (dealer.getHandValue1() > 21) { // based on value2
                if (dealer.getHandValue2() <= 16) { // hit
                    while (dealer.getHandValue2() <= 16) {
                        dealer.assignHand(true, Consts.DEALER_Y);
                    }
                } else if (dealer.getHandValue2() >= 17) { // stand
                }
            }
        }
        board.setStandFlag(true);
        board.setHitFlag(true);
    }

    private void determineFinalStatus() {
        // determines final status
        int dS = dealer.getStatus();
        int gS = gambler.getStatus();
        if ((dS == Consts.BLACKJACK) && (gS == Consts.BLACKJACK)) {
            dealer.setStatus(Consts.PUSHED);
            gambler.setStatus(Consts.PUSHED);
        } else if ((dS == Consts.BUSTED) && (gS == Consts.BUSTED)) {
            dealer.setStatus(Consts.PUSHED);
            gambler.setStatus(Consts.PUSHED);
        }
        dS = dealer.getStatus();
        gS = gambler.getStatus();

        // COMPARE: four combinations
        if (((dS == Consts.COMPARE1) || (dS == Consts.COMPARE2))
                && ((gS == Consts.COMPARE1) || (gS == Consts.COMPARE2))) {
            int dV1 = dealer.getHandValue1();
            int dV2 = dealer.getHandValue2();
            int gV1 = gambler.getHandValue1();
            int gV2 = gambler.getHandValue2();
            if ((dS == Consts.COMPARE1) && (gS == Consts.COMPARE1)) {
                if (dV1 > gV1) {
                    dealer.setStatus(Consts.WON);
                } else if (dV1 < gV1) {
                    gambler.setStatus(Consts.WON);
                } else { // (dV1 == gV1)
                    dealer.setStatus(Consts.PUSHED);
                    gambler.setStatus(Consts.PUSHED);
                }
            } else if ((dS == Consts.COMPARE1) && (gS == Consts.COMPARE2)) {
                if (dV1 > gV2) {
                    dealer.setStatus(Consts.WON);
                } else if (dV1 < gV2) {
                    gambler.setStatus(Consts.WON);
                } else { // (dV1 == gV2)
                    dealer.setStatus(Consts.PUSHED);
                    gambler.setStatus(Consts.PUSHED);
                }
            } else if ((dS == Consts.COMPARE2) && (gS == Consts.COMPARE1)) {
                if (dV2 > gV1) {
                    dealer.setStatus(Consts.WON);
                } else if (dV2 < gV1) {
                    gambler.setStatus(Consts.WON);
                } else { // (dV2 == gV1)
                    dealer.setStatus(Consts.PUSHED);
                    gambler.setStatus(Consts.PUSHED);
                }
            } else if ((dS == Consts.COMPARE2) && (gS == Consts.COMPARE2)) {
                if (dV2 > gV2) {
                    dealer.setStatus(Consts.WON);
                } else if (dV2 < gV2) {
                    gambler.setStatus(Consts.WON);
                } else { // (dV2 == gV2)
                    dealer.setStatus(Consts.PUSHED);
                    gambler.setStatus(Consts.PUSHED);
                }
            }
        }
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == newGameBtn) {
                dealer.assignHand(false, Consts.DEALER_Y);
                dealer.assignHand(true, Consts.DEALER_Y);

                gambler.assignHand(true, Consts.GAMBLER_Y);
                gambler.assignHand(true, Consts.GAMBLER_Y);
                clickedNewGame();
            } else if (evt.getSource() == hitBtn) {
                gambler.assignHand(true, Consts.GAMBLER_Y);
                board.setHitFlag(true);
            } else if (evt.getSource() == standBtn) {
                hitBtn.setEnabled(false);
                // reveal the first facedown card
                Card theCard = dealer.getHand().get(0);
                theCard.setFaceUp(true);

                dealerHitStand();
                determineFinalStatus();
            }
            board.repaint();
        }
    }
    
    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public void setGambler(Player gambler) {
        this.gambler = gambler;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
