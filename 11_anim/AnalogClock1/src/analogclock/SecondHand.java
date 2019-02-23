/*
 * SecondHand.java - A class defines the second hand of an analog clock.
 */
package analogclock;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class SecondHand {

    private int radiusClock;
    private int radiusSecond;
    private Color secondColor;
    private int secTurnAngle;
    private int secMoveX;
    private int secMoveY;

    public SecondHand(int radiusClock) {
        this.radiusClock = radiusClock;
        radiusSecond = (int) (this.radiusClock * 0.95);
        secondColor = Color.RED;
        // initialize the second hand
        secTurnAngle = 0;
        secMoveX = (int) (Math.sin(secTurnAngle * Consts.RADIAN) * 
                radiusSecond);
        secMoveY = (int) (Math.cos(secTurnAngle * Consts.RADIAN) * 
                radiusSecond);
    }

    public void paintSecondHand(Graphics2D g2d) {
        g2d.setColor(secondColor);
        int secCx = Consts.CENTER_X + secMoveX;
        int secCy = Consts.CENTER_Y - secMoveY;
        g2d.drawLine(Consts.CENTER_X, Consts.CENTER_Y, secCx, secCy);
    }
}
