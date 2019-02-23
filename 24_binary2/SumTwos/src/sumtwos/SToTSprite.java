/*
 * SToTsprite.java - A class plays a role of sprite for rendering the
 * conversion from signed-magnitude to 2's complement notation.
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
public class SToTSprite extends AbsSprite {

    private Font theF;
    private int countOne, countTwo, countThree;
    private boolean oneDone, twoDone, flipOne, flipTwo;
    private String signedOne, signedTwo;
    private String onesCompOne, onesCompTwo;
    private final int topMargin = 300, leftMargin = 100, lineH = 20;
    private final int oneMove = 2;
    private int threeY;
    private int x, y;
    private int loop;
    private final int ARYLEN = 8;
    private int[] onesAry = new int[ARYLEN];
    private int[] twosAry = new int[ARYLEN];
    private int[] carryAry = new int[ARYLEN];

    private PropertyChangeSupport sToTDone;
    private boolean overflow;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SToTSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        sToTDone = new PropertyChangeSupport(this);
        theF = new Font("Monospaced", Font.PLAIN, 18);
        countOne = 4; // value digits in signedOne starts from index of 6
        countTwo = 4;
        countThree = 0;
        oneDone = false;
        twoDone = false;
        flipOne = false;
        flipTwo = false;
        onesCompOne = "";
        onesCompTwo = "";
        loop = 1;
        overflow = false;
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
            if (loop == 1) {
                if (!oneDone) {
                    updateOne();
                } else {
                    updateThree();
                }
            } else if (loop == 2) {
                if (!twoDone) {
                    updateTwo();
                } else {
                    updateThree();
                }
            }
        }
    }

    public void updateOne() {
        countOne += oneMove;
    }

    public void updateTwo() {
        countTwo += oneMove;
    }

    public void updateThree() {
        countThree += 1;
        int carryOut = 0;

        int sumBit;
        int currBit = ARYLEN - countThree;
        if (onesAry[0] == 1) { // negative
            if (currBit > 0) {
                if (currBit == 7) {
                    sumBit = onesAry[currBit] + 1;
                } else {
                    sumBit = onesAry[currBit] + carryAry[currBit];
                }
                switch (sumBit) {
                    case 0:
                        twosAry[currBit] = 0;
                        carryAry[currBit - 1] = 0;
                        break;
                    case 1:
                        twosAry[currBit] = 1;
                        carryAry[currBit - 1] = 0;
                        break;
                    case 2:
                        twosAry[currBit] = 0;
                        carryAry[currBit - 1] = 1;
                        break;
                    case 3:
                        twosAry[currBit] = 1;
                        carryAry[currBit - 1] = 1;
                        break;
                }
            } else { // bit 0
                sumBit = onesAry[0] + carryAry[0];
                switch (sumBit) {
                    case 0:
                        twosAry[0] = 0;
                        carryOut = 0;
                        break;
                    case 1:
                        twosAry[0] = 1;
                        carryOut = 0;
                        break;
                    case 2:
                        twosAry[0] = 0;
                        carryOut = 1;
                        break;
                    case 3:
                        twosAry[0] = 1;
                        carryOut = 1;
                        break;
                }
            }
        } else if (onesAry[0] == 0) { // positive
            twosAry[currBit] = onesAry[currBit];
        }
        if (carryAry[0] != carryOut) {
            overflow = true;
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (isVisible()) {
            g2d.setFont(theF);
            paintSignedOnes(g2d);
        }
    }

    public void paintSignedOnes(Graphics2D g2d) { // paint singed and ones comp
        switch (loop) {
            case 1:
                if (!oneDone) {
                    renderTwosComp(g2d, loop, countOne, signedOne, onesCompOne, flipOne);
                } else {
                    renderTwosComp(g2d, 1, countOne, signedOne, onesCompOne, flipOne);
                    renderAddOne(g2d);
                }
                break;
            case 2:
                if (!twoDone) {
                    renderTwosComp(g2d, loop, countTwo, signedTwo, onesCompTwo, flipTwo);
                } else {
                    renderTwosComp(g2d, 2, countTwo, signedTwo, onesCompTwo, flipTwo);
                    renderAddOne(g2d);
                }
                break;
            default:
                break;
        }
    }

    // a method shared by two plottings
    public void renderTwosComp(Graphics2D g2d, int loop, int count,
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
        y += lineH;
        g2d.drawString("One's  ", x, y);
        x += fm.stringWidth("Signed ");
        g2d.drawString("" + signedStr.substring(0, localC), x, y);
        y -= lineH;
        // make onesAry for 2's complement
        onesAry[0] = Integer.parseInt(signedStr.substring(2, 3));

        // rendering the value bits
        int aryIdx = 0;
        while (localC < count) {
            localCNext = localC + oneMove; // get digit at 5 and 6 (" 0")
            g2d.drawString("" + signedStr.substring(localC, localCNext),
                    x + localC * digitW, y);
            y += lineH;
            char origBit = signedStr.charAt(localC + 1);
            char flipBit;
            if (flip) {
                flipBit = makeFlip(origBit);
            } else {
                flipBit = origBit;
            }
            g2d.drawString("" + flipBit, x + (localC + 1) * digitW, y);

            if (aryIdx < 7) {
                aryIdx += 1;
                // store the 1's comp in an array for making 2's comp
                onesAry[aryIdx] = Character.getNumericValue(flipBit);
            }
            localC += oneMove;
            y -= lineH;
        }

        if (loop == 1) {
            if (count >= 19) { // the total lenght is 21
                if (!oneDone) {
                    oneDone = true;
                }
            }
        } else if (loop == 2) {
            if (count >= 19) {
                if (!twoDone) {
                    twoDone = true;
                }
            }
        }
        threeY = y + lineH;
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

    public void renderAddOne(Graphics2D g2d) {
        setCoor();
        int localC = 1;
        y = threeY + lineH;

        FontMetrics fm = g2d.getFontMetrics();
        int digitW = fm.stringWidth("0");
        g2d.drawString("Two's  ", x, y);
        x += fm.stringWidth("Signed ");
        x += 10 * 2 * digitW;
        g2d.drawString("|", x, y);

        int currBit;
        while (localC < countThree) { // when countThree is 2 the while starts
            currBit = ARYLEN - localC; 
            x -= 2 * digitW;
            g2d.drawString("" + twosAry[currBit], x, y);
            localC++;
        }
        if (countThree == 8) {
            x -= 6 * digitW;
            g2d.drawString("| " + twosAry[0] + " | ", x, y);
            if (loop == 1) {
                loop = 2;
                countThree = 0;
            } else if (loop == 2) {
                setActive(false); // stop updateSprite but open paint
                sToTDone.firePropertyChange("sToTDone", false, true);
            }
        }
    }

    // a method for debugging
    private void displayAry(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            System.out.print(ary[i]);
        }
        System.out.println();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        sToTDone.addPropertyChangeListener(pcl);
    }
}
