/*
 * AnyToDecimal.java - The main class of the game AnyToDecimal.
 */
package anytodecimal;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class AnyToDecimal extends JFrame {
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AnyToDecimal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Convert integer in any base to decimal");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new AnyToDecimal();
    }
}
