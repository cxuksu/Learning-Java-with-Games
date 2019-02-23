/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trysympoint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Canvas extends JPanel {

    private int xCenter, yCenter;
    private int radius;
    private int xmin, xmax;
    private int ymin, ymax;
    private int x1, y1;
    private int x2, y2;
    private int x3, y3;
    

    public Canvas() {
        xCenter = (Consts.MAXX - Consts.MINX) / 2;
        yCenter = (Consts.MAXY - Consts.MINY) / 2;
        radius = 5;
        xmin = radius * 2;
        xmax = xCenter - (radius * 2);
        ymin = radius * 2;
        ymax = yCenter - (radius * 2);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.red);
        x1 = giveRand(xmin, xmax);
        y1 = giveRand(ymin, ymax);
        x2 = giveRand(xmin, xmax);
        y2 = giveRand(ymin, ymax);
        x3 = giveRand(xmin, xmax);
        y3 = giveRand(ymin, ymax);
        paintTri(g2d, x1, y1, x2, y2, x3, y3);
        paintTri(g2d, (x1 + 2 * (xCenter - x1)), y1, (x2 + 2 * (xCenter - x2)),
                y2, (x3 + 2 * (xCenter - x3)), y3);
        paintTri(g2d, x1, (y1 + 2 * (yCenter - y1)), x2, 
                (y2 + 2 * (yCenter - y2)), x3, (y3 + 2 * (yCenter - y3)));
        paintTri(g2d, (x1 + 2 * (xCenter - x1)), (y1 + 2 * (yCenter - y1)), 
                (x2 + 2 * (xCenter - x2)), (y2 + 2 * (yCenter - y2)),
                (x3 + 2 * (xCenter - x3)), (y3 + 2 * (yCenter - y3)));
    }

    public int giveRand(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }
    
    public void paintTri(Graphics2D g2d, int x1, int y1, int x2, int y2, 
            int x3, int y3) {
        g2d.fillOval(x1 - radius, y1 - radius, radius * 2, radius * 2);
        g2d.fillOval(x2 - radius, y2 - radius, radius * 2, radius * 2);
        g2d.fillOval(x3 - radius, y3 - radius, radius * 2, radius * 2);
        g2d.drawLine(x1, y1, x2, y2);
        g2d.drawLine(x2, y2, x3, y3);
        g2d.drawLine(x3, y3, x1, y1);
    }
}
