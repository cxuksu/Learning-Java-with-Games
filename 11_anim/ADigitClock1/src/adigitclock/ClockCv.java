/*
 * ClockCv.java -- A class defines a digit clock, which cannot tick
 */
package adigitclock;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ClockCv extends JPanel {
    private Date theDate;
    
    public ClockCv() {
        theDate = new Date();
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font("TimeRoman", Font.BOLD, 24));
        g.drawString(theDate.toString(), 10, 50);
    }
}
