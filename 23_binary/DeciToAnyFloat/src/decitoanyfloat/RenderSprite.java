/*
 * RenderSprite.java - A class that renders calculations dynamically.
 * it functions like a sprite so that name it as RenderSprite.
 */
package decitoanyfloat;

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

    private int inInt, inBase, remainder, countInt, countFloat;
    private final int lineH = 20, gapH = 2, gapW = 5;
    private final int topMargin = 180, leftMargin = 100, middleGap = 150;
    private int x, y, xFloat;
    private boolean intDone = false, fracDone = false;
    private final int ARYLEN = 15;
    private int[] quotients = new int[ARYLEN];
    private int[] remainders = new int[ARYLEN];
    private float[] wholeFloat = new float[ARYLEN];
    private float[] fractions = new float[ARYLEN];
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
        xFloat = x + middleGap;
        countInt = 0;
        countFloat = 0;
        theF = new Font("Monospaced", Font.PLAIN, 18);
        convDone = new PropertyChangeSupport(this);
    }

    public void setInitData(int inInt, float inFrac, int inBase) {
        this.inInt = inInt;
        this.inBase = inBase;
        quotients[0] = inInt;
        wholeFloat[0] = inFrac;
        fractions[0] = inFrac;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }
        
        g2d.setFont(theF);
        
        FontMetrics fm = g2d.getFontMetrics();
        int digitW = fm.stringWidth("0");
        int baseW = fm.stringWidth("" + inBase);
        int initW = fm.stringWidth("" + quotients[0]);
        int remW = fm.stringWidth("" + remainders[0]);
        int x2 = x + baseW + 2 * gapW + initW + gapW + remW;
        int wholeFloatW = fm.stringWidth("" + wholeFloat[0]);
        int wholeFloatLen = ("" + wholeFloat[0]).length();
        int x3 = xFloat + wholeFloatW;

        y = topMargin;
        int localIntC = 0;
        int quoLen = 0;
        // depends on the value of count to paint every digit calculation
        // count = 1, paint the left most digit calclation expression
        // count = 2, paint the left first and second digits expresions ...
        while (localIntC < countInt) {
            y += lineH;
            if (quotients[localIntC] > 0) {
                g2d.drawString("" + remainders[localIntC], x2 - remW, y - gapH);
            }
            quoLen = fm.stringWidth("" + quotients[localIntC]);
            g2d.drawString("" + quotients[localIntC], x2 - remW - gapW - quoLen,
                    y - gapH);
            g2d.drawLine(x2 - remW - gapW, y, x2 - remW - gapW - initW - gapW, y);
            g2d.drawLine(x2 - remW - gapW - initW - gapW, y,
                    x2 - remW - gapW - initW - gapW, y - lineH);
            g2d.drawString("" + inBase, x, y - gapH);

            localIntC += 1;
        }
        if (intDone == true) {
            g2d.drawString("0", x2 - remW - gapW - quoLen, y + lineH);
        }

        String sVal = "0";
        if ((intDone == true) && wholeFloat[0] > 0.0) {
            y = topMargin;
            int localFloatC = 0;
            while (localFloatC < countFloat) {
                renderValue(g2d, sVal, localFloatC, wholeFloatLen, digitW);
                if (localFloatC < 8) {
                    renderMultiSign(g2d, x3, baseW);
                }
                localFloatC++;
            }
            if (fracDone == true) {
                // paint last expression
                renderValue(g2d, sVal, localFloatC, wholeFloatLen, digitW);
                
                this.setActive(false);
                convDone.firePropertyChange("convDone", false, true);
            }
        }
    }

    public void renderValue(Graphics2D g2d, String sVal, int localFloatC, 
            int wholeFloatLen, int digitW) {
        y += lineH;
        // if missing a "0", append a "0" to the string
        // e.g., 1.7 needs to be 1.70
        sVal = "" + wholeFloat[localFloatC];
        if (sVal.length() < wholeFloatLen) {
            sVal = sVal + "0";
        }
        if (Float.parseFloat(sVal) > 10.0) {
            g2d.drawString(sVal.substring(0, wholeFloatLen + 1),
                    xFloat - digitW, y);
        } else {
            g2d.drawString(sVal.substring(0, wholeFloatLen),
                    xFloat, y);
        }
        // draw a rectangle for indicating the digit in the result
        if (localFloatC >= 1) {
            if (Float.parseFloat(sVal) < 10.0) {
                g2d.drawRect(xFloat, y - digitW - gapH, digitW,
                        digitW + 2 * gapH);
            } else {
                g2d.drawRect(xFloat - digitW, y - digitW - gapH,
                        2 * digitW, digitW + 2 * gapH);
            }
        }
    }

    public void renderMultiSign(Graphics2D g2d, int x3, int baseW) {
        y += lineH;
        g2d.drawString("x", xFloat, y);
        g2d.drawString("" + inBase, x3 - baseW, y);
        y += gapH;
        g2d.drawLine(xFloat - gapW, y, x3 + gapW, y);
    }

    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        if (intDone == false) {
            updateInt();
        } else {
            updateFrac();
        }
    }

    private void updateInt() {
        countInt += 1;
        if (inInt > 0) {
            remainder = inInt % inBase;
            inInt = inInt / inBase;
            if (inInt <= 0) {
                intDone = true;
            }
            quotients[countInt] = inInt;
            remainders[countInt - 1] = remainder;
        }
    }

    public void updateFrac() {
        countFloat += 1;
        float theV = fractions[countFloat - 1];
        if ((theV > 0.0) && (countFloat <= Consts.FRAC_LENGTH)) {
            theV = theV * inBase;
            wholeFloat[countFloat] = theV;
            theV = getFracPart(theV);
            fractions[countFloat] = theV;
        } else {
            countFloat--; // to stop the plot ahead
            fracDone = true;
        }
    }

    public float getFracPart(float dValue) {
        Float theFloat = dValue;
        String theFS = theFloat.toString();
        int dPoint = theFS.indexOf(".");
        String fracPart = null;
        if (dPoint != -1) {
            fracPart = theFS.substring(dPoint + 1, theFS.length());
        }

        int powerN = 0;
        if (fracPart != null) {
            powerN = fracPart.length();
        }
        int dFracPart = Integer.parseInt(fracPart);
        float dFracPartF = (float) (dFracPart * Math.pow(10, -powerN));

        return dFracPartF;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        convDone.addPropertyChangeListener(pcl);
    }
}
