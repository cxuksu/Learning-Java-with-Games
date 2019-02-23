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

    private final int radiusClock;
    private int radiusHand;
    private double handPercent;
    private Color handColor;
    private int turnAngle;
    private int moveX;
    private int moveY;

    public ClockHand(int radiusClock) {
        this.radiusClock = radiusClock;
    }

    public void initHand() {
        radiusHand = (int) (this.radiusClock * handPercent);
        moveX = (int) (Math.sin(turnAngle * Consts.RADIAN) * radiusHand);
        moveY = (int) (Math.cos(turnAngle * Consts.RADIAN) * radiusHand);
    }

    public void paintHand(Graphics2D g2d) {
        g2d.setColor(handColor);
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

    public int getTurnAngle() {
        return turnAngle;
    }

    public void setTurnAngle(int turnAngle) {
        this.turnAngle = turnAngle;
    }

    public Color getHandColor() {
        return handColor;
    }

    public void setHandColor(Color handColor) {
        this.handColor = handColor;
    }
}
