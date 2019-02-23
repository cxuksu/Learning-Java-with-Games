/*
 * BallSprite.java - A class that implements a ball sprite.
 */
package symball;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class BallSprite extends AbsSprite2D {

    public BallSprite() {
        super();
    }

    @Override
    public void initSprite() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setX(cvX - Consts.BALL_RADIUS);
        setY(cvY - Consts.BALL_RADIUS);
        setWidth(2 * Consts.BALL_RADIUS);
        setHeight(2 * Consts.BALL_RADIUS);
        setDx(1);
        setDy(0);
        setColor(Color.RED);
        setActive(false);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
        setX(getX() + getDx());
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
