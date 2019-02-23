/*
 * SumSprite.java - A class that performs the summation on two binary 
 * integers in 2's complement.
 */
package sumtwos;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class SumSprite extends AbsSprite {

    private int x, y;
    private int topMargin = 320, leftMargin = 100;
    private final int lineH = 20, gapH = 2, gapW = 5;
    private final int ARYLEN = 8;
    private int[] tempSum = new int[ARYLEN];
    private int[] carryBit = new int[ARYLEN];
    private int sumBit;
    private int carryOut;
    private String twosCompOneStr, twosCompTwoStr;
    private int[] twosCompOneAry, twosCompTwoAry;
    private int countOne;
    private Font theF;
    private boolean oneDone;
    private PropertyChangeSupport sumDone;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SumSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        sumDone = new PropertyChangeSupport(this);
        countOne = 0;
        oneDone = false;
        theF = new Font("Monospaced", Font.PLAIN, 18);
        for (int i = 0; i < ARYLEN; i++) {
            tempSum[i] = 0;
            carryBit[i] = 0;
        }
    }

    public void setCoor() {
        x = leftMargin;
        y = topMargin;
    }

    public void initSummationData(String twosOneStr, String twosTwoStr,
            int[] twosOneAry, int[] twosTwoAry) {
        this.twosCompOneStr = twosOneStr;
        this.twosCompTwoStr = twosTwoStr;
        this.twosCompOneAry = twosOneAry;
        this.twosCompTwoAry = twosTwoAry;
    }

    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        if (!oneDone) {
            updateAddition();
        }
    }

    private void updateAddition() {
        countOne += 1;
        int i = ARYLEN - countOne;
        if (i > 0) {
            sumBit = twosCompOneAry[i] + twosCompTwoAry[i] + carryBit[i];
            switch (sumBit) {
                case 0:
                    tempSum[i] = 0;
                    carryBit[i - 1] = 0;
                    break;
                case 1:
                    tempSum[i] = 1;
                    carryBit[i - 1] = 0;
                    break;
                case 2:
                    tempSum[i] = 0;
                    carryBit[i - 1] = 1;
                    break;
                case 3:
                    tempSum[i] = 1;
                    carryBit[i - 1] = 1;
                    break;
            }
        } else { // i == 0, the sign bit
            sumBit = twosCompOneAry[0] + twosCompTwoAry[0] + carryBit[0];
            switch (sumBit) {
                case 0:
                    tempSum[0] = 0;
                    carryOut = 0;
                    break;
                case 1:
                    tempSum[0] = 1;
                    carryOut = 0;
                    break;
                case 2:
                    tempSum[0] = 0;
                    carryOut = 1;
                    break;
                case 3:
                    tempSum[0] = 1;
                    carryOut = 1;
                    break;
            }
            oneDone = true;
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }
        g2d.setFont(theF);
        paintAddition(g2d);
    }

    private void paintAddition(Graphics2D g2d) {
        renderAddition(g2d, countOne);
        if (oneDone) {
            this.setActive(false); // keep setVisible(true) for painting
            sumDone.firePropertyChange("sumDone", false, true);
        }
    }

    private void renderAddition(Graphics2D g2d, int count) {
        setCoor();
        int localC = 1;

        FontMetrics fm = g2d.getFontMetrics();
        int strLen = fm.stringWidth(twosCompOneStr);
        int digitW = fm.stringWidth("F");
        y += lineH;
        int yFirstLine = y;
        g2d.drawString(twosCompOneStr, x, yFirstLine);
        y += lineH;
        g2d.drawString("+", x - 4 * gapW, y);
        g2d.drawString(twosCompTwoStr, x, y);
        y += 4 * gapH;
        g2d.drawLine(x - 5 * gapW, y, x + strLen + gapW, y);
        x += strLen - digitW;
        y += lineH + 2 * gapH;
        g2d.drawString("|", x, y);
        // rendering the summation dynamically
        while (localC < count) {
            int currBit = ARYLEN - localC;
            g2d.drawString("" + tempSum[currBit], x - 2 * localC * digitW, y);
            if ((currBit - 1 > 0) && (carryBit[currBit - 1] != 0)) {
                g2d.drawString("" + carryBit[currBit - 1],
                        x - 2 * localC * digitW - 2 * digitW, yFirstLine - lineH);
            }
            localC += 1;
        }
        if (oneDone) { // currBit == 0
            // plot the sign bit
            x = x - strLen + digitW;
            g2d.drawString("| " + tempSum[0] + " |", x, y);
            // plot the carry-in and the carryOut bits for overflow checking
            x += 2 * digitW;
            y = yFirstLine - lineH;
            g2d.drawString("" + carryBit[0], x, y); // carry-in
            x -= 2 * digitW;
            g2d.drawString("" + carryOut, x, y); // carryOut
            g2d.drawRect(x, y - digitW - 2 * gapH, digitW, digitW + 4 * gapH);
        }
    }

    // A method for debugging
    private void displayAry(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            System.out.print(ary[i]);
        }
        System.out.println();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        sumDone.addPropertyChangeListener(pcl);
    }
}
