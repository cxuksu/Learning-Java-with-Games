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
public class ClockCv extends JPanel {

    private ClockFace clockFace;
    private MinuteHand minuteHand;
    private SecondHand secondHand;
    // the radius of the clock frame. A global variable for every component
    private int radiusClock;

    public ClockCv() {
        initComponent();
    }

    private void initComponent() {
        initClockFace();
        initMinuteHand();
        initSecondHand();
    }

    public void initClockFace() {
        clockFace = new ClockFace();
        // radiusClock is calculated inside ClockFace.java
        radiusClock = clockFace.getRadiusClock();
    }

    public void initMinuteHand() {
        // radius of the minute hand is based on radiusClock
        minuteHand = new MinuteHand(radiusClock);
    }

    public void initSecondHand() {
        // radius of the second hand is based on radiusClock
        secondHand = new SecondHand(radiusClock);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        clockFace.paintFace(g2d);
        minuteHand.paintHand(g2d);
        secondHand.paintHand(g2d);
    }
}
