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

    public GameCanvas() {
        addMouseMotionListener(new MyMouseAdapter());
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

        for (int i = 0; i < 5; i++) {
            aCard = aList.get(i);
            aCard.paintCard(g2d);
        }
    }

    class MyMouseAdapter extends MouseAdapter {

        boolean firstTime = true;
        Card clickCard;
        int dx, dy;

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (firstTime) {
                if ((evt.getY() >= Consts.COL1_Y)
                        && (evt.getY() <= Consts.COL1_Y + Consts.CARD_H)) {
                    int idx = (evt.getX() - Consts.COL1_X)
                            / (Consts.CARD_W + Consts.CARD_GAP_X);
                    clickCard = aList.get(idx);
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
    }
}
