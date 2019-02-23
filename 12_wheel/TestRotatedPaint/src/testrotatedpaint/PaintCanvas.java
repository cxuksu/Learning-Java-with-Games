/*
 * PaintCanvas.java - A class that defines a canvas for illustrating 
 * the principle of rotate.
 */
package testrotatedpaint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PaintCanvas extends JPanel {

    private final int cx = 250;
    private final int cy = 200;
    
    public PaintCanvas() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.red);
        g2d.setFont(new Font("Times", Font.BOLD, 30));
        
        String aStr = "Zero";

        int radiusCP = 2;
        g2d.drawOval(cx - radiusCP, cy - radiusCP, 2 * radiusCP, 2 * radiusCP);
        
        int degrees = 0;
        for (int i = 0; i < 5; i++) { // print 5 strings at different angles
            g2d.rotate(Math.toRadians(degrees), cx, cy);
            // paint one string "Zero" at a certain angle
            int charRadius = 100;
            for (int j = 0; j < aStr.length(); j++) {
                g2d.drawString(aStr.substring(j, j + 1), cx, (cy - charRadius));
                charRadius -= 25;
            }
            degrees = 20;
        }
    }
}
