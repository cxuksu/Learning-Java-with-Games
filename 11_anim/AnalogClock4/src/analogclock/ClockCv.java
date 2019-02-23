/*
 * ClockCv.java -- A class controls an analog clock.
 */
package analogclock;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ClockCv extends JPanel implements Runnable {

    private ClockFace clockFace;
    private HourHand hourHand;
    private MinuteHand minuteHand;
    private SecondHand secondHand;
    // the radius of the clock frame. A global variable for every component
    private int radiusClock;
    private Thread animThread;

    public ClockCv() {
        initComponent();
        start();
    }

    private void initComponent() {
        initClockFace();
        initHourHand();
        initMinuteHand();
        initSecondHand();
    }

    public void initClockFace() {
        clockFace = new ClockFace();
        // radiusClock is calculated inside ClockFace.java
        radiusClock = clockFace.getRadiusClock();
    }

    public void initHourHand() {
        // radius of the hour hand is based on radiusClock
        hourHand = new HourHand(radiusClock);
    }

    public void initMinuteHand() {
        // radius of the minute hand is based on radiusClock
        minuteHand = new MinuteHand(radiusClock);
    }

    public void initSecondHand() {
        // radius of the second hand is based on radiusClock
        secondHand = new SecondHand(radiusClock);
    }

    private void start() {
        if (animThread == null) {
            animThread = new Thread(this);
            animThread.start();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                updateComponent();
                repaint();
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {

        }
    }

    public void updateComponent() {
        hourHand.updateHand();
        minuteHand.updateHand();
        secondHand.updateHand();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        clockFace.paintFace(g2d);
        hourHand.paintHand(g2d);
        minuteHand.paintHand(g2d);
        secondHand.paintHand(g2d);
    }

    public HourHand getHourHand() {
        return hourHand;
    }

    public MinuteHand getMinuteHand() {
        return minuteHand;
    }

    public SecondHand getSecondHand() {
        return secondHand;
    }
}
