/*
 * BallSprite.java - A class that implements a ball sprite.
 */
package tornado;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class BallSprite extends AbsSprite2D {

    private int midx = (Consts.MAXX - Consts.MINX) / 2;
    private int midy;
    private int majorR;
    private int minorR;
    private int currAngle;
    private int moveAngle;

    public BallSprite() {
        super();
    }

    @Override
    public void initSprite() {
        majorR = 150;
        minorR = 50;
        midy = 180;
        setX(midx + getMajorR());
        setY(midy);
        setWidth(Consts.BALL_RADIUS);
        setHeight(Consts.BALL_RADIUS);
        currAngle = 360;
        moveAngle = 10;
        setColor(Color.RED);
        setActive(true);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
        currAngle = currAngle + moveAngle;
        if (currAngle >= 360) {
            currAngle = -360;
        }
        setX((int) (midx + Math.sin(currAngle * Consts.RADIAN) * getMajorR()));
        setY((int) (midy - Math.cos(currAngle * Consts.RADIAN) * getMinorR()));
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillOval(getX(), getY(), getWidth(), getHeight());
    }

    public int getMajorR() {
        return majorR;
    }

    public void setMajorR(int majorR) {
        this.majorR = majorR;
    }

    public int getMinorR() {
        return minorR;
    }

    public void setMinorR(int minorR) {
        this.minorR = minorR;
    }
}
