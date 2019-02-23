/*
 * BarSprite.java - A class that defines a bar sprite.
 */
package bubblesort;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class BarSprite extends AbsSprite2D {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BarSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        setWidth(Consts.BARWIDTH);
        setHeight(Consts.INITBAR);
        setVisible(true);
        setActive(false);
        int rRed = (int) (Math.random() * 256);
        int rBlue = (int) (Math.random() * 256);
        int rGreen = (int) (Math.random() * 256);
        Color aColor = new Color(rRed, rBlue, rGreen);
        setColor(aColor);
    }

    @Override
    public void updateSprite() {
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
