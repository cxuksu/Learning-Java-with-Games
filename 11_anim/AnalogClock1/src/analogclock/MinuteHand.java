/*
 * MinuteHand.java - A class defines the minute hand of an analog clock.
 */
package analogclock;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class MinuteHand {

    private int radiusClock;
    private int radiusMinute;
    private Color minuteColor;
    private double minuteTurnAngle;
    private int minuteMoveX;
    private int minuteMoveY;

    public MinuteHand(int radiusClock) {
        this.radiusClock = radiusClock;
        radiusMinute = (int) (this.radiusClock * 0.85);
        minuteColor = Color.BLACK;
        // intialize the minute hand
        minuteTurnAngle = 18; // assume 18 degrees
        minuteMoveX = (int) (Math.sin(minuteTurnAngle * Consts.RADIAN)
                * radiusMinute);
        minuteMoveY = (int) (Math.cos(minuteTurnAngle * Consts.RADIAN)
                * radiusMinute);
    }

    public void paintMinuteHand(Graphics2D g2d) {
        g2d.setColor(minuteColor);
        int minuteCx = Consts.CENTER_X + minuteMoveX;
        int minuteCy = Consts.CENTER_Y - minuteMoveY;
        g2d.drawLine(Consts.CENTER_X, Consts.CENTER_Y, minuteCx, minuteCy);
    }
}
