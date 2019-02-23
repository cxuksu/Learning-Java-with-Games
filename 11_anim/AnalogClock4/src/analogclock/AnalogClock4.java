/*
 * AnalogClock4.java - The main class of a project that simulates an analog clock.
 */
package analogclock;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class AnalogClock4 extends JFrame {

    private ClockCv cv;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AnalogClock4() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("An analog clock");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT + Consts.BUTTON_H);

        cv = new ClockCv();
        add(cv, BorderLayout.CENTER);
        AutoSetButton autoSetButton = new AutoSetButton(cv);
        add(autoSetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new AnalogClock4();
    }
}
