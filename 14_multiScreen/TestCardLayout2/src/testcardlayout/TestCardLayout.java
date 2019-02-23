/*
 * TestCardLayout.java - The main class
 */
package testcardlayout;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TestCardLayout extends JFrame {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TestCardLayout() {
        setTitle("Test CardLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        ScreenDeck cp = new ScreenDeck();
        add(cp);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new TestCardLayout();
    }
}
