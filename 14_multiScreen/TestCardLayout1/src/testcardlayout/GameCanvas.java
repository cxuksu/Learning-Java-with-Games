/*
 * GameCanvas.java -- A JPanel as a screen in a screen "deck"
 */
package testcardlayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 26));
        g2d.drawString("This is the class GameCanvas.java", 50, 100);
    }
}
