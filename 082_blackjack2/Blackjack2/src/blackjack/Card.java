/*
 * Card.java - A class defines one card.
 */
package blackjack;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class Card {

    private int x, y;
    private int width = 60, height = 80;
    private Image cardBack;
    private Image cardFront;
    private boolean faceUp;
    private int value;

    public Card() {
        faceUp = false;
        try {
            cardBack = ImageIO.read(getClass().getResource("../cards/Back.gif"));
        } catch (Exception e) {
        }
    }

    public void paintCard(Graphics2D g2d) {
        if (faceUp) {
            g2d.drawImage(cardFront, x, y, width, height, null);
        } else {
            g2d.drawImage(cardBack, x, y, width, height, null);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getCardFront() {
        return cardFront;
    }

    public void setCardFront(Image cardFront) {
        this.cardFront = cardFront;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
}
