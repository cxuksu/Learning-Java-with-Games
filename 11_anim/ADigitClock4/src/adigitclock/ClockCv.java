/*
 * ClockCv.java -- A class defines a digital clock, which ticks by
 * appyling a thread.
 */
package adigitclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
    public void paint(Graphics g){
        // make a special background color
        g.setColor(new Color(50, 255, 50));
        g.fillRect(0, 0, 420, 100);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimeRoman", Font.BOLD, 24));
        g.drawString(theDate.toString(), 10, 50);
    }
}
