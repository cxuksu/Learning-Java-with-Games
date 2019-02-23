/*
 * ClockCv.java -- A class defines a digital clock, which now ticks by
 * applying a thread.
 */
package adigitclock;

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
    private Thread animation;

    public ClockCv() {
        start();
    }

    private void start() {
        if (animation == null) {
            animation = new Thread(this);
            animation.start();
        }
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            while (true) {
                theDate = new Date();
                repaint();
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font("TimeRoman", Font.BOLD, 24));
        g.drawString(theDate.toString(), 10, 50);
    }
}
