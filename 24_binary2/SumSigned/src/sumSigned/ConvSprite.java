/*
 * ConvSprite.java - A class that converts two integers in decimal to
 * binary signed notation and renders calculations dynamically.
 * it looks like a sprite in a game so that name it as ConvSprite.
 */
package sumSigned;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author cxu
 */
public class ConvSprite extends AbsSprite {

    int inInt, inBase, remainder, loop = 1;
    final int ARYLEN = 8;
    final int lineH = 20, gapH = 2, gapW = 5, intW = 60;
    final int topMargin = 250, leftMargin = 100, middleGap = 150;
    int x, y, xLoop2;
    private int inIntOne, inIntTwo;
    private int countOne, countTwo;
    int[] quotientOne = new int[ARYLEN];
    int[] quotientTwo = new int[ARYLEN];
    int[] remainderOne = new int[ARYLEN];
    int[] remainderTwo = new int[ARYLEN];
    String baseS = "" + inBase;
    Font theF;
    JTextField tIntOne;
    JTextField tIntTwo;
    JTextField tSignedOne;
    JTextField tSignedTwo;
    JTextField tSum;
    JLabel lStatus;
    JButton bSum;
    private boolean oneDone, twoDone;
    private PropertyChangeSupport convDone;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ConvSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        convDone = new PropertyChangeSupport(this);
        theF = new Font("Monospaced", Font.PLAIN, 18);
        countOne = 0;
        countTwo = 0;
        oneDone = false;
        twoDone = false;
    }

    public void setCoor() {
        x = leftMargin;
        y = topMargin;
    }

    public void setInitData(int inIntOne, int inIntTwo, int inBase) {
        this.inIntOne = inIntOne;
        quotientOne[0] = inIntOne;
        this.inIntTwo = inIntTwo;
        quotientTwo[0] = inIntTwo;
        this.inBase = inBase;

        setActive(true);
    }

    @Override
    public void updateSprite() {
        if (!(this.isActive())) {
            return;
        }
        if (oneDone == false) {
            updateOne();
        } else {
            updateTwo();
        }
    }

    public void updateOne() {
        countOne += 1;
        if (inIntOne > 0) {
            remainder = inIntOne % inBase;
            inIntOne = inIntOne / inBase;
            if (inIntOne <= 0) {
                oneDone = true;
            }
            quotientOne[countOne] = inIntOne;
            remainderOne[countOne - 1] = remainder;
        }
    }

    public void updateTwo() {
        countTwo += 1;
        if (inIntTwo > 0) {
            remainder = inIntTwo % inBase;
            inIntTwo = inIntTwo / inBase;
            if (inIntTwo <= 0) {
                twoDone = true;
            }
            quotientTwo[countTwo] = inIntTwo;
            remainderTwo[countTwo - 1] = remainder;
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (!(this.isVisible())) {
            return;
        }
        g2d.setFont(theF);
        toSigned(g2d);
    }

    private void toSigned(Graphics2D g2d) {
        int loopOne = 1; // to keep the  loop one when plotting loop two
        if (loop == 1) {
            renderConversion(g2d, loop, countOne, quotientOne, remainderOne);
            if (oneDone) {
                loop += 1;
            }
        } else if (loop == 2) {
            // after finishing the paint for the first integer, the
            // rendering needs to be kept on the screen and then move to 
            // paint the second integer.
            renderConversion(g2d, loopOne, countOne, quotientOne, remainderOne);
            renderConversion(g2d, loop, countTwo, quotientTwo, remainderTwo);
            if (twoDone) {
                //it cannot be terminated; otherwise all painting is lost
                this.setActive(false); // stop updateSprite but open paint
                convDone.firePropertyChange("convDone", false, true);
            }
        }
    }

    // a method shared by two plottings
    public void renderConversion(Graphics2D g2d, int loop, int count,
            int[] quotient, int[] remainder) {
        setCoor();
        int localC = 0;
        FontMetrics fm = g2d.getFontMetrics();
        int baseW = fm.stringWidth("" + inBase);
        int initW = fm.stringWidth("" + quotient[0]);
        int remW = fm.stringWidth("" + remainder[0]);
        int x2 = x + baseW + 2 * gapW + initW + gapW + remW; // the total length
        // the second render should be moved to right distance of middleGap
        if (loop == 2) {
            x += middleGap; // x is the starting point
            x2 += middleGap; // x2 is the end point
            // because the lengths of quotients quoLen is in changing, 
            // (x2 - ...) is easy to find out the drawing starting point 
            // than (x + ...)
        }

        while (localC < count) {
            y += lineH;
            if (quotient[localC] > 0) {
                g2d.drawString("" + remainder[localC], x2 - remW, y - gapH);
            }
            int quoLen = fm.stringWidth("" + quotient[localC]);
            g2d.drawString("" + quotient[localC], x2 - remW - gapW - quoLen,
                    y - gapH);
            g2d.drawLine(x2 - remW - gapW, y, x2 - remW - gapW - initW - gapW, y);
            g2d.drawLine(x2 - remW - gapW - initW - gapW, y,
                    x2 - remW - gapW - initW - gapW, y - lineH);
            g2d.drawString("" + inBase, x, y - gapH);

            localC += 1;
        }
        if (oneDone == true) {
            int quoLen = fm.stringWidth("" + quotient[localC]);
            g2d.drawString("" + quotient[localC], x2 - remW - gapW - quoLen,
                    y - gapH + lineH);
        }
    }

    // a method for debugging use
    private void displayAry(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            System.out.print(ary[i]);
        }
        System.out.println();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        convDone.addPropertyChangeListener(pcl);
    }
}
