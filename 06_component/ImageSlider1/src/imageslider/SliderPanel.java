/*
 * SliderPanel.java - A class defines the canvas panel for the image slider.
 */
package imageslider;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class SliderPanel extends JPanel {
    
    private ShowBoard showBoard;
    
    public SliderPanel() {
        initComponent();
    }
    
    private void initComponent() {
        setLayout(new BorderLayout());
        initShowPanel();
    }
    
    public void initShowPanel() {
        showBoard = new ShowBoard();
        add(showBoard, BorderLayout.CENTER);
    }
}
