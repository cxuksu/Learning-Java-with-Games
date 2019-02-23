/*
 * GameCanvas.java -- A class defines the game canvas.
 */
package testcardlayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

    private CardLayout cardLayout;
    private ScreenDeck parent;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        setLayout(new BorderLayout());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 26));
        g2d.drawString("This is the class GameCanvas.java", 50, 100);
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public void setParent(ScreenDeck parent) {
        this.parent = parent;
    }
}
