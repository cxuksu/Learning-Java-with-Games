/*
 * TestGUIBasis.java -- A frame is a subclass of JFrame.
 */
package testguibasis;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author cxu
 */
public class TestGUIBasis extends JFrame {

    private ACanvas anyCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TestGUIBasis() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Test a GUI basis");
        setSize(500, 400);

        anyCanvas = new ACanvas();
        add(anyCanvas);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestGUIBasis();
    }
}
