/*
 * ADigitClock -- A project that implements a digit clock but it doesn't tick
 */
package adigitclock;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class ADigitClock extends JFrame {

    public ADigitClock() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("A digital clock");
        setSize(420, 100);

        ClockCv cv = new ClockCv();
        add(cv);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ADigitClock();
    }
}
