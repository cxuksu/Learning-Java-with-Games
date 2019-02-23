/*
 * ClockCv.java -- A class defines a digit clock, which ticks by applying
 * a thread and invokes paintComponent() for taking advantage of the 
 * double buffer technique.
 */
package adigitclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ClockCv extends JPanel implements Runnable {

    private Date theDate;
    private Thread tickThread;

    public ClockCv() {
        start();
    }

    private void start() {
        if (tickThread == null) {
            tickThread = new Thread(this);
            tickThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                updateValue();
                repaint();
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
        }
    }

    public void updateValue() {
        theDate = new Date();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // make a special background color
        g2d.setColor(new Color(0, 255, 100));
        g2d.fillRect(0, 0, 420, 100);
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("TimeRoman", Font.BOLD, 24));
        g2d.drawString(theDate.toString(), 10, 50);
    }
}
