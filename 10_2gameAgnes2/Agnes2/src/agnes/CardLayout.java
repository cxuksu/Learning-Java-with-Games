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

    private Deck deck; // for dealing a card to column or foundation

    public CardLayout() {
        column = new ArrayList[Consts.NUM_COL];
        foundation = new ArrayList[Consts.NUM_SUIT];
        initDataStructure();
    }

    private void initDataStructure() {
        for (int col = 0; col < Consts.NUM_COL; col++) {
            column[col] = new ArrayList<>();
        }
        for (int fCol = 0; fCol < Consts.NUM_SUIT; fCol++) {
            foundation[fCol] = new ArrayList<>();
        }
    }

    public void initLayout() {
        Card aCard;
        // init the layout with seven columns
        for (int row = 0; row < Consts.NUM_ROW; row++) {
            for (int col = 0; col < Consts.NUM_COL - row; col++) {
                aCard = deck.dealCard();
                aCard.setFaceUp(true);
                aCard.setX(Consts.COL1_X + col
                        * (Consts.CARD_W + Consts.CARD_GAP_X));
                if (column[col].isEmpty()) {
                    aCard.setY(Consts.COL1_Y);
                } else {
                    int tempY;
                    tempY = ((Card) (column[col].get(
                            column[col].size() - 1))).getY();
                    aCard.setY(tempY + Consts.CARD_GAP_Y);
                }
                column[col].add(aCard);
            }
        }
        // init the foundation
        aCard = deck.dealCard();
        aCard.setFaceUp(true);
        aCard.setX(Consts.FOUNDATION_X);
        aCard.setY(Consts.FOUNDATION_Y);
        foundation[0].add(aCard);
    }

    public void paintLayout(Graphics2D g2d) {
        Card aCard;

        // paint the layout with seven columns
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
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
