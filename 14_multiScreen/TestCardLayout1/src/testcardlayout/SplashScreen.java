/*
 * SplashScreen.java -- A JPanel for mimicking a screen in a screen "deck".
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
public class SplashScreen extends JPanel {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SplashScreen() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 26));
        String script1
                = "This is the class SplashScreen.java";
        g2d.drawString(script1, 50, 100);
    }
}
