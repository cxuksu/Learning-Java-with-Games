/*
 * SumOnesComp - A project that allows the user to input an integer 
 * in decimal and the target base, and then converts the integer to 
 * an integer in the target base.
 */
package sumonescomp;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class SumOnesComp extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SumOnesComp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Summation of two decimal integers in 1's complement notation");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);

        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new SumOnesComp();
    }
}
