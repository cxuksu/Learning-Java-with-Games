/*
 * ClockFace.java - A class defines the clock face of an analog clock.
 */
package analogclock;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class ClockFace {

    private int radiusClock;
    private int numCircles; // clock frame consists of number of circles
    private Color circleColor;
    private int radiusDotCircle;
    private int numDots;
    private int dotRadius;
    private Color dotColor;
    private int da; // distance in angle between dots

    public ClockFace() {
        if (Consts.CV_WIDTH > Consts.CV_HEIGHT) {
            radiusClock = (Consts.MAXY_DIS - Consts.MINY_DIS) / 2;
        } else {
            radiusClock = (Consts.MAXX_DIS - Consts.MINX_DIS) / 2;
        }
        numCircles = 8;
        circleColor = Color.ORANGE;
        // distance between the dot circle and the frame is 10
        radiusDotCircle = radiusClock - (int)(radiusClock * 0.10); 
        numDots = 60;
        dotRadius = 3;
        dotColor = Color.GREEN;
        da = 6; // 6 degrees
    }
    
    public void paintFace(Graphics2D g2d) {
         // paint the frame of the analog clock 
        g2d.setColor(circleColor);
        for (int i = 0; i < numCircles; i++) {
            g2d.drawOval(Consts.CENTER_X - (radiusClock - i), 
                    Consts.CENTER_Y - (radiusClock - i),
                    2 * (radiusClock - i), 2 * (radiusClock - i));
        }

        // paint the 60 dots
        g2d.setColor(dotColor);
        for (int j = 0; j < numDots; j++) {
            int dotCx = Consts.CENTER_X + 
                    (int) (Math.sin(da * j * Consts.RADIAN) * radiusDotCircle);
            int dotCy = Consts.CENTER_Y - 
                    (int) (Math.cos(da * j * Consts.RADIAN) * radiusDotCircle);
            g2d.fillOval(dotCx - dotRadius, dotCy - dotRadius, 
                    2 * dotRadius, 2 * dotRadius);
        }
    }

    public int getRadiusClock() {
        return radiusClock;
    }
}
