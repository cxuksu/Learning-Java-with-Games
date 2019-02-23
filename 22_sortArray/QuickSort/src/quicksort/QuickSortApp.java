/*
 * QuickSortApp - A project that implements an animated bubble sort.
 */
package quicksort;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class QuickSortApp extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public QuickSortApp() {
        setTitle("Animated Quick Sort");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String args[]) {
        new QuickSortApp();
    }
}
