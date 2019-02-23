/*
 * MinuteHand.java - A class defines the minute hand of an analog clock.
 */

package analogclock;

import java.awt.Color;

/**
 *
 * @author cxu
 */
public class MinuteHand extends ClockHand {

    public MinuteHand(int radiusClock) {
        super(radiusClock);
        setHandPercent(0.85);
        setTurnAngle(18);
        setHandColor(Color.BLACK);
        setAnglePerSec(Consts.MINUTE_ANGLE);
        initHand();
    }
}
