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
    private CardLayout cardLayout;
    private boolean firstTime;
    private Card clickCard;
    private int clickCol;
    private ArrayList<Card> tempList;
    private int tempLen;
    private ArrayList[] theColumn;
    private ArrayList[] theFoundation;

    public GameCanvas() {
        firstTime = true;
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        cardLayout = new CardLayout();
        cardLayout.setDeck(deck);
        cardLayout.initLayout(); // this needs the deck
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        cardLayout.paintLayout(g2d);
    }

    class MyMouseAdapter extends MouseAdapter {

        Card aCard;
        int dx, dy; // offset of (x, y) to (evt.getX(), evt.getY())

        // the mouseDragged() and mouseReleased() belong to
        // two different listeners (variables cannot be shared)
        @Override
        public void mouseDragged(MouseEvent evt) {
            theColumn = cardLayout.getColumn();
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
                    cardToFoundation(evt.getX(), tempList);
                    removeFromColumn(clickCol, tempList);
                } else {
                    // append cards in tempList to the destination column
                    int destCol = theCol(evt.getX()); // the destination column
                    cardToColumn(destCol, tempList);
                    removeFromColumn(clickCol, tempList);
                }
                // prevent a null click
                clickCard = null;
                tempList.clear();
                tempList = null;
            }
            firstTime = true; // after release one card, then select the next
            repaint();
        }

        private int theCol(int evtX) { // find the column based on the evt.getX()
            int relativeX = evtX - columnLeft(0);
            return (relativeX / (Consts.CARD_W + Consts.CARD_GAP_X));
        }

        private boolean dragTheCard(int evtX, int evtY, int cardX, int cardY) {
            boolean inside = false;
            if ((evtX >= cardX) && (evtX <= cardX + Consts.CARD_W)
                    && (evtY >= cardY) && (evtY <= cardY + Consts.CARD_H)) {
                inside = true;
            }
            return inside;
        }

        // append cards in the tempList to the destination column
        private void cardToColumn(int aCol, ArrayList tList) {
            Card tCard;
            for (Object tList1 : tList) {
                tCard = (Card) tList1;
                appendColumn(tCard, aCol);
            }
        }
        
        // remove cards from the clickCol (the source column)
        private void removeFromColumn(int aCol, ArrayList tList) {
            Card tCard;
            for (Object tList1 : tList) {
                tCard = (Card) tList1;
                theColumn[aCol].remove(tCard);
            }
        }

        // append one card to theCol
        private void appendColumn(Card theCard, int theCol) {
            if (!(theColumn[theCol].isEmpty())) {
                theCard.setX(columnLeft(theCol));
                theCard.setY(columnBottomCard(theCol).getY() + Consts.CARD_GAP_Y);
            } else {
                theCard.setX(columnLeft(theCol));
                theCard.setY(Consts.COL1_Y + Consts.CARD_GAP_Y);
            }
            theColumn[theCol].add(theCard);
        }

        // append cards in the tempList to the foundation
        private void cardToFoundation(int evtX, ArrayList tList) {
            int fCol;
            Card fCard, tCard;
            // assure the column in the foundation
            fCol = theCol(evtX);
            if ((fCol >= 0) && (fCol < Consts.NUM_SUIT)) {
                theFoundation = cardLayout.getFoundation();
                int aLen = theFoundation[fCol].size();
                if (aLen > 0) {
                    fCard = (Card) theFoundation[fCol].get(aLen - 1);
                    if ((fCard.getSuit() == clickCard.getSuit())
                            && (fCard.getValue() == clickCard.getValue() - 1)) {
                        for (Object tList1 : tList) {
                            tCard = (Card) tList1;
                            appendFoundation(tCard, fCol);
                        }
                    } else {
                    } // ignore it
                } else { // an empty slot
                    for (Object tList1 : tList) {
                        tCard = (Card) tList1;
                        appendFoundation(tCard, fCol);
                    }
                }
            }
        }

        // append a card to the foundation
        private void appendFoundation(Card aCard, int aCol) {
            // no matter it is empty or not
            aCard.setX(columnLeft(aCol));
            aCard.setY(Consts.FOUNDATION_Y);
            theFoundation[aCol].add(aCard);
        }

        private int columnLeft(int col) {
            return (Consts.COL1_X + col * Consts.CARD_W
                    + col * Consts.CARD_GAP_X);
        }

        private int columnRight(int col) {
            return (columnLeft(col) + Consts.CARD_W);
        }

        private Card columnBottomCard(int col) {
            int size = theColumn[col].size();
            return (Card) (theColumn[col].get(size - 1));
        }
    }
}
