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
    private boolean hitFlag;
    private boolean standFlag;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        newGameFlag = false;
        hitFlag = false;
        standFlag = false;
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

        // show hand values
        dealer.paintHand(g2d);
        gambler.paintHand(g2d);
        if (newGameFlag) {
            if (gambler.getHandValue2() == 0) {
                g2d.drawString("" + gambler.getHandValue1(),
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            } else { // value2 is not 0, show both
                g2d.drawString("" + gambler.getHandValue1()
                        + "(" + gambler.getHandValue2() + ")",
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            }
            newGameFlag = false;
        }
        if (hitFlag) {
            if (gambler.getHandValue2() == 0) {
                g2d.drawString("" + gambler.getHandValue1(),
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            } else { // value2 is not 0, show both
                g2d.drawString("" + gambler.getHandValue1()
                        + "(" + gambler.getHandValue2() + ")",
                        Consts.VALUE_X, Consts.GAMBLER_V_Y);
            }
        }
        if (standFlag) {
            if (dealer.getHandValue2() == 0) {
                g2d.drawString("" + dealer.getHandValue1(),
                        Consts.VALUE_X, Consts.DEALER_V_Y);
            } else {
                g2d.drawString("" + dealer.getHandValue1()
                        + "(" + dealer.getHandValue2() + ")",
                        Consts.VALUE_X, Consts.DEALER_V_Y);
            }
        }

        // show status
        int dS = dealer.getStatus();
        int gS = gambler.getStatus();
        if (newGameFlag) {
            if (gS == Consts.BLACKJACK) {
                g2d.drawString("Blackjack", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            } else if (gS == Consts.BUSTED) {
                g2d.drawString("Busted", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            }
        }
        if (hitFlag) {
            if (gS == Consts.BLACKJACK) {
                g2d.drawString("Blackjack", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            } else if (gS == Consts.BUSTED) {
                g2d.drawString("Busted", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            }
        }
        if (standFlag) {
            if (dS == Consts.BLACKJACK) {
                g2d.drawString("Blackjack", Consts.STATUS_X, Consts.DEALER_S_Y);
            } else if (dS == Consts.BUSTED) {
                g2d.drawString("Busted", Consts.STATUS_X, Consts.DEALER_S_Y);
            } else if (dS == Consts.WON) {
                g2d.drawString("Won", Consts.STATUS_X, Consts.DEALER_S_Y);
            } else if (dS == Consts.PUSHED) {
                g2d.drawString("Pushed", Consts.STATUS_X, Consts.DEALER_S_Y);
                g2d.drawString("Pushed", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            } else if (gS == Consts.WON) {
                g2d.drawString("Won", Consts.STATUS_X, Consts.GAMBLER_S_Y);
            }
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

    public void setHitFlag(boolean hitFlag) {
        this.hitFlag = hitFlag;
    }

    public void setStandFlag(boolean standFlag) {
        this.standFlag = standFlag;
    }

    public void setReset() {
        newGameFlag = false;
        hitFlag = false;
        standFlag = false;
    }
}
