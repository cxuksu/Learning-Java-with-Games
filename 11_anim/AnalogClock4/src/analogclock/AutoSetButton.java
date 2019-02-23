/*
 * AutoSetButton.java - A class defines an auto-setting button
 * for automatically sets up the analog clock.
 */
package analogclock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class AutoSetButton extends JPanel {

    private JButton autoSetButton;
    private ClockCv clockCv;

    public AutoSetButton(ClockCv clockCv) {
        this.clockCv = clockCv;
        autoSetButton = new JButton("Auto-setting");
        add(autoSetButton);
        autoSetButton.addActionListener(new AutoSetListener());
    }

    class AutoSetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == autoSetButton) {
                // get the supported ids for GMT-05:00 (Eastern Standard Time)
                String[] ids = TimeZone.getAvailableIDs(-5 * 60 * 60 * 1000);
                // if no ids were returned, something is wrong. get out.
                if (ids.length == 0) {
                    System.exit(0);
                }

                // create a Eastern Standard Time time zone
                SimpleTimeZone est = new SimpleTimeZone(
                        -5 * 60 * 60 * 1000, ids[0]);
                // set up rules for Daylight Saving Time
                est.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY,
                        2 * 60 * 60 * 1000);
                est.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY,
                        2 * 60 * 60 * 1000);

                // create a GregorianCalendar with the Eastern Daylight time
                // zone and the current date and time
                GregorianCalendar gcalendar = new GregorianCalendar(est);
                int hour = gcalendar.get(Calendar.HOUR);
                int minute = gcalendar.get(Calendar.MINUTE);
                int second = gcalendar.get(Calendar.SECOND);

                int secondDegree = second * Consts.TICK_ANGLE;
                // minute degree should include the second's angle
                double minuteDegree = minute * 360 / 60
                        + secondDegree * 1.0 / 60; // convert to double
                // hour degree should include the minute's angle
                double hourDegree = hour * 360 / 12
                        + minuteDegree * 1.0 / 60;

                clockCv.getHourHand().setTurnAngle(hourDegree);
                clockCv.getMinuteHand().setTurnAngle(minuteDegree);
                clockCv.getSecondHand().setTurnAngle(secondDegree);
            }
        }
    }
}
