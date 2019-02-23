/*
 * WheelGUI.java - A class defines the wheel component.
 */
package wheelvideo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class WheelGUI extends JPanel {

    private final String wheel[];
    private final int wheelLen = 10; // assume that
    private int cx, cy;
    private int wheelRadius;
    private final int pRadius = 4; // radius of the red point
    private final double rPercent = 0.8; // radius percent for leaving margine

    public WheelGUI() {
        wheel = new String[wheelLen];
        initWheel();
    }

    private void initWheel() {
        if (Consts.CV_WIDTH > Consts.CV_HEIGHT) {
            wheelRadius = (int) ((Consts.MAXY - Consts.MINY) / 2 * rPercent);
        } else {
            wheelRadius = (int) ((Consts.MAXX - Consts.MINX) / 2 * rPercent);
        }
        int ranInt;
        for (int i = 0; i < wheelLen; i++) {
            ranInt = (int) ((Math.random() * 1000) + 100); // [100, 1100)
            if (i == 5) {
                wheel[i] = "Zero";
            } else {
                wheel[i] = Integer.toString(ranInt); // all are Strings
            }
        }
        cx = (int) (Consts.MAXX - Consts.MINX) / 2;
        cy = (int) (Consts.MAXY - Consts.MINY) / 2;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

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
        drawWheel(g2d);
    }
    
    public void drawWheel(Graphics2D g2d) {
        g2d.setFont(new Font("Times", Font.BOLD, 30));
        // rotate the angle for painting strings and lines
        int rotateToStr = 13;
        int rotateToLine = 23;
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
}
