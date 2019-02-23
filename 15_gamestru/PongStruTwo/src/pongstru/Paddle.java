/*
 * Paddle.java - A class that implements a paddle for the game Pong.
 */
package pongstru;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Paddle extends AbsSprite2D {

    public Paddle() {
        super();
    }

    @Override
    public void initSprite() {
        setX(Consts.MAXX - Consts.PADDLE_MARGIN);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.PADDLE_HEIGHT / 2);
        setColor(Color.ORANGE);
        setActive(true);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), Consts.PADDLE_WIDTH, Consts.PADDLE_HEIGHT);
    }
}
