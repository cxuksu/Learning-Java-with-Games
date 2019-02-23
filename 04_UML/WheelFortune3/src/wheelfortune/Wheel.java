/*
 * Wheel.java - A class defines the wheel.
 */
package wheelfortune;

/**
 *
 * @author cxu
 */
public class Wheel {

    private String[] wheel;
    private int wheelLen;

    public Wheel() {
        wheelLen = 10;
        wheel = new String[wheelLen];
        initWheel();
    }

    private void initWheel() {
        int ranInt;
        for (int i = 0; i < wheelLen; i++) {
            ranInt = (int) ((Math.random() * 1000) + 100); // [100. 1100)
            if (i == 5) {
                wheel[i] = "Bankrupt";
            } else {
                wheel[i] = Integer.toString(ranInt);
            }
        }
    }
    
    public String wheelTurns() {
        int ranIdx = (int)(Math.random() * 10); // [0, 9]
        return wheel[ranIdx];
    }
}
