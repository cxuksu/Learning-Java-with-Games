/*
 * SecondHand.java - A class defines the second hand of an analog clock.
 */

package analogclock;

import java.awt.Color;

/**
 *
 * @author cxu
 */
public class SecondHand extends ClockHand {
    
    public SecondHand(int radiusClock) {
        super(radiusClock);
        setHandPercent(0.95);
        setTurnAngle(0);
        setHandColor(Color.RED);
        initHand();
    }
}
