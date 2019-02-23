/*
 * ClockHand.java - A class defines the superclass clock hands of an analog clock.
 */
package analogclock;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class ClockHand {

    private int radiusClock;
    private int radiusHand;
    private double handPercent;
    private Color handColor;
    private double turnAngle;
    private int moveX;
    private int moveY;
    private double anglePerSec;

    public ClockHand(int radiusClock) {
        this.radiusClock = radiusClock;
    }

    public void initHand() {
        radiusHand = (int) (this.radiusClock * getHandPercent());
        moveX = (int) (Math.sin(getTurnAngle() * Consts.RADIAN) * radiusHand);
        moveY = (int) (Math.cos(getTurnAngle() * Consts.RADIAN) * radiusHand);
    }

    public void updateHand() {
        setTurnAngle(getTurnAngle() + getAnglePerSec());
        if (getTurnAngle() == 360) {
            setTurnAngle(-360);
        }
        moveX = (int) (Math.sin(getTurnAngle() * Consts.RADIAN) * radiusHand);
        moveY = (int) (Math.cos(getTurnAngle() * Consts.RADIAN) * radiusHand);
    }

    public void paintHand(Graphics2D g2d) {
        g2d.setColor(getHandColor());
        int tipX = Consts.CENTER_X + moveX;
        int tipY = Consts.CENTER_Y - moveY;
        g2d.drawLine(Consts.CENTER_X, Consts.CENTER_Y, tipX, tipY);
    }

    public double getHandPercent() {
        return handPercent;
    }

    public void setHandPercent(double handPercent) {
        this.handPercent = handPercent;
    }

    public double getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(double turnAngle) {
        this.turnAngle = turnAngle;
    }

    public Color getHandColor() {
        return handColor;
    }

    public void setHandColor(Color handColor) {
        this.handColor = handColor;
    }

    public double getAnglePerSec() {
        return anglePerSec;
    }

    public void setAnglePerSec(double anglePerSec) {
        this.anglePerSec = anglePerSec;
    }
}
