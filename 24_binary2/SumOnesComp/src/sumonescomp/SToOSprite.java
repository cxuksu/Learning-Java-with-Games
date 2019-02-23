/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sumonescomp;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class SToOSprite extends AbsSprite {

    private Font theF;
    private int countOne;
    private int countTwo;
    private boolean oneDone, twoDone, flipOne, flipTwo;
    private String signedOne, signedTwo;
    private String onesCompOne, onesCompTwo;
    private final int topMargin = 300, leftMargin = 100, lineH = 20, lineGap = 40;
    private final int oneMove = 2;
    private int x, y;
    private int loop;

    private PropertyChangeSupport sToODone;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SToOSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        sToODone = new PropertyChangeSupport(this);
        theF = new Font("Monospaced", Font.PLAIN, 18);
        countOne = 4; // value digits in signedOne starts from index of 6
        countTwo = 4;
        oneDone = false;
        twoDone = false;
        flipOne = false;
        flipTwo = false;
        onesCompOne = "";
        onesCompTwo = "";
        loop = 1;
    }

    public void setCoor() {
        x = leftMargin;
        y = topMargin;
    }

    public void setInitData(String signedOne, String signedTwo) {
        this.signedOne = signedOne;
        this.signedTwo = signedTwo;
        char signChar = this.signedOne.charAt(2);
        flipOne = signChar == '1';
        if (!oneDone) {
            onesCompOne += signedOne.substring(0, 6);
        }
        signChar = this.signedTwo.charAt(2);
        flipTwo = signChar == '1';
        if (!twoDone) {
            onesCompTwo += signedTwo.substring(0, 6);
        }
        setActive(true);
    }

    @Override
    public void updateSprite() {
        if (isActive()) {
            if (!oneDone) {
                updateOne();
            } else {
                updateTwo();
            }
        }
    }

    public void updateOne() {
        countOne += oneMove;
    }

    public void updateTwo() {
        countTwo += oneMove;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (isVisible()) {
            g2d.setFont(theF);
            toOnesComp(g2d);
        }
    }

    public void toOnesComp(Graphics2D g2d) {
        if (loop == 1) {
            renderOnesComp(g2d, loop, countOne, signedOne, onesCompOne, flipOne);
            if (oneDone) {
                loop += 1;
            }
        } else if (loop == 2) {
            renderOnesComp(g2d, loop, countTwo, signedTwo, onesCompTwo, flipTwo);
            if (twoDone) {
                // no updating but still painting until the user clicks next btn
                setActive(false); // stop updateSprite but open paint
                sToODone.firePropertyChange("sToODone", false, true);
            }
        }
    }

    // a method shared by two plottings
    public void renderOnesComp(Graphics2D g2d, int loop, int count,
            String signedStr, String onesCompStr, boolean flip) {
        setCoor();
        int localC = 5; // get characters at indexes from 0 to 4
        int localCNext;

        FontMetrics fm = g2d.getFontMetrics();
        int digitW = fm.stringWidth("0");

        // rendering the sign bit
        if (loop == 1) {
            g2d.drawString("Signed binary one", x, y);
        } else if (loop == 2) {
            g2d.drawString("Signed binary two", x, y);
        }
        y += lineH;
        g2d.drawString("Signed ", x, y); // start from the origin
        x += fm.stringWidth("Signed "); // skip the width
        g2d.drawString("" + signedStr.substring(0, localC), x, y);
        x -= fm.stringWidth("Signed "); // move back to the origin
        y += lineGap;
        g2d.drawString("One's  ", x, y);
        x += fm.stringWidth("Signed ");
        g2d.drawString("" + signedStr.substring(0, localC), x, y);
        y -= lineGap;

        // rendering the value bits
        while (localC < count) {
            localCNext = localC + oneMove; // get digit at 5 and 6 (" 0")
            g2d.drawString("" + signedStr.substring(localC, localCNext),
                    x + localC * digitW, y);
            y += lineGap;
            char origBit = signedStr.charAt(localC + 1);
            char flipBit;
            if (flip) {
                flipBit = makeFlip(origBit);
            } else {
                flipBit = origBit;
            }
            g2d.drawString("" + flipBit, x + (localC + 1) * digitW, y);

            localC += oneMove;
            y -= lineGap;
        }
        if (count >= 19) { // the total lenght is 21
            if (!oneDone) {
                oneDone = true;
                x = leftMargin; // reset to starting point
                y += lineGap;
            } else if (!twoDone) {
                twoDone = true;
            }
        }
    }

    public char makeFlip(char origBit) {
        char flipBit;
        switch (origBit) {
            case '0':
                flipBit = '1';
                break;
            case '1':
                flipBit = '0';
                break;
            default:
                flipBit = origBit;
                break;
        }
        return flipBit;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        sToODone.addPropertyChangeListener(pcl);
    }
}
