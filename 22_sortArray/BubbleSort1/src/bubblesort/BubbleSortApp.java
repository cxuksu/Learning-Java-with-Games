/*
 * BubbleSortApp - A project that implements an animated bubble sort.
 */
package bubblesort;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class BubbleSortApp extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BubbleSortApp() {
        setTitle("Animated Bubble Sort");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String args[]) {
        new BubbleSortApp();
    }
}
