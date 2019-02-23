/*
 * HourHand.java - A class defines the hour-hand for an analog clock.
 */

package analogclock;

import java.awt.Color;

/**
 *
 * @author cxu
 */
public class HourHand extends ClockHand {
    
    public HourHand(int radiusClock) {
        super(radiusClock);
        setHandPercent(0.65);
        setTurnAngle(36); // an arbitrary initial angle
        setHandColor(Color.BLACK);
        setAnglePerSec(Consts.HOUR_ANGLE);
        initHand();
    }
}
