/*
 * Board.java - A class defines the board for displaying the game scene.
 */
package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Board extends JPanel {

    private Player dealer;
    private Player gambler;
    private boolean newGameFlag;
    // private boolean hitFlag;
    private boolean standFlag;
    private boolean betFlag;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        newGameFlag = false;
        standFlag = false;
        betFlag = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.drawRect(Consts.MINX, Consts.MINY, Consts.CV_WIDTH, Consts.BOARD_H);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Times", Font.BOLD, 22));
        g2d.drawString("Dealer", 10, 60);
        g2d.drawString("Gambler", 10, 190);

        if (newGameFlag) {
            // show two hands
            dealer.paintHand(g2d);
            gambler.paintHand(g2d);
            // show gambler's handValue
            if (gambler.getHandValue2() == 0) {
                g2d.drawString("" + gambler.getHandValue1(),
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            } else { // value2 is not 0, show both
                g2d.drawString("" + gambler.getHandValue1()
                        + "(" + gambler.getHandValue2() + ")",
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            }
            // show gambler's special status
            int specStatus = gambler.getStatus();
            if (specStatus == Consts.BLACKJACK) {
                g2d.drawString("Blackjack", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            } else if (specStatus == Consts.BUSTED) {
                g2d.drawString("Busted", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            }
        }
        if (standFlag) {
            // show dealer's handValue
            if (dealer.getHandValue2() == 0) {
                g2d.drawString("" + dealer.getHandValue1(),
                        Consts.VALUE_X, Consts.DEALER_V_Y);
            } else {
                g2d.drawString("" + dealer.getHandValue1()
                        + "(" + dealer.getHandValue2() + ")",
                        Consts.VALUE_X, Consts.DEALER_V_Y);
            }
            // show dealer's special status
            int specStatus = dealer.getStatus();
            if (specStatus == Consts.BLACKJACK) {
                g2d.drawString("Blackjack", Consts.STATUS_X, Consts.DEALER_S_Y);
            } else if (specStatus == Consts.BUSTED) {
                g2d.drawString("Busted", Consts.STATUS_X, Consts.DEALER_S_Y);
            }
            // show final status
            int dS = dealer.getStatus();
            int gS = gambler.getStatus();

            if (dS == Consts.WON) {
                g2d.drawString("Won", Consts.STATUS_X, Consts.DEALER_S_Y);
            } else if (dS == Consts.PUSHED) {
                g2d.drawString("Pushed", Consts.STATUS_X, Consts.DEALER_S_Y);
                g2d.drawString("Pushed", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            } else if (gS == Consts.WON) {
                g2d.drawString("Won", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            }
        }
        // show chips and chips value
        if (betFlag) {
            gambler.paintChips(g2d);
        }
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public void setGambler(Player gambler) {
        this.gambler = gambler;
    }

    public void setNewGameFlag(boolean newGameFlag) {
        this.newGameFlag = newGameFlag;
    }

    public boolean isStandFlag() {
        return standFlag;
    }

    public void setStandFlag(boolean standFlag) {
        this.standFlag = standFlag;
    }

    public void setBetFlag(boolean betFlag) {
        this.betFlag = betFlag;
    }

    public void setReset() {
        newGameFlag = false;
        standFlag = false;
        betFlag = false;
    }
}
