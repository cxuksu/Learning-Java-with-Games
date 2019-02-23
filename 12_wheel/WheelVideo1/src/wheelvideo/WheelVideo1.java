/*
 * WheelVideo1.java - The main class of the game WheelVideo1.
 */

package wheelvideo;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class WheelVideo1 extends JFrame {

    public WheelVideo1() {
        setTitle("Wheel Forture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT+Consts.BOARD_H+
                Consts.PLAYER_PANEL_H);
        
        GameCanvas gameCanvas = new GameCanvas();
        add(gameCanvas);
        
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new WheelVideo1();
    }
}
