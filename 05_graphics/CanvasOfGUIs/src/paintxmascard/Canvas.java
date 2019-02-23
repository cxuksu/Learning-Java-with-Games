/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintxmascard;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Canvas extends JPanel {
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(0, 0, Consts.CV_WIDTH, Consts.FIELD_HEIGHT);
        g.drawRect(0+Consts.MARGIN, 0+Consts.MARGIN, 
                Consts.CV_WIDTH-2*Consts.MARGIN, 
                Consts.FIELD_HEIGHT-2*Consts.MARGIN);
    }
}
