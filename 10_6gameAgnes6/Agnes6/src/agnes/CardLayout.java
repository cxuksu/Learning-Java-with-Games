/*
 * CardLayout.java - A class defines a player.
 */
package agnes;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class CardLayout {

    private ArrayList[] column;
    private ArrayList[] foundation;

    private Deck deck; // for dealing cards

    public CardLayout() {
        column = new ArrayList[Consts.NUM_COL];
        foundation = new ArrayList[Consts.NUM_SUIT];
        initStructure();
    }

    private void initStructure() { // it should be called by PlayerPanel.java
        for (int col = 0; col < Consts.NUM_COL; col++) {
            column[col] = new ArrayList<>();
        }
        for (int fCol = 0; fCol < Consts.NUM_SUIT; fCol++) {
            foundation[fCol] = new ArrayList<>();
        }
        //initLayout(); // it should be called after setDeck(deck);
    }

    public void initLayout() {
        Card aCard;
        for (int row = 0; row < Consts.NUM_ROW; row++) {
            for (int col = 0; col < Consts.NUM_COL - row; col++) {
                aCard = deck.dealCard();
                aCard.setFaceUp(true);
                aCard.setX(Consts.COL1_X + col * Consts.CARD_W + col * Consts.CARD_GAP_X);
                if (column[col].isEmpty()) {
                    aCard.setY(Consts.COL1_Y);
                } else {
                    int tempY;
                    tempY = ((Card) (column[col].get(column[col].size() - 1))).getY();
                    aCard.setY(tempY + Consts.CARD_GAP_Y);
                }
                column[col].add(aCard);
            }
        }
        // initFoundation
        aCard = deck.dealCard();
        aCard.setFaceUp(true);
        aCard.setX(Consts.FOUNDATION_X);
        aCard.setY(Consts.FOUNDATION_Y);
        foundation[0].add(aCard);
    }

    public void paintLayout(Graphics2D g2d) {
        Card aCard;
        // paint column
        for (int col = 0; col < Consts.NUM_COL; col++) {
            for (int row = 0; row < column[col].size(); row++) {
                aCard = (Card) (column[col].get(row));
                aCard.paintCard(g2d);
            }
        }
        // paint the foundation line
        for (int fCol = 0; fCol < Consts.NUM_SUIT; fCol++) {
            for (int fRow = 0; fRow < foundation[fCol].size(); fRow++) {
                aCard = (Card) foundation[fCol].get(fRow);
                if (aCard != null) {
                    aCard.paintCard(g2d);
                }
            }
        }
        // paint remaining cards in the deck
        int deckX = Consts.COL1_X + (Consts.NUM_COL - 1)
                * (Consts.CARD_W + Consts.CARD_GAP_X);
        ArrayList<Card> theDeck = deck.getDeck();
        for (Card remainingCard : theDeck) {
            remainingCard.setX(deckX);
            remainingCard.setY(Consts.FOUNDATION_Y);
            remainingCard.setFaceUp(false);
            remainingCard.paintCard(g2d);
        }
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public ArrayList[] getColumn() {
        return column;
    }

    public ArrayList[] getFoundation() {
        return foundation;
    }
}
