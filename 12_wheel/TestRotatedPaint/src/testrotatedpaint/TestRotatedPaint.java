/*
 * TestRotatedPaint.java - The main class of the project TestRotatedPaint.
 */
package testrotatedpaint;

import javafx.scene.canvas.Canvas;
import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TestRotatedPaint extends JFrame {

    public TestRotatedPaint() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        PaintCanvas tc = new PaintCanvas();
        add(tc);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestRotatedPaint();
    }
}
