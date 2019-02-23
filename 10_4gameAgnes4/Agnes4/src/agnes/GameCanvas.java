/*
 * GameCanvas.java - A class defines a canvas for the game.
 */
package agnes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private Deck deck;
    private CardLayout player;
    private boolean firstTime;
    private Card clickCard;
    private int clickCol;
    private ArrayList[] theColumn;
    private ArrayList<Card> tempList;
    private int tempLen;
    private ArrayList[] theFoundation;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        firstTime = true;
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        player = new CardLayout();
        player.setDeck(deck);
        player.initLayout(); // this method needs the deck
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        player.paintLayout(g2d);
    }

    class MyMouseAdapter extends MouseAdapter {

        Card aCard;
        int dx, dy; // offset of (x, y) to (evt.getX(), evt.getY())

        // the mouseDragged() and mouseReleased() belong to
        // two different listeners (variables cannot be shared)
        @Override
        public void mouseDragged(MouseEvent evt) {
            theColumn = player.getColumn();
            // select a tempList to be dragged
            if (firstTime) {
                // click the card and find the clickCol
                clickCol = theCol(evt.getX());
                if ((clickCol >= 0) && (clickCol < Consts.NUM_COL)) {
                    // get the clicked card and the tempList
                    for (int row = (theColumn[clickCol].size() - 1); row >= 0; row--) {
                        aCard = (Card) theColumn[clickCol].get(row);
                        if (dragTheCard(evt.getX(), evt.getY(),
                                aCard.getX(), aCard.getY())) { // (x, y) is inside the card
                            clickCard = aCard;
                            tempList = new ArrayList<>();
                            tempLen = (theColumn[clickCol].size() - 1) - row + 1;
                            for (int i = 0; i < tempLen; i++) {
                                aCard = (Card) theColumn[clickCol].get(row + i);
                                tempList.add(aCard);
                            }
                            break;
                        } else {
                            clickCard = null;
                        }
                    }
                }
                if (clickCard != null) {
                    // find the offset between the clicked point and the card (x, y)
                    dx = evt.getX() - clickCard.getX(); // dx and dy must be a constant
                    dy = evt.getY() - clickCard.getY();
                }
                firstTime = false;
            }
            if (tempList != null) {
                // drag the entire tempList around
                for (int k = 0; k < tempLen; k++) {
                    tempList.get(k).setX(evt.getX() - dx);
                    tempList.get(k).setY((evt.getY() - dy) + k * Consts.CARD_GAP_Y);
                }
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            if (tempList != null) {
                if (evt.getY() < (Consts.COL1_Y + Consts.CARD_GAP_Y)) {
                    // append cards in tempList to the foundation line
                    if (!cardToFoundation(evt.getX(), tempList)) {
                        sendBackTempList(clickCol, tempList);
                    }
                } else {
                    // append cards in tempList to the destination column
                    int destCol = theCol(evt.getX()); // the destination column
                    if (!cardToColumn(destCol, tempList)) {
                        sendBackTempList(clickCol, tempList);
                    }
                }
                // prevent a null click
                clickCard = null;
                tempList.clear();
                tempList = null;
            }
            firstTime = true; // for allowing clicking the next card
            repaint();
        }
        
        // find the index of a column based on the evt.getX()
        private int theCol(int evtX) {
            int relativeX = evtX - columnLeft(0);
            return (relativeX / (Consts.CARD_W + Consts.CARD_GAP_X));
        }

        // assure the card or the first card of a tempList is dragged
        private boolean dragTheCard(int evtX, int evtY, int cardX, int cardY) {
            boolean inside = false;
            if ((evtX >= cardX) && (evtX <= cardX + Consts.CARD_W)
                    && (evtY >= cardY) && (evtY <= cardY + Consts.CARD_H)) {
                inside = true;
            }
            return inside;
        }

        // attach the tempList to the destination column
        private boolean cardToColumn(int aCol, ArrayList tList) {
            Card tCard;
            boolean success = false;
            // append one card to theCol
            if (!(theColumn[aCol].isEmpty())) { // not empty
                tCard = (Card)tList.get(0);
                if ((sameColor(columnBottomCard(aCol), tCard)) &&
                            (descendOrder(columnBottomCard(aCol), tCard))) {
                    for (Object tList1 : tList) {
                        tCard = (Card) tList1;
                        appendColumn(tCard, aCol);
                    }
                    success = true;
                } 
            } else { // is empty
                for (Object tList1 : tList) {
                    tCard = (Card) tList1;
                    appendColumn(tCard, aCol);
                }
                success = true;
            }
            // always to remove the tempList from the source column
            removeFromColumn(clickCol, tempList);
            return success;
        }

        // remove tList from the source column
        private void removeFromColumn(int aCol, ArrayList tList) {
            Card tCard;
            for (Object tList1 : tList) {
                tCard = (Card) tList1;
                theColumn[aCol].remove(tCard);
            }
        }

        // append a card to the destination column
        private void appendColumn(Card theCard, int theCol) {
            if (!theColumn[theCol].isEmpty()) {
                theCard.setX(columnLeft(theCol));
                theCard.setY(columnBottomCard(theCol).getY() + Consts.CARD_GAP_Y);
            } else {
                theCard.setX(columnLeft(theCol));
                theCard.setY(Consts.COL1_Y + Consts.CARD_GAP_Y);
            }
            theColumn[theCol].add(theCard);
        }

        // assure the same color of the appending card and the exposed card
        private boolean sameColor(Card existCard, Card addCard) {
            boolean same = false;
            if ((existCard.getSuit() == Consts.CARD_CLUBS) && 
                    ((addCard.getSuit() == Consts.CARD_CLUBS) ||
                    (addCard.getSuit() == Consts.CARD_SPADES))) {
                same = true;
            } else if ((existCard.getSuit() == Consts.CARD_SPADES) && 
                    ((addCard.getSuit() == Consts.CARD_CLUBS) ||
                    (addCard.getSuit() == Consts.CARD_SPADES))) {
                same = true;
            } else if ((existCard.getSuit() == Consts.CARD_DIAMONDS) && 
                    ((addCard.getSuit() == Consts.CARD_DIAMONDS) ||
                    (addCard.getSuit() == Consts.CARD_HEARTS))) {
                same = true;
            } else if ((existCard.getSuit() == Consts.CARD_HEARTS) && 
                    ((addCard.getSuit() == Consts.CARD_DIAMONDS) ||
                    (addCard.getSuit() == Consts.CARD_HEARTS))) {
                same = true;
            } 
            return same;
        }
        
        // assure the two cards in order
        private boolean descendOrder(Card existCard, Card addCard) {
            boolean withOrder = false;
            if (existCard.getValue() == (addCard.getValue()+1)) {
                withOrder = true;
            }
            return withOrder;
        }
        
        // send the tempList back to the source column
        private void sendBackTempList(int sCol, ArrayList tList) {
            Card tCard;
            for (Object tList1 : tList) {
                tCard = (Card) tList1;
                appendColumn(tCard, sCol);
            }    
        }
        
        // attach the tempList to the foundation
        private boolean cardToFoundation(int evtX, ArrayList tList) {
            boolean success = false;
            int fCol, theCol = 0;
            Card fCard, tCard;
            // assure the destination foundation
            fCol = theCol(evtX);
            if ((fCol >= 0) && (fCol < Consts.NUM_SUIT)) {
                theFoundation = player.getFoundation();
                int aLen = theFoundation[fCol].size();
                if (aLen > 0) {
                    fCard = (Card) theFoundation[fCol].get(aLen - 1);
                    if (sameSuit(fCard, clickCard) && ascendOrder(fCard, clickCard)) {
                        for (Object tList1 : tList) {
                            tCard = (Card) tList1;
                            appendFoundation(tCard, fCol);
                        }
                        success = true;
                    } else {
                    } // ignore it
                } else { // an empty slot
                    for (int col = 0; col < Consts.NUM_SUIT; col++) {
                        if (!theFoundation[col].isEmpty()) {
                            theCol = col;
                            break;
                        }
                    }
                    if (sameRank((Card)theFoundation[theCol].get(0), 
                            (Card)tList.get(0))) {
                        for (Object tList1 : tList) {
                            tCard = (Card) tList1;
                            appendFoundation(tCard, fCol);
                        }
                        success = true;
                    }
                }
            }
            removeFromColumn(clickCol, tList);
            return success;
        }

        private boolean sameSuit(Card existCard, Card addCard) {
            boolean same = false;
            if (existCard.getSuit() == addCard.getSuit()) {
                same = true;
            }
            return same;
        }
        
        private boolean ascendOrder(Card existCard, Card addCard) {
            boolean ascend = false;
            if (existCard.getValue() == addCard.getValue() - 1) {
                ascend = true;
            }
            return ascend;
        }
        
        // append a card to the foundation
        private void appendFoundation(Card aCard, int aCol) {
            // no matter it is empty or not
            aCard.setX(columnLeft(aCol));
            aCard.setY(Consts.FOUNDATION_Y);
            theFoundation[aCol].add(aCard);
        }

        private boolean sameRank(Card card1, Card card2) {
            boolean rank = false;
            if (card1.getValue() == card2.getValue()) {
                rank = true;
            }
            return rank;
        }
        
        // get the x-coordinate of the left edge of a column
        private int columnLeft(int col) {
            return (Consts.COL1_X + col * Consts.CARD_W
                    + col * Consts.CARD_GAP_X);
        }

        // get the x-coordinate of the right edge of a column
        private int columnRight(int col) {
            return (columnLeft(col) + Consts.CARD_W);
        }

        // get the exposed card of a column
        private Card columnBottomCard(int col) {
            int size = theColumn[col].size();
            return (Card) (theColumn[col].get(size - 1));
        }

        // deal one card from the stock to every column
        @Override
        public void mouseClicked(MouseEvent evt) {
            theColumn = player.getColumn();
            int deckX = columnLeft(0) + (Consts.NUM_COL - 1) * 
                    (Consts.CARD_W + Consts.CARD_GAP_X);
            if ((evt.getX() >= deckX) && (evt.getX() <= deckX + Consts.CARD_W)
                    && (evt.getY() >= Consts.FOUNDATION_Y) && 
                    (evt.getY() <= Consts.FOUNDATION_Y + Consts.CARD_H)) {
                for (int col = 0; col < Consts.NUM_COL; col++) {
                    aCard = deck.dealCard();
                    if (aCard != null) {
                        aCard.setFaceUp(true);
                        appendColumn(aCard, col);
                    }
                }
            }
        }
    }
}
