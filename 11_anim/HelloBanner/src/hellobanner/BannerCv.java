/*
 * BannerCv.java - A class animates a string object "Hello World!"
 */
package hellobanner;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class BannerCv extends JPanel implements Runnable {

    private String bannerStr;
    private int strWidth;
    private int frameW;
    private int frameH;
    private Thread bannerThread;
    private int x;
    private int dx;
    private Graphics g;
    
    public BannerCv(int frameW, int frameH) {
        bannerStr = "Hello World!";
        // find out the width of the string bannerStr
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        Font font = new Font("TimeRoman", Font.BOLD, 24);
        FontMetrics fm = g.getFontMetrics(font);
        strWidth = fm.stringWidth(bannerStr);
        g.dispose();
        // initialize other variables
        this.frameW = frameW;
        this.frameH = frameH;
        dx = 2;
        x = frameW - dx; // string starting point
        start();
    }

    private void start() {
        if (bannerThread == null) {
            bannerThread = new Thread(this);
            bannerThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                updatePosition();
                repaint();
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updatePosition() {
        x = x - dx;
        if (x == (-strWidth)) { // when string is out of the frame
            x = frameW; // wraps it to the right edge
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("TimeRoman", Font.BOLD, 24));
        g2d.drawString(bannerStr, x, 50);
    }
}
