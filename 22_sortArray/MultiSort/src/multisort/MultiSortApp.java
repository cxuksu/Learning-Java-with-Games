/*
 * MultiSortApp - A project that implements an animated bubble sort.
 */
package multisort;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class MultiSortApp extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MultiSortApp() {
        setTitle("Animated Bubble Sort");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String args[]) {
        new MultiSortApp();
    }
}
