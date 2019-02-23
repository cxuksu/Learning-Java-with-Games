/*
 * Player.java - A class defines a player.
 */
package blackjack;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int handValue1;
    private int handValue2;
    private int numAce;
    private int status;
    private ArrayList<Chip> chips;
    private int chipsValue;
    private int totalAmount;

    private Deck deck; // for dealing a card to hand

    public Player() {
        //initPlayer(); it is called by playerPanel.resetGame()
    }

    public void initPlayer() { // it should be called by PlayerPanel.java
        hand = new ArrayList<>();
        handValue1 = 0;
        handValue2 = 0;
        numAce = 0;
        chips = new ArrayList<>();
        chipsValue = 0;
        status = Consts.COMPARE1;
    }

    public void assignHand(boolean faceUp, int y) {
        Card card = deck.dealCard();
        card.setFaceUp(faceUp);
        if (hand.isEmpty()) {
            card.setX(Consts.CARD_X);
        } else {
            int tempX = (hand.get(hand.size() - 1)).getX();
            card.setX(tempX + Consts.CARD_GAP);
        }
        card.setY(y);
        hand.add(card);
        // calculate hand values
        if (card.getValue() == 11) {
            numAce++;
        }
        handValue1 += card.getValue();
        if (numAce > 0) {
            handValue2 = handValue1 - numAce * 10;
        }
        // determine status
        if ((handValue1 == Consts.V_BLACKJACK)
                || (handValue2 == Consts.V_BLACKJACK)) {
            status = Consts.BLACKJACK;
        } else if ((handValue1 > Consts.V_BLACKJACK)
                && (handValue2 > Consts.V_BLACKJACK)) {
            status = Consts.BUSTED;
        } else if ((handValue1 > Consts.V_BLACKJACK) && (handValue2 == 0)) {
            status = Consts.BUSTED;
        } else if ((handValue1 > Consts.V_BLACKJACK)
                && (handValue2 < Consts.V_BLACKJACK)) {
            status = Consts.COMPARE2;
        } else if (handValue1 < Consts.V_BLACKJACK) { // handValue2 must < 21
            status = Consts.COMPARE1;
        }
    }

    public void paintHand(Graphics2D g2d) {
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).paintCard(g2d);
        }
    }

    public int assignChips(int chipValue) {
        totalAmount -= chipValue;
        if (totalAmount >= 0) {
            Chip chip = new Chip();
            chip.initChip(chipValue);
            chip.setX((int) ((Math.random() * Consts.CHIP_RANGE_X)
                    + Consts.CHIP_MINX));
            chip.setY((int) ((Math.random() * Consts.CHIP_RANGE_Y)
                    + Consts.CHIP_MINY));
            chips.add(chip);
            chipsValue += chipValue;
        } else {
            // do nothing here; but the case will be handlered by PlayerPanel
        }
        return totalAmount;
    }

    public void paintChips(Graphics2D g2d) {
        for (int i = 0; i < chips.size(); i++) {
            chips.get(i).paintChip(g2d);
        }
        g2d.drawString("" + chipsValue, Consts.CHIPS_VALUE_X, Consts.CHIPS_VALUE_Y);
    }

    public int getHandValue1() {
        return handValue1;
    }

    public int getHandValue2() {
        return handValue2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    public int getChipsValue() {
        return chipsValue;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
