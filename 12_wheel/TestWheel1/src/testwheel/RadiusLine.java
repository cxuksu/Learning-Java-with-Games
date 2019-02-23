/*
 * RadiusLine.java -- A class that defines an object of radiusLine.
 */
package testwheel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author cxu
 */
public class RadiusLine {

    private final int cx;
    private final int cy;
    private int wheelRadius;
    private int rotateAngle;
    private int dAngle; // increase angle for g2d.rotate()
    private final int pRadius = 4; // radius of the red point
    private final double rPercent = 0.8; // radius percent for leaving margine

    public RadiusLine() {
        if (Consts.CV_WIDTH > Consts.CV_HEIGHT) {
            wheelRadius = (int) ((Consts.MAXY - Consts.MINY) / 2 * 0.8);
        }
        cx = (int) (Consts.MAXX - Consts.MINX) / 2;
        cy = (int) (Consts.MAXY - Consts.MINY) / 2;
    }

    public void updateLine() {
        rotateAngle += dAngle;
    }

    public void paintLine(Graphics2D g2d) {
        // paint the stop-indicator
        g2d.setColor(Color.RED);
        g2d.fillOval(cx - pRadius, cy - wheelRadius - 5 - 2 * pRadius,
                2 * pRadius, 2 * pRadius);
        // paint the wheel circle
        g2d.setColor(Color.GRAY);
        Stroke solid = new BasicStroke(5.0f);
        g2d.setStroke(solid);
        g2d.drawOval(cx - wheelRadius, cy - wheelRadius, 2 * wheelRadius,
                2 * wheelRadius);

        // paint the radius line
        g2d.setColor(Color.BLUE);
        // rotate the coordinate system around the center point
        g2d.rotate(Math.toRadians(rotateAngle), cx, cy);
        drawLine(g2d);
    }

    public void drawLine(Graphics2D g2d) {
        g2d.drawLine(cx, cy, cx, cy - wheelRadius);
    }

    public void reStartLine() {
        // continue the rotate from the starting angle
        rotateAngle = 0;
        //System.out.println("starting angle = " + rotateAngle);
        // randomly selects dAngle for making the wheel stopped randomly
        // avoiding dAngle == 36 since it makes the rotating line seems stoped
        dAngle = (int) (Math.random() * 33) + 2; // [2, 35)
        System.out.println("dAngle = " + dAngle);
    }
}
