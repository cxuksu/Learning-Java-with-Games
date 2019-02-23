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

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BallSprite() {
        super();
    }

    @Override
    public void initSprite() {
    }

    // a special method that has a parameter "section"
    public void initSprite(int section) {
        switch (section) {
            case 1:
                midx = 30;
                midy = 360;
                majorR = (int) (Math.random() * 3);
                minorR = (int) (Math.random() * 10);
                break;
            case 2:
                midx = 30 + 10;
                midy = 340;
                majorR = (int) (Math.random() * 10);
                minorR = (int) (Math.random() * 10);
                break;
            case 3:
                midx = 30 + 25;
                midy = 320;
                majorR = (int) (Math.random() * 15);
                minorR = (int) (Math.random() * 12);
                break;
            case 4:
                midx = 30 + 35;
                midy = 300;
                majorR = (int) (Math.random() * 20);
                minorR = (int) (Math.random() * 15);
                break;
            case 5:
                midx = 30 + 38;
                midy = 280;
                majorR = (int) (Math.random() * 30);
                minorR = (int) (Math.random() * 18);
                break;
            case 6:
                midx = 30 + 40;
                midy = 260;
                majorR = (int) (Math.random() * 40);
                minorR = (int) (Math.random() * 20);
                break;
            case 7:
                midx = 30 + 50;
                midy = 230;
                majorR = (int) (Math.random() * 52);
                minorR = (int) (Math.random() * 24);
                break;
            case 8:
                midx = 30 + 70;
                midy = 200;
                majorR = (int) (Math.random() * 65);
                minorR = (int) (Math.random() * 30);
                break;
            case 9:
                midx = 30 + 100;
                midy = 160;
                majorR = (int) (Math.random() * 85);
                minorR = (int) (Math.random() * 35);
                break;
            case 10:
                midx = 30 + 140;
                midy = 120;
                majorR = (int) (Math.random() * 105);
                minorR = (int) (Math.random() * 45);
                break;
            case 11:
                midx = 30 + 160;
                midy = 90;
                majorR = (int) (Math.random() * 130);
                minorR = (int) (Math.random() * 60);
                break;
            case 12:
                midx = 30 + 200;
                midy = 50;
                majorR = (int) (Math.random() * 160);
                minorR = (int) (Math.random() * 80);
                break;
            case 13:
                midx = 30 + 240;
                midy = 30;
                majorR = (int) (Math.random() * 200);
                minorR = (int) (Math.random() * 100);
                break;
            default:
                break;
        }

        setX(midx + getMajorR());
        setY(midy);
        setWidth((int) (2 * Math.random() * Consts.BALL_RADIUS));
        setHeight((int) (2 * Math.random() * Consts.BALL_RADIUS));
        currAngle = (int) (Math.random() * 360);
        moveAngle = (int) (Math.random() * 10);
        setActive(true);
        setVisible(true);
    }

    public Color ranGrayColor() {
        int aColor = (int) (Math.random() * 2);
        Color returnColor = null;
        if (aColor == 0) {
            returnColor = Color.GRAY;
        } else if (aColor == 1) {
            returnColor = Color.LIGHT_GRAY;
        }
        return returnColor;
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
