/*
 * PlayerPanel.java - A class that defines the player panel. It is an empty
 * class at this moment.
 */
package blackjack;

import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {
    
    public PlayerPanel() {
        initPlayerPanel();
    }
    
    private void initPlayerPanel() {
        
    }
    
    public void paintPlayerPanel(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.drawRect(Consts.MINX, Consts.MINY+300, 
                Consts.CV_WIDTH, Consts.PLAYER_PANEL_H);
    }
}
