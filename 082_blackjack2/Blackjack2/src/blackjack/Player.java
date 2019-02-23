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

    private Deck deck; // for dealing a card to hand
    
    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        hand = new ArrayList<>();
        numAce = 0;
        handValue1 = 0;
        handValue2 = 0;
        status = Consts.COMPARE1;
    }

    public void assignHand(boolean faceUp, int y) {
        Card card = deck.dealCard();
        card.setFaceUp(faceUp);
        // assign the coordinate (x, y) for painting
        if (hand.isEmpty()) {
            card.setX(Consts.INIT_X);
        } else {
            int tempX = (hand.get(hand.size() - 1)).getX();
            card.setX(tempX + Consts.CARD_GAP);
        }
        card.setY(y);
        hand.add(card);
        // calculate hand values
        if (card.getValue() == 11) { // i.e. the card is an Ace
            numAce++;
        }
        handValue1 += card.getValue(); // the total value of the hand
        if (numAce > 0) {
            handValue2 = handValue1 - numAce * 10;
        }
        // determine current status
        if ((handValue1 == 21) || (handValue2 == 21)) {
            status = Consts.BLACKJACK;
        } else if ((handValue1 > 21) && (handValue2 > 21)) {
            status = Consts.BUSTED;
        } else if ((handValue1 > 21) && (handValue2 == 0)) {
            status = Consts.BUSTED;
        } else if ((handValue1 > 21) && (handValue2 < 21)) {
            status = Consts.COMPARE2;
        } else if (handValue1 < 21) { // handValue2 default < 21
            status = Consts.COMPARE1;
        }
    }

    public void paintHand(Graphics2D g2d) {
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).paintCard(g2d);
        }
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
