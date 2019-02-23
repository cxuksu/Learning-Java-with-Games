/*
 * AnalogClock3.java - The main class of a project that simulates an analog clock.
 */
package analogclock;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class AnalogClock3 extends JFrame {

    private ClockCv cv;

    public AnalogClock3() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("An analog clock");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);

        cv = new ClockCv();
        add(cv);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AnalogClock3();
    }
}
