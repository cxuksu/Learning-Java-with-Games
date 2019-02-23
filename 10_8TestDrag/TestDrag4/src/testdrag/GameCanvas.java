/*
 * GameCanvas.java - A class defines a canvas for the game.
 */
package testdrag;

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
    private Card aCard;
    private ArrayList<Card> aList;

    private boolean firstTime = true;
    private Card clickCard;
    private Card removedCard;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        addMouseMotionListener(new MyMouseAdapter());
        addMouseListener(new MyMouseAdapter());
        aList = new ArrayList<>();
        initComponent();
    }

    private void initComponent() {
        deck = new Deck();
        initCard();
    }

    public void initCard() {
        for (int i = 0; i < 5; i++) {
            aCard = deck.dealCard();
            aCard.setX(Consts.COL1_X + i * (Consts.CARD_W + Consts.CARD_GAP_X));
            aCard.setY(Consts.COL1_Y);
            aCard.setFaceUp(true);
            aList.add(aCard);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < aList.size(); i++) {
            aCard = aList.get(i);
            aCard.paintCard(g2d);
        }
        if (removedCard != null) {
            removedCard.paintCard(g2d);
        }
    }

    class MyMouseAdapter extends MouseAdapter {

        int dx = 0, dy = 0;
        int clickIdx = 0;

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (firstTime) {
                if ((evt.getY() >= Consts.COL1_Y)
                        && (evt.getY() <= Consts.COL1_Y + Consts.CARD_H)) {
                    clickIdx = (evt.getX() - Consts.COL1_X)
                            / (Consts.CARD_W + Consts.CARD_GAP_X);
                    clickCard = aList.get(clickIdx);
                }
                dx = evt.getX() - clickCard.getX();
                dy = evt.getY() - clickCard.getY();
                firstTime = false;
            }
            if (clickCard != null) {
                clickCard.setX(evt.getX() - dx);
                clickCard.setY(evt.getY() - dy);
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            removedCard = clickCard;
            aList.remove(clickCard);
            adjustList(aList);

            firstTime = true;
            repaint();
        }

        // the coordinates (x, y) of each card should be adjusted for painting
        private void adjustList(ArrayList tList) {
            Card tCard;
            for (int i = 0; i < tList.size(); i++) {
                tCard = (Card) tList.get(i);
                tCard.setX(Consts.COL1_X + i * (Consts.CARD_W + Consts.CARD_GAP_X));
                tCard.setY(Consts.COL1_Y);
            }
        }
    }
}
