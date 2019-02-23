/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data for the conversion.
 */
package sumonescomp;

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
    private JTextField onesCompOneTxt, onesCompTwoTxt;
    private JTextField sumOnesCompTxt, sumIntTxt;
    private JButton dToSBtn;
    private JButton sToOBtn;
    private JButton sumOnesCompBtn;
    private JButton resetBtn;
    private JLabel statusLbl;
    private JPanel inputP;
    private DToSSprite dToSSprite;
    private SToOSprite sToOSprite;
    private SumSprite sumSprite;

    private GameCanvas gameCanvas;

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
        sToOBtn.setEnabled(false);
        sumOnesCompBtn.setEnabled(false);
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
                + " in 1's complement notation", 0);
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

        JLabel oneCompOneLbl = new JLabel("The first integer in one's: ");
        onesCompOneTxt = new JTextField();
        JLabel oneCompTwoLbl = new JLabel("The second integer in one's: ");
        onesCompTwoTxt = new JTextField();

        JLabel sumOneCompLbl = new JLabel("The summation in one's: ");
        sumOnesCompTxt = new JTextField();
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
        dataP.add(onesCompOneTxt);
        dataP.add(oneCompTwoLbl);
        dataP.add(onesCompTwoTxt);

        dataP.add(sumOneCompLbl);
        dataP.add(sumOnesCompTxt);
        dataP.add(sumIntLbl);
        dataP.add(sumIntTxt);
        inP.add(dataP, BorderLayout.CENTER);

        // the button panel on EAST of the user panel
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(4, 1));
        dToSBtn = new JButton("Deci to Signed");
        dToSBtn.addActionListener(new DeciToSignedListener());
        sToOBtn = new JButton("Signed to One's");
        sToOBtn.addActionListener(new SignedToOnesCompListener());
        sumOnesCompBtn = new JButton("sum One's Comp");
        sumOnesCompBtn.addActionListener(new SummationListener());
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ResetListener());
        btnP.add(dToSBtn);
        btnP.add(sToOBtn);
        btnP.add(sumOnesCompBtn);
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

            sumOnesCompTxt.setText("");
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

        // display() -- To display the contents of an array.
        public void display(int[] ary) {
            for (int i = 0; i < ary.length; i++) {
                System.out.print(ary[i] + " ");
            }
            System.out.println();
        }
    }

    class SignedToOnesCompListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == sToOBtn)) {
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
                String onesCompOneStr = makeOnesComp(signedOneStr);
                onesCompOneTxt.setText(onesCompOneStr);
            } else {
                onesCompOneTxt.setText(signedOneStr);
            }

            signChar = signedTwoStr.charAt(2);
            if (signChar == '1') {
                String oneCompTwoStr = makeOnesComp(signedTwoStr);
                onesCompTwoTxt.setText(oneCompTwoStr);
            } else {
                onesCompTwoTxt.setText(signedTwoStr);
            }

            // send data to SToOSprite for rendering
            sToOSprite.setInitData(signedOneStr, signedTwoStr);
            sToOSprite.setActive(true);
            sToOSprite.setVisible(true);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String makeOnesComp(String signedStr) {
        String onesComp = "";
        onesComp += signedStr.substring(0, 6); // sign bit
        for (int i = 6; i < signedStr.length(); i++) {
            switch (signedStr.charAt(i)) {
                case '0':
                    onesComp += "1";
                    break;
                case '1':
                    onesComp += "0";
                    break;
                default:
                    onesComp += signedStr.charAt(i) + "";
                    break;
            }
        }
        return onesComp;
    }

    // It performs summation in 1's complement.
    class SummationListener implements ActionListener {

        final int ARYLEN = 8;
        int[] onesCompOneAry = new int[ARYLEN];
        int[] onesCompTwoAry = new int[ARYLEN];
        int[] sumResult;
        boolean overflowFlag = false;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == sumOnesCompBtn)) {
                return;
            }

            // stop the rendering of SToOSprite
            gameCanvas.getsToOSprite().setActive(false);
            gameCanvas.getsToOSprite().setVisible(false);

            // reset overflowFlag
            if (overflowFlag == true) {
                overflowFlag = false;
            }
            // get the binary integers in 1's complement 
            String onesCompOneStr = onesCompOneTxt.getText();
            String onesCompTwoStr = onesCompTwoTxt.getText();

            // load the strings into arrays
            loadOnesCompToAry(onesCompOneStr, onesCompTwoStr);
            sumResult = addOnesComp(onesCompOneAry, onesCompTwoAry);
            // convert the summation result from an array to a String
            String sSum = sumToStr(sumResult);
            sumOnesCompTxt.setText(sSum);
            // convert summation in 1's to decimal
            String signedStr;
            String sign;
            int deciResult;
            if (sumResult[0] == 0) { 
                signedStr = sSum;
                deciResult = bToD(sumResult);
                sign = "+";
            } else { // == 1
                signedStr = makeOnesComp(sSum);
                int[] signedAry = strToAry(signedStr);
                deciResult = bToD(signedAry);
                sign = "-";
            }
            sumIntTxt.setText(signedStr + " " + "(" + sign + deciResult + ")");
            // checking overflow
            if (onesCompOneAry[0] == onesCompTwoAry[0]) {
                if (onesCompOneAry[0] != sumResult[0]) {
                    overflowFlag = true;
                    statusLbl.setText("Overflow occurs");
                }
            }

            // start rendering the summation
            //if (overflowFlag == false) {
            dToSSprite.setVisible(false);
            sumSprite.initSummationData(onesCompOneStr, onesCompTwoStr,
                    onesCompOneAry, onesCompTwoAry);
            sumSprite.setActive(true);
            sumSprite.setVisible(true);
        }

        private void loadOnesCompToAry(String onesOneStr, String onesTwoStr) {
            int j = 0;
            for (int i = 0; i < onesOneStr.length(); i++) {
                String oneS = onesOneStr.substring(i, i + 1);
                String twoS = onesTwoStr.substring(i, i + 1);

                if ((!(oneS.equals("|"))) && (!(oneS.equals(" ")))) {
                    onesCompOneAry[j] = Integer.parseInt(oneS);
                    onesCompTwoAry[j] = Integer.parseInt(twoS);
                    j++;
                }
            }
        }

        private int[] addOnesComp(int[] onesCompOneAry, int[] onesCompTwoAry) {
            int[] tempResult = new int[ARYLEN];
            int[] carryBit = new int[ARYLEN];
            int[] roundOver = new int[ARYLEN];
            int sumBit;
            int signBit;
            // add bits [7] to [1]
            for (int i = onesCompOneAry.length - 1; i > 0; i--) {
                sumBit = onesCompOneAry[i] + onesCompTwoAry[i] + carryBit[i];
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
            // add sign bit
            signBit = onesCompOneAry[0] + onesCompTwoAry[0] + carryBit[0];
            switch (signBit) {
                case 0:
                    tempResult[0] = 0;
                    break;
                case 1:
                    tempResult[0] = 1;
                    break;
                case 2:
                    tempResult[0] = 0;
                    roundOver[7] = 1; // carry round
                    tempResult = addOnesComp(tempResult, roundOver);
                    break;
                case 3:
                    tempResult[0] = 1;
                    roundOver[7] = 1; // carry round
                    tempResult = addOnesComp(tempResult, roundOver);
                    break;
            }
            return tempResult;
        }

        private String sumToStr(int[] sumAry) {
            String sumStr = "| " + sumAry[0] + " | ";
            for (int i = 1; i < sumAry.length; i++) {
                sumStr += sumAry[i] + " ";
            }
            sumStr += "|";
            return sumStr;
        }

        private int[] strToAry(String sumStr) {
            int[] resAry = new int[ARYLEN];
            int i = sumStr.length();
            int j = ARYLEN - 1;
            while (j >= 0) {
                String oneBit = sumStr.substring(i-1, i);
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
        
        public int bToD(int[] signedAry) {
            int theD = 0;
            int lastIdx = signedAry.length - 1;
            for (int i = lastIdx; i > 0; i--) {
                theD += (signedAry[i] * Math.pow(2, (lastIdx - i)));
            }
            return theD;
        }
        
        private void displayAry(int[] ary) {
            for (int i = 0; i < ary.length; i++) {
                System.out.print(ary[i]);
            }
            System.out.println();
        }
    }

    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == resetBtn)) {
                return;
            }
            dToSBtn.setEnabled(true);
            sToOBtn.setEnabled(false);
            sumOnesCompBtn.setEnabled(false);
            resetBtn.setEnabled(false);

            statusLbl.setText("");
            intOneTxt.setText("");
            intOneTxt.requestFocus(true);
            intTwoTxt.setText("");
            signedOneTxt.setText("");
            signedTwoTxt.setText("");
            onesCompOneTxt.setText("");
            onesCompTwoTxt.setText("");
            sumOnesCompTxt.setText("");
            sumIntTxt.setText("");
            gameCanvas.stopGame();
            gameCanvas.renewGame();
        }
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        // from gameCanvas to get dToSSprite and sumSprite
        // for avoiding passing both objects
        dToSSprite = this.gameCanvas.getdToSSprite();
        sToOSprite = this.gameCanvas.getsToOSprite();
        sumSprite = this.gameCanvas.getSumSprite();
    }

    public JButton getsToOBtn() {
        return sToOBtn;
    }

    public JButton getSumOnesCompBtn() {
        return sumOnesCompBtn;
    }

    public JButton getdToSBtn() {
        return dToSBtn;
    }

    public JButton getResetBtn() {
        return resetBtn;
    }
}
