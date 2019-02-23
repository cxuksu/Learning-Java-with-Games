/*
 * CanvasOfGUIs.java - The main class of the project CanvasOfGUIs.
 */
package paintxmascard;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class CanvasOfGUIs extends JFrame {
    private Canvas aCanvas;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CanvasOfGUIs() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("A canvas");
        setSize(500, 400);
        
        aCanvas = new Canvas();
        add(aCanvas);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new CanvasOfGUIs();
    }
}

