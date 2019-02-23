/*
 * ArrowSprite.java - A class that defines an arrow that points to
 * the center position of the current barSprite.
 */
package bubblesort;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class ArrowSprite extends AbsSprite2D {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ArrowSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        setX(Consts.MARGIN + Consts.BARWIDTH / 2);
        setY(Consts.BASEY + Consts.INITBAR);
        setColor(Color.red);
        setVisible(true);
        setActive(true);
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.drawLine(getX(), getY(), getX() - 5, getY() + 5);
        g2d.drawLine(getX() + 1, getY(), getX() - 5 + 1, getY() + 5);
        g2d.drawLine(getX() - 1, getY(), getX() - 5 - 1, getY() + 5);
        g2d.drawLine(getX(), getY(), getX() + 5, getY() + 5);
        g2d.drawLine(getX() + 1, getY(), getX() + 5 + 1, getY() + 5);
        g2d.drawLine(getX() - 1, getY(), getX() + 5 - 1, getY() + 5);
        g2d.drawLine(getX(), getY(), getX(), getY() + 15);
        g2d.drawLine(getX() + 1, getY(), getX() + 1, getY() + 15);
        g2d.drawLine(getX() - 1, getY(), getX() - 1, getY() + 15);
    }

    @Override
    public void updateSprite() {
    }
}
