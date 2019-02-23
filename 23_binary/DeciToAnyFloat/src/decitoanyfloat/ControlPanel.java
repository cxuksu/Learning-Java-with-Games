/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data for the conversion.
 */
package decitoanyfloat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author cxu
 */
public class ControlPanel extends JPanel {

    private JTextField floatTxt;
    private JTextField baseTxt;
    private JTextField targetTxt;
    private JLabel statusLbl;
    private JButton convBtn;
    private JButton resetBtn;
    private JPanel inputP;

    private GameCanvas gameCanvas;
    private RenderSprite renderSprite;

    public ControlPanel() {
        initControlPanel();
    }

    private void initControlPanel() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH, 180));
        setLayout(new BorderLayout());
        JPanel leftP = new JPanel(); // make a left gap
        leftP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(leftP, BorderLayout.WEST);
        JPanel rightP = new JPanel(); // make a right margin
        rightP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(rightP, BorderLayout.EAST);
        inputP = createInputPanel();
        add(inputP, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel inP = new JPanel();
        inP.setLayout(new BorderLayout());

        // the title panel is on NORTH of the input panel
        JPanel titleP = new JPanel();
        titleP.setLayout(new GridLayout(2, 1));
        JLabel titleLbl = new JLabel("Convert positive float in decimal "
                + "to any base", 0);
        titleLbl.setForeground(Color.red);
        JLabel gapLbl = new JLabel(" ", 0);
        titleP.add(titleLbl);
        titleP.add(gapLbl);
        inP.add(titleP, BorderLayout.NORTH);

        // the data panel is on CENTER of the input panel
        JPanel dataP = new JPanel();
        dataP.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel floatLbl = new JLabel("The float in decimal: ");
        floatLbl.setPreferredSize(new Dimension(150, 25));
        c.gridx = 0;
        c.gridy = 0;
        dataP.add(floatLbl, c);
        floatTxt = new JTextField();
        floatTxt.setPreferredSize(new Dimension(230, 25));
        c.gridx = 1;
        c.gridy = 0;
        dataP.add(floatTxt, c);
        JLabel intGap = new JLabel("", 0);
        intGap.setPreferredSize(new Dimension(120, 25));
        c.gridx = 2;
        c.gridy = 0;
        dataP.add(intGap, c);

        JLabel baseLbl = new JLabel("The base of the target: ");
        baseLbl.setPreferredSize(new Dimension(150, 25));
        c.gridx = 0;
        c.gridy = 1;
        dataP.add(baseLbl, c);
        baseTxt = new JTextField();
        baseTxt.setPreferredSize(new Dimension(230, 25));
        c.gridx = 1;
        c.gridy = 1;
        dataP.add(baseTxt, c);
        convBtn = new JButton("Do Conversion");
        convBtn.setPreferredSize(new Dimension(120, 25));
        convBtn.addActionListener(new ConversionListener());
        convBtn.setActionCommand(Consts.CONV + ""); // convert int to be String
        c.gridx = 2;
        c.gridy = 1;
        dataP.add(convBtn, c);

        c.gridx = 0;
        c.gridy = 2;
        dataP.add(intGap, c);
        c.gridx = 1;
        c.gridy = 2;
        dataP.add(intGap, c);
        c.gridx = 2;
        c.gridy = 2;
        dataP.add(intGap, c);

        JLabel targetLbl = new JLabel("The float in the base: ");
        targetLbl.setPreferredSize(new Dimension(150, 25));
        c.gridx = 0;
        c.gridy = 3;
        dataP.add(targetLbl, c);
        targetTxt = new JTextField();
        targetTxt.setPreferredSize(new Dimension(230, 25));
        c.gridx = 1;
        c.gridy = 3;
        dataP.add(targetTxt, c);
        resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(120, 25));
        resetBtn.setActionCommand(Consts.RESET + "");
        resetBtn.addActionListener(new ResetListener());
        c.gridx = 2;
        c.gridy = 3;
        dataP.add(resetBtn, c);
        resetBtn.setVisible(false);

        inP.add(dataP, BorderLayout.CENTER);

        // the status panel is on SOUTH of the input panel
        JPanel statusP = new JPanel();
        statusP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 25));
        statusLbl = new JLabel("    ", 0);
        statusP.add(statusLbl);
        inP.add(statusP, BorderLayout.SOUTH);

        return inP;
    }

    // inner class ConversionListener -- A listener that converts a positive 
    // integer in any base to an integer in decimal
    class ConversionListener implements ActionListener {

        int baseInt;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getActionCommand().equals(Consts.CONV + ""))) {
                return;
            }
            statusLbl.setForeground(Color.red);
            String inputFloatStr = floatTxt.getText();
            String baseStr = baseTxt.getText();
            if ((inputFloatStr.equals("") || (baseStr.equals("")))) {
                statusLbl.setText("No input integer or base");
                return;
            }

            // verify input data for possible errors
            int correctValue = verifyInput(inputFloatStr, baseStr);
            switch (correctValue) {
                case 0: // no error, start working
                    statusLbl.setText("");
                    Float inputFloat = Float.parseFloat(inputFloatStr);
                    baseInt = Integer.parseInt(baseStr);
                    String sOut = deciFloatToAnyBase(inputFloat, baseInt);
                    targetTxt.setText(sOut);

                    // start ploting
                    int dIntPart = getIntPart(inputFloat);
                    float dFracPart = getFracPart(inputFloat);
                    renderSprite.setInitData(dIntPart, dFracPart, baseInt);
                    renderSprite.setActive(true);
                    renderSprite.setVisible(true);
                    break;
                case 1:
                    statusLbl.setText("The base should be "
                            + "in the range of [2, 16]");
                    resetBtn.setVisible(true);
                    break;
                case 2:
                    statusLbl.setText("Input digits should be "
                            + "in the range of [0, F]");
                    resetBtn.setVisible(true);
                    break;
                case 3:
                    statusLbl.setText("Input digits cannot be larger than base. "
                            + "Enter numbers again");
                    resetBtn.setVisible(true);
                    break;
                default:
            }
        }

        private int verifyInput(String floatS, String baseS) {
            int correctVal = 0;
            int theBase = Integer.parseInt(baseS);
            if ((theBase < 2) || (theBase > 16)) {
                correctVal = 1;
                return correctVal;
            }
            for (int i = 0; i < floatS.length(); i++) {
                String theS = floatS.substring(i, i + 1);
                if (!(theS.equals("."))) {
                    int theV = realValue(theS);
                    if (theV > 9) {
                        correctVal = 2;
                        break;
                    }
                }
            }
            return correctVal;
        }

        // getIntPart() -- To get the integer part of a float number
        public int getIntPart(float dValue) {
            Float theF = dValue;
            String theFS = theF.toString();
            int dPoint = theFS.indexOf(".");
            String intPart = null;
            if (dPoint != -1) {
                intPart = theFS.substring(0, dPoint);
            }
            int dIntPart = Integer.parseInt(intPart);
            return dIntPart;
        }

        // getFracPart() -- To get the fraction part of a double number.
        public float getFracPart(float dValue) {
            Float theF = dValue;
            String theFS = theF.toString();
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

        // deciFloatToAnyBase() -- To convert an double in decimal to any base
        // number system. If base = 2, the method gives binary notation.
        public String deciFloatToAnyBase(float dValue, int base) {
            String theResult = null;
            int dIntPart = getIntPart(dValue);
            float dFracPart = getFracPart(dValue);

            ArrayList<Integer> intPartV;
            ArrayList<Integer> fracPartV;
            if ((dIntPart == 0) && (dFracPart != 0)) {
                fracPartV = convertDFrac(dFracPart, base);
                theResult = arraylistToString("", fracPartV, 1);
            } else if ((dIntPart != 0) && (dFracPart == 0)) {
                intPartV = convertDInt(dIntPart, base);
                theResult = arraylistToString("", intPartV, 0);
            } else if ((dIntPart != 0) && (dFracPart != 0)) {
                String intString;
                intPartV = convertDInt(dIntPart, base);
                intString = arraylistToString("", intPartV, 0);

                fracPartV = convertDFrac(dFracPart, base);
                theResult = arraylistToString(intString, fracPartV, 1);
            }
            return theResult;
        }

        // convertDFrac() -- To convert a fraction number to any base. The
        // result stored in the arrayList is in the right order.
        public ArrayList<Integer> convertDFrac(float dFrac, int base) {
            ArrayList<Integer> bFracPart = new ArrayList<Integer>();
            float theV = dFrac;
            while ((theV > 0.0) && (bFracPart.size() < Consts.FRAC_LENGTH)) {
                theV = theV * base;
                bFracPart.add(getIntPart(theV));
                theV = getFracPart(theV);
            }
            return bFracPart;
        }

        // arrayListToString() -- To convert an arrayList to a string.
        public String arraylistToString(String inS, ArrayList<Integer> aV,
                int pointStatus) {
            String theS = inS;
            switch (pointStatus) {
                case 0:  // no fractional part
                    for (int i = aV.size() - 1; i >= 0; i--) {
                        theS += switchChar(((aV.get(i)))) + " ";
                    }
                    break;
                case 1:  // either only fraction part OR (intPart + fracPart)
                    if (inS.equals("")) {
                        theS = theS + "0 ";
                    }
                    theS = theS + ". ";
                    for (int i = 0; i < aV.size(); i++) {
                        theS += switchChar(((aV.get(i)))) + " ";
                    }
                    break;
            }
            return theS.trim();
        }

        // switchChar() -- To switch characters
        public char switchChar(int value) {
            char ch;

            switch (value) {
                case 10:
                    ch = 'A';
                    break;
                case 11:
                    ch = 'B';
                    break;
                case 12:
                    ch = 'C';
                    break;
                case 13:
                    ch = 'D';
                    break;
                case 14:
                    ch = 'E';
                    break;
                case 15:
                    ch = 'F';
                    break;
                default:
                    ch = (char) (value + 48);
                    break;
            }
            return ch;
        }

        public String aryToString(char[] ary) {
            String theS = "";
            for (int i = 0; i < ary.length; i++) {
                theS += (ary[i] + " ");
            }
            return theS;
        }

        // deciIntToAnyBase() -- To convert an integer in decimal to
        // any base number system.  If base = 2, the method gives
        // binary notation.
        public char[] deciIntToAnyBase(int dValue, int base) {
            ArrayList<Integer> theReverse;
            theReverse = convertDInt(dValue, base);
            char[] theBase = new char[theReverse.size()];
            loadArrayListToAry(theReverse, theBase, 0, theReverse.size());
            return theBase;
        }

        // convertDInt() -- To convert a decimal integer to any base
        // and store the result into an arrayList, which contains
        // the reversed order of the result.  The correct order of
        // result should be made by calling loadArrayListToAry().
        public ArrayList<Integer> convertDInt(int dValue, int base) {
            ArrayList<Integer> revValue = new ArrayList<Integer>();
            int theV = dValue;

            while (theV > 0) {
                Integer theI = theV % base;
                revValue.add(theI);
                theV = theV / base;
            }
            return revValue;
        }

        // loadArrayListToAry() -- To revers the reversed binary
        // (or any base) result stored in the arrayList to the array.
        public void loadArrayListToAry(ArrayList<Integer> theV, char[] theA,
                int start, int len) {
            for (int i = 0; i < len; i++) {
                switch (((theV.get(i)))) {
                    case 10:
                        theA[start + len - i - 1] = 'A';
                        break;
                    case 11:
                        theA[start + len - i - 1] = 'B';
                        break;
                    case 12:
                        theA[start + len - i - 1] = 'C';
                        break;
                    case 13:
                        theA[start + len - i - 1] = 'D';
                        break;
                    case 14:
                        theA[start + len - i - 1] = 'E';
                        break;
                    case 15:
                        theA[start + len - i - 1] = 'F';
                        break;
                    default:
                        theA[start + len - i - 1]
                                = (char) ((((theV.get(i)))) + 48);
                        break;
                }
            }
        }

        private int realValue(String str) {
            int value;
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
                default:
                    value = 20; // an arbitrary value
            }
            if (str.charAt(0) <= '9') {
                value = Integer.parseInt(str);
            }
            return value;
        }
    }

    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getActionCommand().equals(Consts.RESET + ""))) {
                return;
            }
            if ((JButton) evt.getSource() == resetBtn) {
                convBtn.setVisible(true);
                resetBtn.setVisible(false);
                floatTxt.requestFocus();
                floatTxt.setText("");
                baseTxt.setText("");
                targetTxt.setText("");
                gameCanvas.stopGame(); // defined in AbsGameCanvas.java
                gameCanvas.renewGame();
            }
        }
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }

    public void setRenderSprite(RenderSprite renderSprite) {
        this.renderSprite = renderSprite;
    }

    public JButton getConvBtn() {
        return convBtn;
    }

    
    public JButton getResetBtn() {
        return resetBtn;
    }
}
