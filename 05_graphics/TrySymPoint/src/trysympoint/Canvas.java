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

    private int xmin, xmax;
    private int ymin, ymax;
    private int x, y;
    private int xCenter, yCenter;
    private int radius;

    public Canvas() {
        xCenter = (Consts.MAXX - Consts.MINX) / 2;
        yCenter = (Consts.MAXY - Consts.MINY) / 2;
        radius = 4;
        xmin = radius * 2;
        xmax = Consts.MAXX - (radius * 2);
        ymin = radius * 2;
        ymax = Consts.MAXY - (radius * 2);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;

        g2d.setColor(Color.red);
        x = giveRand(xmin, xmax);
        y = giveRand(ymin, ymax);
        g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g2d.fillOval(x + 2 * (xCenter - x) - radius, y - radius, 
                radius * 2, radius * 2);
        g2d.fillOval(x - radius, y + 2 * (yCenter - y) - radius, 
                radius * 2, radius * 2);
        g2d.fillOval(x + 2 * (xCenter - x) - radius, 
                y + 2 * (yCenter - y) - radius, radius * 2, radius * 2);
    }

    public int giveRand(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }
}
