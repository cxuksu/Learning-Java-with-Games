/*
 * RenderSprite.java - A class that renders calculations dynamically.
 * it seems a sprite so that name it as RenderSprite.
 */
package decimaltoany;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RenderSprite extends AbsSprite {

    private int inInt, inBase, remainder, count;
    private final int lineH = 20, gapH = 2, gapW = 5;
    private final int topMargin = 180, leftMargin = 100;
    private int x, y; // the coordinates for rendering
    private Font theF;
    private final int ARYLEN = 15;
    private int[] quotients = new int[ARYLEN];
    private int[] remainders = new int[ARYLEN];
    private PropertyChangeSupport convDone;
    private boolean stopCalc = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public RenderSprite() {
        initSprite();
    }
    
    @Override
    public void initSprite() {
        x = leftMargin;
        y = topMargin;
        count = 0;
        theF = new Font("Monospaced", Font.PLAIN, 18);
        convDone = new PropertyChangeSupport(this);
    }

    public void setInitData(int inputInt, int inBase) {
        this.inInt = inputInt;
        this.inBase = inBase;
        quotients[count] = inInt; // for plotting the first value
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }
        
        g2d.setFont(theF);

        FontMetrics fm = g2d.getFontMetrics();
        int baseW = fm.stringWidth("" + inBase);
        int initW = fm.stringWidth("" + quotients[0]);
        int remW = fm.stringWidth("" + remainders[0]);
        int x2 = x + baseW + 2 * gapW + initW + gapW + remW;

        y = topMargin; // reset the starting y position
        int localC = 0;
        int quoLen = 0;
        // depends on the value of count to paint every digit calculation
        // count = 1, paint the left most digit calclation expression
        // count = 2, paint the left first and second digits expresions ...
        while (localC < count) {
            y += lineH;
            if (quotients[localC] > 0) {
                g2d.drawString("" + remainders[localC], x2 - remW, y - gapH);
            }
            quoLen = fm.stringWidth("" + quotients[localC]);
            g2d.drawString("" + quotients[localC], x2 - remW - gapW - quoLen,
                    y - gapH);
            g2d.drawLine(x2 - remW - gapW, y, x2 - remW - gapW - initW - gapW, y);
            g2d.drawLine(x2 - remW - gapW - initW - gapW, y,
                    x2 - remW - gapW - initW - gapW, y - lineH);
            g2d.drawString("" + inBase, x, y - gapH);

            localC += 1;
        }
        if (stopCalc == true) {
            // paint the last quotient
            g2d.drawString("0", x2 - remW - gapW - quoLen, y + lineH);
            
            this.setActive(false);
            convDone.firePropertyChange("convDone", false, true);
        }
    }

    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        count += 1;
        if (inInt > 0) {
            remainder = inInt % inBase;
            inInt = inInt / inBase;
            if (inInt <= 0) {
                stopCalc = true;
            }
            quotients[count] = inInt;
            remainders[count - 1] = remainder;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        convDone.addPropertyChangeListener(pcl);
    }
}
