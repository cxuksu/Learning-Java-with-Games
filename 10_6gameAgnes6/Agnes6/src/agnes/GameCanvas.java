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
    private PlayerPanel playerPanel;
    private boolean gameTerminate;
    // mouseDragged() and mouseReleased() belong to two listeners
    // these variables have to be global (they won't work if inside the listener
    private boolean firstTime;
    private Card clickCard;
    private int clickCol;
    private ArrayList[] theColumn;
    private ArrayList<Card> tempList;
    private int tempLen;
    private ArrayList[] theFoundation;

    int overCol, beginCol;
    ArrayList<Integer> overColumnList;
    ArrayList<Integer> overFoundationList;

    public GameCanvas() {
        gameTerminate = false;
        firstTime = true;
        overColumnList = new ArrayList<>();
        overFoundationList = new ArrayList<>();
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        initCardLayout();
    }

    public void initCardLayout() {
        cardLayout = new CardLayout();
        cardLayout.setDeck(deck);
        cardLayout.initLayout(); // this method needs the deck
        theColumn = cardLayout.getColumn();
        theFoundation = cardLayout.getFoundation();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        cardLayout.paintLayout(g2d);
    }

    public void renewGame() {
        gameTerminate = false;
        playerPanel.setGameTerminate(gameTerminate);
        deck = new Deck();
        initCardLayout();
        repaint();
    }

    class MyMouseAdapter extends MouseAdapter {

        Card aCard;
        int dx, dy; // offset of (x, y) to (evt.getX(), evt.getY())

        // the mouseDragged() and mouseReleased() belong to
        // two different listeners (variables cannot be shared)
        @Override
        public void mouseDragged(MouseEvent evt) {
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
                beginCol = clickCol; // the original column
                firstTime = false;
            }
            if (tempList != null) {
                // drag the entire tempList around
                for (int k = 0; k < tempLen; k++) {
                    tempList.get(k).setX(evt.getX() - dx);
                    tempList.get(k).setY((evt.getY() - dy) + k * Consts.CARD_GAP_Y);
                }
            }
            // place the tempList on the top of the explored card
            Card dragCard;
            dragCard = tempList.get(0);
            if (dragCard.getY() >= Consts.COL1_Y) { // for layout
                overCol = theCol(dragCard.getX() + Consts.CARD_W);
                if ((overCol > beginCol) || (overCol < beginCol)) {
                    overColumnList.add(overCol); // keeps track of the overCol
                    topColumn(overCol, tempList); // "add" tempList on the overCol
                    beginCol = overCol; // more beginCol to the current overCol
                }
            } else { // for foundation
                overCol = theCol(dragCard.getX());
                if (overCol < beginCol) {
                    if (overCol < Consts.NUM_SUIT) {
                        overFoundationList.add(overCol);
                        topFoundation(overCol, tempList);
                        beginCol = overCol;
                    }
                }
                int overCol2 = theCol(dragCard.getX() + Consts.CARD_W);
                if (overCol2 > beginCol) {
                    if (overCol2 < Consts.NUM_SUIT) {
                        overFoundationList.add(overCol2);
                        topFoundation(overCol2, tempList);
                        beginCol = overCol2;
                    }
                }
                int overY = dragCard.getY();
                if (overY < (Consts.FOUNDATION_Y + Consts.CARD_H)) {
                    overFoundationList.add(overCol);
                    topFoundation(overCol, tempList);
                }
            }

            repaint();
        }

        private void topColumn(int aCol, ArrayList tList) {
            Card tCard;
            if (aCol < Consts.NUM_COL) {
                for (int i = 0; i < tList.size(); i++) {
                    tCard = (Card) tList.get(i);
                    theColumn[aCol].add(tCard);
                }
            }
        }

        private void topFoundation(int aCol, ArrayList tList) {
            Card tCard;
            if ((aCol < Consts.NUM_SUIT) && (!theFoundation[aCol].isEmpty())) {
                for (int i = 0; i < tList.size(); i++) {
                    tCard = (Card) tList.get(i);
                    theFoundation[aCol].add(tCard);
                }
            }
        }

        private void removeTopColumn(int aCol, ArrayList tList) {
            Card tCard;
            if ((aCol >= 0) && (aCol < Consts.NUM_COL)) {
                for (int i = (tList.size() - 1); i >= 0; i--) {
                    tCard = (Card) tList.get(i);
                    theColumn[aCol].remove(tCard);
                }
            }
        }

        private void removeTopFoundation(int aCol, ArrayList tList) {
            Card theCard;
            if ((aCol < Consts.NUM_SUIT) && (tList != null)) {
                for (Object tCard : tList) {
                    theCard = (Card) tCard;
                    theFoundation[aCol].remove(theCard);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            // remove all of tempList the have been added in overCol
            if (!overColumnList.isEmpty()) {
                for (int p = 0; p < overColumnList.size(); p++) {
                    removeTopColumn(overColumnList.get(p), tempList);
                }
                overColumnList.clear();
            }
            if (!overFoundationList.isEmpty()) {
                for (int p = 0; p < overFoundationList.size(); p++) {
                    removeTopFoundation(overFoundationList.get(p), tempList);
                }
            }

            if (tempList != null) {
                if (evt.getY() < Consts.COL1_Y) { // to foundation
                    // append cards in tempList to the foundation line
                    if (!cardToFoundation(evt.getX(), tempList)) {
                        sendBackTempList(clickCol, tempList);
                    } else { // success, check whether game is over
                        if (isOver()) {
                            gameTerminate = true;
                            playerPanel.setGameTerminate(true);
                        }
                    }
                } else { // to Column
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
            firstTime = true; // after release one card, then select the next
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
            removeTopColumn(aCol, tList);
            if (!(theColumn[aCol].isEmpty())) { // not empty
                tCard = (Card) tList.get(0);
                if ((sameColor(tCard, exploredCardColumn(aCol)))
                        && (decendingRank(tCard, exploredCardColumn(aCol)))) {
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
            // always to remove the tempList from the sourc column
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
                theCard.setY(exploredCardColumn(theCol).getY()
                        + Consts.CARD_GAP_Y);
            } else {
                theCard.setX(columnLeft(theCol));
                theCard.setY(Consts.COL1_Y);
            }
            theColumn[theCol].add(theCard);
        }

        // assure the same color of the appending card and the exposed card
        private boolean sameColor(Card card1, Card card2) {
            boolean same = false;
            if ((card1.getSuit() == Consts.CARD_CLUBS)
                    && (card2.getSuit() == Consts.CARD_CLUBS)
                    || (card2.getSuit() == Consts.CARD_SPADES)) {
                same = true;
            } else if ((card1.getSuit() == Consts.CARD_SPADES)
                    && (card2.getSuit() == Consts.CARD_CLUBS)
                    || (card2.getSuit() == Consts.CARD_SPADES)) {
                same = true;
            } else if ((card1.getSuit() == Consts.CARD_DIAMONDS)
                    && (card2.getSuit() == Consts.CARD_DIAMONDS)
                    || (card2.getSuit() == Consts.CARD_HEARTS)) {
                same = true;
            } else if ((card1.getSuit() == Consts.CARD_HEARTS)
                    && (card2.getSuit() == Consts.CARD_DIAMONDS)
                    || (card2.getSuit() == Consts.CARD_HEARTS)) {
                same = true;
            }
            return same;
        }

        // assure the two cards in a decending order
        private boolean decendingRank(Card card1, Card card2) {
            boolean withOrder = false;
            if (card1.getValue() == (card2.getValue() - 1)) {
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
                int aLen = theFoundation[fCol].size();
                if (aLen > 0) {
                    fCard = (Card) theFoundation[fCol].get(aLen - 1);
                    if ((fCard.getSuit() == clickCard.getSuit())
                            && (fCard.getValue() == (clickCard.getValue() - 1))) {
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
                    if (sameRank((Card) theFoundation[theCol].get(0),
                            (Card) tList.get(0))) {
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
        private Card exploredCardColumn(int col) {
            int size = theColumn[col].size();
            return (Card) (theColumn[col].get(size - 1));
        }

        // get the exposed card of a column
        private Card lastCardFoundation(int col) {
            int size = theFoundation[col].size();
            return (Card) (theFoundation[col].get(size - 1));
        }

        private boolean isOver() {
            boolean over = false;
            int count = 0;
            for (int fCol = 0; fCol < Consts.NUM_SUIT; fCol++) {
                if (!theFoundation[fCol].isEmpty()) {
                    if ((lastCardFoundation(fCol).getValue() == 13)) {
                        count += 1;
                    }
                }
            }
            if (count == Consts.NUM_SUIT) {
                over = true;
            }
            return over;
        }

        // deal one card from the stock to every column
        @Override
        public void mouseClicked(MouseEvent evt) {
            theColumn = cardLayout.getColumn();
            int deckX = columnLeft(0) + (Consts.NUM_COL - 1)
                    * (Consts.CARD_W + Consts.CARD_GAP_X);
            if ((evt.getX() >= deckX) && (evt.getX() <= deckX + Consts.CARD_W)
                    && (evt.getY() >= Consts.FOUNDATION_Y)
                    && (evt.getY() <= Consts.FOUNDATION_Y + Consts.CARD_H)) {
                for (int col = 0; col < Consts.NUM_COL; col++) {
                    aCard = deck.dealCard();
                    if (aCard != null) {
                        aCard.setFaceUp(true);
                        appendColumn(aCard, col);
                    }
                }
            }
            repaint();
        }
    }

    public void setPlayerPanel(PlayerPanel playerPanel) {
        this.playerPanel = playerPanel;
    }
}
