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
public class BallSprite extends AbsSprite2D implements Runnable {

    private Thread twinkling;
    private Graphics2D gra;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BallSprite() {
        super();
        initSprite();
    }

    @Override
    public void initSprite() {
        int cvX = (Consts.MAXX - Consts.MINX) / 2;
        int cvY = (Consts.MAXY - Consts.MINY) / 2;
        setX(cvX - Consts.BALL_RADIUS);
        setY(cvY - Consts.BALL_RADIUS);
        setWidth(2 * Consts.BALL_RADIUS);
        setHeight(2 * Consts.BALL_RADIUS);
        setDx(0);
        setDy(0);
        setColor(ranColor());
        setActive(false);
        setVisible(true);
    }

    public void ballStart() {
        if (twinkling == null) {
            twinkling = new Thread(this);
            twinkling.start();
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (true) {
                // it changes the color with higher speed than game loop
                paintColor(gra); // it performs both updating and painting colors
                Thread.sleep(15);
            }
        } catch (InterruptedException | NullPointerException ex) {
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(ranColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    // This method changes the colors of all balls.
    // It does not animate any balls that appear in the middle column line.
    // The classes ToLeftBall.java and ToRightBall.java have their own
    // updateSprte() methods that animate those balls to left or to right.
    @Override
    public void updateSprite() {
        setColor(ranColor());
    }

    public Color ranColor() {
        int redc = (int) (Math.random() * 256);
        int greenc = (int) (Math.random() * 256);
        int bluec = (int) (Math.random() * 256);
        Color ballc = new Color(redc, greenc, bluec);
        
        return ballc;
    }

    public void paintColor(Graphics2D g2d) {
        g2d.setColor(ranColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    public Graphics2D getGra() {
        return gra;
    }

    public void setGra(Graphics2D gra) {
        this.gra = gra;
    }
}
