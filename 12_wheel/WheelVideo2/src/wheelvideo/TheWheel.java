/*
 * TheWheel.java - A class that defines the wheel component.
 */
package wheelvideo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author cxu
 */
public class TheWheel {

    private final int cx;
    private final int cy;
    private int wheelRadius;
    private int rotateAngle;
    private int dAngle; // increase angle for g2d.rotate()
    private final int pRadius = 4; // radius of the red point
    private final double rPercent = 0.8; // radius percent for leaving margine
    private final int rotateToStr = 13; // rotate the angles for painting strings
    private final int rotateToLine = 23; // rotate the angles for painting lines
    private final int wheelLen = 10; // assume the wheel has 10 integers
    private final String[] wheel;

    public TheWheel() {
        if (Consts.CV_WIDTH > Consts.CV_HEIGHT) {
            wheelRadius = (int) ((Consts.MAXY - Consts.MINY) / 2 * 0.8);
        }
        cx = (int) (Consts.MAXX - Consts.MINX) / 2;
        cy = (int) (Consts.MAXY - Consts.MINY) / 2;

        // add all of integers into wheel[]
        wheel = new String[wheelLen];
        int ranInt;
        for (int i = 0; i < wheelLen; i++) {
            ranInt = (int) ((Math.random() * 1000) + 100); // [100, 1100)
            if (i == 5) {
                wheel[i] = "Zero";
            } else {
                wheel[i] = Integer.toString(ranInt);
            }
        }
        System.out.println("wheel[0] = " + wheel[0]);
    }

    public void updateWheel() {
        rotateAngle += dAngle;
        //System.out.println("rotateAngle = " + rotateAngle);
    }

    public void paintWheel(Graphics2D g2d) {
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

        // paint the wheel
        g2d.setColor(Color.BLUE);
        // rotate the coordinate system around the center point
        g2d.rotate(Math.toRadians(rotateAngle), cx, cy);
        drawWheel(g2d);
    }

    public void drawWheel(Graphics2D g2d) {
        g2d.setFont(new Font("Times", Font.BOLD, 30));
        for (int i = 0; i < wheelLen; i++) {
            // paint number strings: rotate the angle from the line to the number
            g2d.rotate(Math.toRadians(rotateToStr), cx, cy);
            int charRadius = (int) (wheelRadius * rPercent);
            String aStr = wheel[i];
            for (int j = 0; j < aStr.length(); j++) {
                g2d.drawString(aStr.substring(j, j + 1), cx, (cy - charRadius));
                charRadius -= 25;
            }
            // paint lines: rotate the angle from the number string to the next line
            g2d.rotate(Math.toRadians(rotateToLine), cx, cy);
            g2d.drawLine(cx, cy, cx, cy + wheelRadius);
        }
    }

    public String stopStatistic() {
        // For checking the calculations, all of System.out.println()
        // statements can be released for printing related information.
        //System.out.println("rotateAngle = " + rotateAngle);
        // the over turning angle A = (total rotated angles % 360)
        // due to the opposite direction against the red point
        // the over turning angle in the clock wise direction B = 360 - A
        // the index of the slot in the wheel theIndex = B / 36
        int theIndex = (360 - (rotateAngle % 360)) / 36;
        //System.out.println("theIndex = " + theIndex);
        // the score is wheel[theIndex]
        //System.out.println("wheel[theIndex] = " + wheel[theIndex]);
        return wheel[theIndex];
    }

    public void reStartWheel() {
        // continue the rotate from the angle where the wheel stopped
        rotateAngle = rotateAngle % 360;
        //System.out.println("starting angle = " + rotateAngle);
        // randomly selects dAngle for making the wheel stopped randomly
        // avoiding dAngle == 36 since it makes the rotating line seems stoped
        dAngle = (int) (Math.random() * 33) + 2; // [2, 35)
        //System.out.println("dAngle = " + dAngle);
    }
}
