/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data for the conversion.
 */
package sumtwos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

    private JTextField intOneTxt, intTwoTxt;
    private JTextField signedOneTxt, signedTwoTxt;
    private JTextField twosCompOneTxt, twosCompTwoTxt;
    private JTextField sumTwosCompTxt, sumIntTxt;
    private JButton dToSBtn;
    private JButton sToTBtn;
    private JButton sumTwosCompBtn;
    private JButton resetBtn;
    private JLabel statusLbl;
    private JPanel inputP;
    private DToSSprite dToSSprite;
    private SToTSprite sToTSprite;
    private SumSprite sumSprite;

    private GameCanvas gameCanvas;
    private final int ARYLEN = 8;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ControlPanel() {
        initControlPanel();
    }

    public void initControlPanel() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH, 280));
        setLayout(new BorderLayout());
        JPanel leftP = new JPanel(); // make a left margin
        leftP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(leftP, BorderLayout.WEST);
        JPanel rightP = new JPanel(); // make a right margin
        rightP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(rightP, BorderLayout.EAST);
        inputP = createInputPanel();
        add(inputP, BorderLayout.CENTER);

        // initially disable these buttons
        sToTBtn.setEnabled(false);
        sumTwosCompBtn.setEnabled(false);
        resetBtn.setEnabled(false);
    }

    private JPanel createInputPanel() {
        JPanel inP = new JPanel();
        inP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 260));
        inP.setLayout(new BorderLayout());

        // the title panel on NORTH
        JPanel titleP = new JPanel();
        titleP.setLayout(new GridLayout(3, 1));
        JLabel titleLbl = new JLabel("Summation of two decimal integers"
                + " in 2's complement notation", 0);
        JLabel subTitleLbl = new JLabel("using 8-bit only (the absolute "
                + "value cannot be larger than 127)", 0);
        titleLbl.setForeground(Color.red);
        subTitleLbl.setForeground(Color.blue);
        JLabel gapLbl = new JLabel(" ", 0);
        titleP.add(titleLbl);
        titleP.add(subTitleLbl);
        titleP.add(gapLbl);
        inP.add(titleP, BorderLayout.NORTH);

        // the data panel on CENTER of the user panel
        JPanel dataP = new JPanel();
        dataP.setLayout(new GridLayout(8, 2));
        JLabel intOneLbl = new JLabel("The first integer in decimal: ");
        intOneTxt = new JTextField();
        JLabel intTwoLbl = new JLabel("The second integer in decimal: ");
        intTwoTxt = new JTextField();

        JLabel signedOneLbl = new JLabel("The first integer in signed: ");
        signedOneTxt = new JTextField();
        JLabel signedTwoLbl = new JLabel("The second integer in signed: ");
        signedTwoTxt = new JTextField();

        JLabel oneCompOneLbl = new JLabel("The first integer in two's: ");
        twosCompOneTxt = new JTextField();
        JLabel oneCompTwoLbl = new JLabel("The second integer in two's: ");
        twosCompTwoTxt = new JTextField();

        JLabel sumOneCompLbl = new JLabel("The summation in two's: ");
        sumTwosCompTxt = new JTextField();
        JLabel sumIntLbl = new JLabel("The summation in signed: ");
        sumIntTxt = new JTextField();

        //JLabel gap2Lbl = new JLabel(" ", 0);
        dataP.add(intOneLbl);
        dataP.add(intOneTxt);
        dataP.add(intTwoLbl);
        dataP.add(intTwoTxt);
        dataP.add(signedOneLbl);
        dataP.add(signedOneTxt);
        dataP.add(signedTwoLbl);
        dataP.add(signedTwoTxt);
        dataP.add(oneCompOneLbl);
        dataP.add(twosCompOneTxt);
        dataP.add(oneCompTwoLbl);
        dataP.add(twosCompTwoTxt);

        dataP.add(sumOneCompLbl);
        dataP.add(sumTwosCompTxt);
        dataP.add(sumIntLbl);
        dataP.add(sumIntTxt);
        inP.add(dataP, BorderLayout.CENTER);

        // the button panel on EAST of the user panel
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(4, 1));
        dToSBtn = new JButton("Deci to Signed");
        dToSBtn.addActionListener(new DeciToSignedListener());
        sToTBtn = new JButton("Signed to Two's");
        sToTBtn.addActionListener(new SignedToTwosCompListener());
        sumTwosCompBtn = new JButton("sum Two's Comp");
        sumTwosCompBtn.addActionListener(new SummationListener());
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ResetListener());
        btnP.add(dToSBtn);
        btnP.add(sToTBtn);
        btnP.add(sumTwosCompBtn);
        btnP.add(resetBtn);
        inP.add(btnP, BorderLayout.EAST);

        // the status panel on SOUTH of the user panel
        JPanel statusP = new JPanel();
        statusP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 25));
        statusLbl = new JLabel("    ", 0);
        statusLbl.setForeground(Color.red);
        statusP.add(statusLbl);
        inP.add(statusP, BorderLayout.SOUTH);

        return inP;
    }

    // inner class DeciToSignedListener -- A listener that converts an integer in
    // any base to an integer in decimal
    class DeciToSignedListener implements ActionListener {

        int intOne, intTwo;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == dToSBtn)) {
                return;
            }
            dToSBtn.setEnabled(false);

            sumTwosCompTxt.setText("");
            String sIntOne = intOneTxt.getText();
            String sIntTwo = intTwoTxt.getText();
            if (sIntOne.equals("") || (sIntTwo.equals(""))) {
                statusLbl.setText("No input integer or base");
                resetBtn.setEnabled(true);
                return;
            }

            // verify the inputs
            int correctValue = verifyInput(sIntOne, sIntTwo);
            switch (correctValue) {
                case 0:
                    statusLbl.setText("");
                    intOne = Integer.parseInt(sIntOne);
                    intTwo = Integer.parseInt(sIntTwo);
                    String sOutOne = dToSignedStr(intOne);
                    signedOneTxt.setText(sOutOne);
                    String sOutTwo = dToSignedStr(intTwo);
                    signedTwoTxt.setText(sOutTwo);

                    // send data to DToSSprite for rendering
                    dToSSprite.setInitData(Math.abs(intOne),
                            Math.abs(intTwo), 2);
                    dToSSprite.setActive(true);
                    dToSSprite.setVisible(true);
                    break;
                case 1:
                    statusLbl.setText("The digits should be in the range of "
                            + "[0, 9] for a decimal. Reset");
                    resetBtn.setEnabled(true);
                    intOneTxt.requestFocus();
                    break;
                case 2:
                    statusLbl.setText("The integer value should be <= 127. "
                            + "Reset");
                    resetBtn.setEnabled(true);
                    intOneTxt.requestFocus();
                    break;
                default:
            }
        }

        private int verifyInput(String sIntOne, String sIntTwo) {
            int correctVal = 0;
            if (correctVal == 0) {
                for (int i = 0; i < sIntOne.length(); i++) {
                    int theV = realValue(sIntOne.substring(i, i + 1));
                    if (theV > 9) {
                        correctVal = 1;
                        break;
                    }
                }
            }
            if (correctVal == 0) {
                for (int j = 0; j < sIntTwo.length(); j++) {
                    int theV = realValue(sIntTwo.substring(j, j + 1));
                    if (theV > 9) {
                        correctVal = 1;
                        break;
                    }
                }
            }
            if (correctVal == 0) {
                intOne = Math.abs(Integer.parseInt(sIntOne));
                if (intOne > 127) {
                    correctVal = 2;
                }
            }
            if (correctVal == 0) {
                intTwo = Math.abs(Integer.parseInt(sIntTwo));
                if (intTwo > 127) {
                    correctVal = 2;
                }
            }
            return correctVal;
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
            if (str.charAt(0) != '-') {
                if (str.charAt(0) < 'A') {
                    value = Integer.parseInt(str);
                }
            } else {
                value = 0;
            }
            return value;
        }

        // dToSignedStr() -- To express a signed binary
        public String dToSignedStr(int dValue) {
            String outS = "";
            int sign = 0;
            if (dValue < 0) {
                sign = 1;
            }
            int dInt = Math.abs(dValue);
            char[] intPartV;

            if (dInt != 0) {
                intPartV = dToBinary(dInt, 2);

                outS = outS + "| " + sign + " | ";
                outS = outS + aryToString(intPartV);
                outS += "|";
            }
            return outS;
        }

        // dToBinary() -- To convert an integer in decimal to any base
        // number system. If base = 2, the method gives binary notation.
        public char[] dToBinary(int dInt, int base) {
            ArrayList<Integer> theReverse;
            theReverse = convertDInt(dInt, base);
            char[] theIntChar = new char[theReverse.size()];
            loadIntArrayListToAry(theReverse, theIntChar, 0, theReverse.size());
            return theIntChar;
        }

        // convertDInt() -- To convert a decimal integer to any base and store
        // the result into a arraylist, which contains the reversed order of
        // the result. The correct order of result should be made by calling
        // loadIntArraylistToAry().
        public ArrayList<Integer> convertDInt(int dInt, int base) {
            ArrayList<Integer> revValue = new ArrayList<Integer>();
            int theV = dInt;

            while (theV > 0) {
                Integer theI = theV % base;
                revValue.add(theI);
                theV = theV / base;
            }
            // make a 7-bit arrayList (reserve one bit for sign)
            for (int i = revValue.size(); i < 7; i++) {
                revValue.add(0);
            }
            return revValue;
        }

        // loadIntVectorToAry() -- To revers the binary (or any base)
        // in the reversed order stored in the arraylist to the array.
        public void loadIntArrayListToAry(ArrayList<Integer> theV, char[] theA,
                int start, int len) {
            for (int i = 0; i < len; i++) {
                switch (theV.get(i)) {
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
                        theA[start + len - i - 1] = (char) (theV.get(i) + 48);
                        break;
                }
            }
        }

        // aryToString() -- to convert the value in an array to a string 
        public String aryToString(char[] ary) {
            String theS = "";
            for (int i = 0; i < ary.length; i++) {
                theS += (ary[i] + " ");
            }
            return theS;
        }
    }

    class SignedToTwosCompListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == sToTBtn)) {
                return;
            }

            // clear the rendering of the DToSSprite
            gameCanvas.getdToSSprite().setActive(false);
            gameCanvas.getdToSSprite().setVisible(false);
            // get the two signed binary
            String signedOneStr = signedOneTxt.getText();
            String signedTwoStr = signedTwoTxt.getText();

            // check the sign bit. If it is 1 (negative), convert to one's comp
            char signChar = signedOneStr.charAt(2);
            if (signChar == '1') {
                String twosCompOneStr = makeTwosComp(signedOneStr);
                twosCompOneTxt.setText(twosCompOneStr);
            } else {
                twosCompOneTxt.setText(signedOneStr);
            }

            signChar = signedTwoStr.charAt(2);
            if (signChar == '1') {
                String oneCompTwoStr = makeTwosComp(signedTwoStr);
                twosCompTwoTxt.setText(oneCompTwoStr);
            } else {
                twosCompTwoTxt.setText(signedTwoStr);
            }

            // send data to SToTSprite for rendering
            sToTSprite.setInitData(signedOneStr, signedTwoStr);
            sToTSprite.setActive(true);
            sToTSprite.setVisible(true);
        }
    }

    // It performs summation in 1's complement.
    class SummationListener implements ActionListener {

        final int ARYLEN = 8;
        int[] twosCompOneAry = new int[ARYLEN];
        int[] twosCompTwoAry = new int[ARYLEN];
        int[] tempResult = new int[ARYLEN];
        int[] carryBit = new int[ARYLEN];
        int[] sumResult;
        int carryOut = 0;
        boolean overflowFlag = false;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == sumTwosCompBtn)) {
                return;
            }

            // stop the rendering of SToTSprite
            gameCanvas.getsToTSprite().setActive(false);
            gameCanvas.getsToTSprite().setVisible(false);

            // reset overflowFlag
            if (overflowFlag == true) {
                overflowFlag = false;
            }
            // get the binary integers in 1's complement 
            String onesCompOneStr = twosCompOneTxt.getText();
            String onesCompTwoStr = twosCompTwoTxt.getText();

            // load the strings into arrays
            loadTwosCompToAry(onesCompOneStr, onesCompTwoStr);
            sumResult = addTwosComp(twosCompOneAry, twosCompTwoAry);
            // convert the summation result from an array to a String
            String sSum = aryToStr(sumResult);
            sumTwosCompTxt.setText(sSum);
            // convert summation in 2's to decimal
            String signedStr;
            String sign;
            int deciResult;
            if (sumResult[0] == 0) {
                signedStr = sSum;
                deciResult = bToD(sumResult);
                sign = "+";
            } else { // == 1
                signedStr = makeTwosComp(sSum);
                int[] signedAry = strToAry(signedStr);
                deciResult = bToD(signedAry);
                sign = "-";
            }
            sumIntTxt.setText(signedStr + " " + "(" + sign + deciResult + ")");
            // checking overflow
            if (carryBit[0] != carryOut) {
                overflowFlag = true;
                statusLbl.setText("Overflow occurs");
            }

            // start rendering the summation, even overflow occurs
            dToSSprite.setVisible(false);
            sumSprite.initSummationData(onesCompOneStr, onesCompTwoStr,
                    twosCompOneAry, twosCompTwoAry);
            sumSprite.setActive(true);
            sumSprite.setVisible(true);
        }

        private void loadTwosCompToAry(String twosOneStr, String twosTwoStr) {
            int j = 0;
            for (int i = 0; i < twosOneStr.length(); i++) {
                String oneS = twosOneStr.substring(i, i + 1);
                String twoS = twosTwoStr.substring(i, i + 1);

                if ((!(oneS.equals("|"))) && (!(oneS.equals(" ")))) {
                    twosCompOneAry[j] = Integer.parseInt(oneS);
                    twosCompTwoAry[j] = Integer.parseInt(twoS);
                    j++;
                }
            }
        }

        private int[] addTwosComp(int[] twosCompOneAry, int[] twosCompTwoAry) {
            int sumBit;

            for (int i = ARYLEN - 1; i > 0; i--) {
                sumBit = twosCompOneAry[i] + twosCompTwoAry[i] + carryBit[i];
                switch (sumBit) {
                    case 0:
                        tempResult[i] = 0;
                        carryBit[i - 1] = 0;
                        break;
                    case 1:
                        tempResult[i] = 1;
                        carryBit[i - 1] = 0;
                        break;
                    case 2:
                        tempResult[i] = 0;
                        carryBit[i - 1] = 1;
                        break;
                    case 3:
                        tempResult[i] = 1;
                        carryBit[i - 1] = 1;
                        break;
                }
            }
            sumBit = twosCompOneAry[0] + twosCompTwoAry[0] + carryBit[0];
            switch (sumBit) {
                case 0:
                    tempResult[0] = 0;
                    carryOut = 0;
                    break;
                case 1:
                    tempResult[0] = 1;
                    carryOut = 0;
                    break;
                case 2:
                    tempResult[0] = 0;
                    carryOut = 1;
                    break;
                case 3:
                    tempResult[0] = 1;
                    carryOut = 1;
                    break;
            }
            if (carryBit[0] != carryOut) {
                overflowFlag = true;
                statusLbl.setText("Overflow occurs");
            }
            return tempResult;
        }

        public int bToD(int[] signedAry) {
            boolean allZero = true;
            int theD = 0;
            int lastIdx = ARYLEN - 1;
            if (signedAry[0] == 1) { // check the special case of all zeros
                for (int i = lastIdx; i > 0; i--) {
                    if (signedAry[i] == 1) {
                        allZero = false;
                        break;
                    }
                }
            } else { // if signedAry[0] == 0, no such a special case 
                allZero = false;
            }
            
            if (allZero) { // a special case for negative
                theD = (int) Math.pow(2, 7);
            } else {
                for (int i = lastIdx; i > 0; i--) {
                    theD += (signedAry[i] * Math.pow(2, (lastIdx - i)));
                }
            }
            return theD;
        }
    }

    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == resetBtn)) {
                return;
            }
            dToSBtn.setEnabled(true);
            sToTBtn.setEnabled(false);
            sumTwosCompBtn.setEnabled(false);
            resetBtn.setEnabled(false);

            statusLbl.setText("");
            intOneTxt.setText("");
            intOneTxt.requestFocus(true);
            intTwoTxt.setText("");
            signedOneTxt.setText("");
            signedTwoTxt.setText("");
            twosCompOneTxt.setText("");
            twosCompTwoTxt.setText("");
            sumTwosCompTxt.setText("");
            sumIntTxt.setText("");
            gameCanvas.stopGame();
            gameCanvas.renewGame();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String makeTwosComp(String signedStr) {
        String twosStr;
        int[] signedAry;
        int[] twosAry = new int[ARYLEN];
        signedAry = strToAry(signedStr);

        twosAry[0] = signedAry[0];
        for (int i = 1; i < ARYLEN; i++) {
            if (signedAry[i] == 0) {
                twosAry[i] = 1;
            } else {
                twosAry[i] = 0;
            }
        }
        twosAry = addOne(twosAry);
        twosStr = aryToStr(twosAry);

        return twosStr;
    }

    private int[] addOne(int[] twosCompAry) {
        int[] tempResult = new int[ARYLEN];
        int[] carryBit = new int[ARYLEN];
        int sumBit;
        // add bits [7] to [1]
        for (int i = ARYLEN - 1; i > 0; i--) {
            if (i == 7) {
                sumBit = twosCompAry[i] + 1;
            } else {
                sumBit = twosCompAry[i] + carryBit[i];
            }
            switch (sumBit) {
                case 0:
                    tempResult[i] = 0;
                    carryBit[i - 1] = 0;
                    break;
                case 1:
                    tempResult[i] = 1;
                    carryBit[i - 1] = 0;
                    break;
                case 2:
                    tempResult[i] = 0;
                    carryBit[i - 1] = 1;
                    break;
                case 3:
                    tempResult[i] = 1;
                    carryBit[i - 1] = 1;
                    break;
            }
        }
        tempResult[0] = twosCompAry[0];
        return tempResult;
    }

    private int[] strToAry(String sumStr) {
        int[] resAry = new int[ARYLEN];
        int i = sumStr.length();
        int j = ARYLEN - 1;
        while (j >= 0) {
            String oneBit = sumStr.substring(i - 1, i);
            if ((oneBit.equals("0")) || (oneBit.equals("1"))) {
                resAry[j] = Integer.parseInt(oneBit);
                j -= 1;
            } else {
                //i--;
            }
            i--;
        }
        return resAry;
    }

    private String aryToStr(int[] anAry) {
        String sumStr = "| " + anAry[0] + " | ";
        for (int i = 1; i < anAry.length; i++) {
            sumStr += anAry[i] + " ";
        }
        sumStr += "|";
        return sumStr;
    }

    private void displayAry(int[] ary) {
        for (int i = 0; i < ary.length; i++) {
            System.out.print(ary[i]);
        }
        System.out.println();
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        // from gameCanvas to get dToSSprite and sumSprite
        // for avoiding passing both objects
        dToSSprite = this.gameCanvas.getdToSSprite();
        sToTSprite = this.gameCanvas.getsToTSprite();
        sumSprite = this.gameCanvas.getSumSprite();
    }

    public JButton getsToTBtn() {
        return sToTBtn;
    }

    public JButton getSumTwosCompBtn() {
        return sumTwosCompBtn;
    }

    public JButton getdToSBtn() {
        return dToSBtn;
    }

    public JButton getResetBtn() {
        return resetBtn;
    }
}
