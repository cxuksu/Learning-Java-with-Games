/*
 * RenderSprite.java - A class that renders conversion.
 */
package anytodecimal;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class RenderSprite extends AbsSprite {

    private String intStr;
    private int base, count;
    private final int lineH = 20, gapH = 2, gapW = 5, powerH = 10;
    private final int topMargin = 200, leftMargin = 100;
    private int x, y;
    private int[] values;
    private Font theF;
    private PropertyChangeSupport convDone;

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
        values = new int[15];
        convDone = new PropertyChangeSupport(this);
    }

    public void setInitData(String inputIntStr, int baseInt) {
        this.intStr = inputIntStr;
        this.base = baseInt;
    }

    // update the count: increased by 1 in every loop
    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        if (count < intStr.length()) {
            // store the max values in the array values[]
            count += 1;
            values[count - 1]
                    = (int) (realValue(intStr.substring(count - 1, count))
                    * Math.pow(base, intStr.length() - count));
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }

        g2d.setFont(theF);

        FontMetrics fm = g2d.getFontMetrics();
        int digitW = fm.stringWidth("F");
        int baseW = fm.stringWidth("" + base);
        // paint an expression "2 x 8^2 = 128 +"
        // including equal sign, the length is fixed
        int xToEqualSign = x + digitW + gapW + digitW + gapW + baseW
                + digitW + gapW + digitW;
        // after equal sign, width depends on the max value
        int xToMaxW = 0;

        int localC = 0;
        y = topMargin; // reset the starting y position
        // depends on the value of count to paint an expression for every digit
        // count = 1, paint the left most digit calculation expression
        // count = 2, paint the left first and second digits expressions ...
        while ((localC < count) && (count <= intStr.length())) {
            y += lineH;
            if (localC == 0) { // prepare the width of max value and a "+"
                int maxV = values[localC]; // max value were stored in values[]
                int maxW = fm.stringWidth("" + maxV);
                xToMaxW = xToEqualSign + gapW + maxW + gapW + digitW;
            }
            // paint one expression
            g2d.drawString(intStr.substring(localC, localC + 1), x, y); // digit
            g2d.drawString("x", x + digitW + gapW, y); // multiplication sign
            g2d.drawString("" + base, x + digitW + gapW + digitW + gapW, y); // base
            g2d.drawString("" + (intStr.length() - localC - 1),
                    x + digitW + gapW + digitW + gapW + baseW, y - powerH); // power
            g2d.drawString("=", xToEqualSign - digitW, y); // equal sign
            int valueW = fm.stringWidth("" + values[localC]); // width of max value
            g2d.drawString("" + values[localC],
                    xToMaxW - digitW - gapW - valueW, y); // the max value
            if (localC < (intStr.length() - 1)) {
                g2d.drawString("+", xToMaxW - digitW, y); // "+" sign
            }
            localC += 1;
            if (localC >= intStr.length()) {
                // draw a line
                g2d.drawLine(x, y + 2 * gapH, xToMaxW, y + 2 * gapH);
                // draw the total value under the line
                int totalV = 0;
                for (int k = 0; k < intStr.length(); k++) { // the total
                    totalV += values[k];
                }
                int totalW = fm.stringWidth("" + totalV);
                g2d.drawString("" + totalV, xToMaxW - digitW - gapW - totalW,
                        y + gapH + lineH);
            }
            if (count == intStr.length()) { // stop plotting
                this.setActive(false); // no update, has paint
                convDone.firePropertyChange("convDone", false, true);
            }
        }
    }

    private int realValue(String str) {
        int value = 0;
        switch (str.charAt(0)) {
            case 'A':
            case 'a':
                value = 10;
                break;
            case 'B':
            case 'b':
                value = 11;
                break;
            case 'C':
            case 'c':
                value = 12;
                break;
            case 'D':
            case 'd':
                value = 13;
                break;
            case 'E':
            case 'e':
                value = 14;
                break;
            case 'F':
            case 'f':
                value = 15;
                break;
        }
        if (str.charAt(0) < 'A') {
            value = Integer.parseInt(str);
        }
        return value;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        convDone.addPropertyChangeListener(pcl);
    }

    // for debugging
    public void printValues(int idx) {
        for (int i = 0; i <= idx; i++) {
            System.out.print(values[i]);
        }
        System.out.println();
    }
}
