/*
 * Tornado.java - The main class for the project Tornado.
 */
package tornado;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class Tornado extends JFrame {

    public Tornado() {
        initFrame();
    }
    
    private void initFrame() {
        setTitle("Tornado");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GameCanvas gameCv = new GameCanvas();
        add(gameCv);
        
        setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new Tornado();
    }
}
