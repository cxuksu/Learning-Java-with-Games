/*
 * SumSprite.java - A class that performs the summation on two binary 
 * integers in signed. It has to look at the sign bit for determining
 * add them or sub them. Then, it renders the summation process 
 * dynamically. It looks like a sprite in a game so name it as SumSprite.
 */
package sumSigned;

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

    private int command;
    private final int ADDB = 1, SUBB = 2;
    private int x, y;
    private int topMargin = 280, leftMargin = 100;
    private final int lineH = 20, gapH = 2, gapW = 5;
    private final int FIRSTL = 1, SECONDL = 2; // which one is larger?
    private int whichLarger = FIRSTL;
    private final int ARYLEN = 8;
    private int[] tempSum = new int[ARYLEN];
    private int[] carryBit = new int[ARYLEN];
    private int[] tempDiff = new int[ARYLEN];
    private int[] borrowBit = new int[ARYLEN];
    private int[] bigger = new int[ARYLEN];
    private int[] smaller = new int[ARYLEN];
    private int sumBit;
    private String sSignedOne, sSignedTwo; // string with "|" and space
    private int[] bIntOne, bIntTwo;
    private int count;
    private Font theF;
    private PropertyChangeSupport sumDone;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SumSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        sumDone = new PropertyChangeSupport(this);
        count = 0;
        theF = new Font("Monospaced", Font.PLAIN, 18);
        for (int i = 0; i < ARYLEN; i++) {
            tempSum[i] = 0;
            carryBit[i] = 0;
            tempDiff[i] = 0;
            borrowBit[i] = 0;
        }
    }

    public void setCoor() {
        x = leftMargin;
        y = topMargin;
    }

    public void initSummationData(String sSignedOne, String sSignedTwo,
            int[] bIntOne, int[] bIntTwo) {
        this.sSignedOne = sSignedOne;
        this.sSignedTwo = sSignedTwo;
        this.bIntOne = bIntOne;
        this.bIntTwo = bIntTwo;
    }

    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        switch (command) {
            case ADDB:
                updateAddBinary();
                break;
            case SUBB:
                updateSubBinary();
                break;
        }
    }

    private void updateAddBinary() {
        count += 1;
        int i = bIntOne.length - count;
        if (i > 0) {
            sumBit = bIntOne[i] + bIntTwo[i] + carryBit[i];
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
        } else {
            tempSum[0] = bIntOne[0];
        }
    }

    private void updateSubBinary() {
        // the first step (when count == 0) should determine which one is 
        // bigger and place the bigger value on the top
        if (count == 0) {
            String sIntOne = "", sIntTwo = "";
            // ignore the sign bit
            for (int i = 1; i < bIntOne.length; i++) {
                sIntOne += bIntOne[i];
                sIntTwo += bIntTwo[i];
            }
            // determine which value is larger than the other
            // and then load the larger one to bigger[]
            if (Integer.parseInt(sIntOne) > Integer.parseInt(sIntTwo)) {
                whichLarger = FIRSTL;
                for (int i = 0; i < ARYLEN; i++) {
                    bigger[i] = bIntOne[i];
                    smaller[i] = bIntTwo[i];
                }
            } else {
                whichLarger = SECONDL;
                for (int i = 0; i < ARYLEN; i++) {
                    bigger[i] = bIntTwo[i];
                    smaller[i] = bIntOne[i];
                }
            }
        }
        
        // Then, computing bit-by-bit 
        count += 1;
        int i = bIntOne.length - count;
        int diffBit;
        if (i > 0) {
            if ((bigger[i] + borrowBit[i]) >= smaller[i]) {
                diffBit = (bigger[i] + borrowBit[i]) - smaller[i];
            } else {
                // the ith position borrows 1 from previous position
                // borrowBit[i] += 2 and bigger[i - 1] -= 1
                borrowBit[i] += 2;
                bigger[i - 1] -= 1;
                // if bigger[i - 1] < 0 (== -1), its notation is still 0
                // but the borrowBit[i - 1] should be 1 (not 2)
                if (bigger[i - 1] < 0) {
                    bigger[i - 1] = 0;
                    borrowBit[i - 1] -= 1;
                }
                diffBit = borrowBit[i] + bigger[i] - smaller[i];
                // converting the format of bigger[] to sSignedOne for painting
                sSignedOne = aryToPaintStr(bigger);
            }
            tempDiff[i] = diffBit;
        } else {
            tempDiff[0] = bigger[0];
        }
    }

    public String aryToPaintStr(int[] ary) {
        String str = "| ";

        str += ary[0] + " " + "| ";
        for (int i = 1; i < ARYLEN; i++) {
            str += ary[i] + " ";
        }
        str += "|";
        return str;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }
        g2d.setFont(theF);
        switch (command) {
            case ADDB:
                sumAdd(g2d);
                break;
            case SUBB:
                sumSub(g2d);
                break;
        }
    }

    private void sumAdd(Graphics2D g2d) {
        setCoor();
        int localC = 1;
        g2d.setFont(theF);
        FontMetrics fm = g2d.getFontMetrics();
        int strLen = fm.stringWidth(sSignedOne);
        int digitW = fm.stringWidth("F");
        y += lineH;
        int yFirstLine = y;
        g2d.drawString(sSignedOne, x, yFirstLine);
        y += lineH;
        g2d.drawString("+", x - 4 * gapW, y);
        g2d.drawString(sSignedTwo, x, y);
        g2d.drawLine(x - 5 * gapW, y + 4 * gapH, x + strLen + gapW, y + 4 * gapH);
        x = x + strLen - digitW;
        y = y + 4 * gapH + lineH;
        g2d.drawString("|", x, y);

        int currBit;
        while (localC < count) {
            currBit = ARYLEN - localC;
            g2d.drawString("" + tempSum[currBit],
                    x - 2 * localC * digitW, y);
            if (carryBit[currBit - 1] != 0) {
                g2d.drawString("" + carryBit[currBit - 1],
                        x - 2 * localC * digitW - 2 * digitW, yFirstLine - lineH);
                if (currBit - 1 == 0) {
                    g2d.drawRect(x - 2 * localC * digitW - 2 * digitW,
                            yFirstLine - lineH - digitW - gapH, digitW,
                            digitW + 2 * gapH);
                }
            }
            localC += 1;
        }
        if (count == ARYLEN) {
            // plot the bit [0]
            g2d.drawString("| " + tempSum[ARYLEN - count] + " |",
                    x - strLen + digitW, y);

            this.setActive(false); // keep setVisible(true) for painting
            // it cannot issue terminate; otherwise the inputPanel will
            // be erased
            sumDone.firePropertyChange("sumDone", false, true);
        }
    }

    private void sumSub(Graphics2D g2d) {
        setCoor();
        int localC = 1;
        g2d.setFont(theF);
        FontMetrics fm = g2d.getFontMetrics();
        int strLen = fm.stringWidth(sSignedOne);
        int digitW = fm.stringWidth("F");
        y += lineH;
        int yFirstLine = y;
        if (whichLarger == FIRSTL) {
            g2d.drawString(sSignedOne, x, yFirstLine);
            y += lineH;
            g2d.drawString("-", x - 4 * gapW, y);
            g2d.drawString(sSignedTwo, x, y);
        } else if (whichLarger == SECONDL) {
            g2d.drawString(sSignedTwo, x, yFirstLine);
            y += lineH;
            g2d.drawString("-", x - 4 * gapW, y);
            g2d.drawString(sSignedOne, x, y);
        }
        y += 4 * gapH;
        g2d.drawLine(x - 5 * gapW, y, x + strLen + gapW, y);
        x = x + strLen - digitW;
        y = y + 4 * gapH + lineH;
        g2d.drawString("|", x, y);

        while (localC < count) {
            if (borrowBit[bIntOne.length - localC] > 0) {
                g2d.drawString("" + borrowBit[bIntOne.length - localC],
                        x - 2 * localC * digitW, yFirstLine - lineH);
            }
            g2d.drawString("" + tempDiff[bIntOne.length - localC],
                    x - 2 * localC * digitW, y);

            localC += 1;
        }
        if (count == ARYLEN) {
            // plot the last item
            g2d.drawString("| " + tempDiff[bIntOne.length - count] + " |",
                    x - strLen + digitW, y);

            this.setActive(false);
            // it cannot terminate the game. Otherwise, the inputPanel will
            // be erased
            sumDone.firePropertyChange("sumDone", false, true);
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

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
