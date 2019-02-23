/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data for the conversion.
 */
package sumSigned;

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
    private JTextField sumTxt;
    private JLabel statusLbl;
    private JButton sumBtn;
    private JButton convBtn;
    private JButton resetBtn;
    private JPanel inputP;
    private ConvSprite convSprite;
    private SumSprite sumSprite;
    
    private GameCanvas gameCanvas;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ControlPanel() {
        initControlPanel();
    }
    
    public void initControlPanel() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH, 220));
        setLayout(new BorderLayout());
        JPanel leftP = new JPanel(); // make a left margin
        leftP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(leftP, BorderLayout.WEST);
        JPanel rightP = new JPanel(); // make a right margin
        rightP.setPreferredSize(new Dimension(20, Consts.CV_HEIGHT));
        add(rightP, BorderLayout.EAST);
        inputP = createInputPanel();
        add(inputP, BorderLayout.CENTER);
    
        // initially disable these two buttons
        sumBtn.setEnabled(false);
        resetBtn.setEnabled(false); 
    }
    
    private JPanel createInputPanel() {
        JPanel inP = new JPanel();
        inP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 200));
        inP.setLayout(new BorderLayout());

        // the title panel on NORTH
        JPanel titleP = new JPanel();
        titleP.setLayout(new GridLayout(3, 1));
        JLabel titleLbl = new JLabel("Summation of two decimal integers"
                + " in signed-magnitude notation", 0);
        JLabel subTitleLbl = new JLabel("using 8-bit only (the absolute "
                + "value cannot be larger than 127)", 0);
        titleLbl.setForeground(Color.red);
        subTitleLbl.setForeground(Color.blue);
        JLabel gapLbl = new JLabel(" ", 0);
        titleP.add(titleLbl);
        titleP.add(subTitleLbl);
        titleP.add(gapLbl);
        inP.add(titleP, BorderLayout.NORTH);

        // the data panel on CENTER of the input panel
        JPanel dataP = new JPanel();
        dataP.setLayout(new GridLayout(6, 2));
        JLabel intOneLbl = new JLabel("The first integer in decimal: ");
        JLabel intTwoLbl = new JLabel("The second integer in decimal: ");
        intOneTxt = new JTextField();
        intTwoTxt = new JTextField();

        JLabel signedOneLbl = new JLabel("The first integer in signed: ");
        signedOneTxt = new JTextField();
        JLabel signedTwoLbl = new JLabel("The second integer in signed: ");
        signedTwoTxt = new JTextField();

        JLabel sumLbl = new JLabel("The summation in signed: ");
        sumTxt = new JTextField();

        JLabel gap2Lbl = new JLabel(" ", 0);
        dataP.add(intOneLbl);
        dataP.add(intOneTxt);
        dataP.add(intTwoLbl);
        dataP.add(intTwoTxt);
        dataP.add(signedOneLbl);
        dataP.add(signedOneTxt);
        dataP.add(signedTwoLbl);
        dataP.add(signedTwoTxt);
        dataP.add(gapLbl);
        dataP.add(gap2Lbl);
        dataP.add(sumLbl);
        dataP.add(sumTxt);
        inP.add(dataP, BorderLayout.CENTER);

        // the status panel on SOUTH of the input panel
        JPanel statusP = new JPanel();
        statusP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 25));
        statusLbl = new JLabel("    ", 0);
        statusLbl.setForeground(Color.red);
        statusP.add(statusLbl);
        inP.add(statusP, BorderLayout.SOUTH);

        // the button panel on EAST of the input panel
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(3, 1));
        convBtn = new JButton("Do Conversion");
        convBtn.addActionListener(new ConversionListener());
        sumBtn = new JButton("Do Summation");
        sumBtn.addActionListener(new SummationListener());
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new ResetListener());
        btnP.add(convBtn);
        btnP.add(sumBtn);
        btnP.add(resetBtn);
        inP.add(btnP, BorderLayout.EAST);

        return inP;
    }

    // inner class ConversionListener -- A listener that converts an integer in
    // decimal to an integer any base
    class ConversionListener implements ActionListener {

        int intOne, intTwo;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == convBtn)) {
                return;
            }
            convBtn.setEnabled(false);
            
            sumTxt.setText("");
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

                    // send data to ConvSprite for rendering
                    convSprite.setInitData(Math.abs(intOne), 
                            Math.abs(intTwo), 2);
                    convSprite.setActive(true);
                    convSprite.setVisible(true);
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

        /**
         * dToSignedStr() -- To express a signed binary
         */
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

        /**
         * dToBinary() -- To convert an integer in decimal to any base
         * number system. If base = 2, the method gives binary notation.
         */
        public char[] dToBinary(int dInt, int base) {
            ArrayList<Integer> theReverse;
            theReverse = convertDInt(dInt, base);
            char[] theIntChar = new char[theReverse.size()];
            loadIntArrayListToAry(theReverse, theIntChar, 0, theReverse.size());
            return theIntChar;
        }

        /**
         * convertDInt() -- To convert a decimal integer to any base and store
         * the result into an arraylist, which contains the reversed order of
         * the result. The correct order of result should be made by calling
         * loadIntArraylistToAry().
         */
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

        /**
         * loadIntVectorToAry() -- To revers the binary (or any base)
         * in the reversed order stored in the arraylist to the array.
         */
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
                        theA[start + len - i - 1] = (char)(theV.get(i) + 48);
                        break;
                }
            }
        }

        /**
         * aryToString() -- to convert the value in an array to a string for
         * painting so that it contains "|" and " "
         */ 
        public String aryToString(char[] ary) {
            String theS = "";
            for (int i = 0; i < ary.length; i++) {
                theS += (ary[i] + " ");
            }
            return theS;
        }

        /**
         * display() -- To display the contents of an array.
         */
        public void display(int[] ary) {
            for (int i = 0; i < ary.length; i++) {
                System.out.print(ary[i] + " ");
            }
            System.out.println();
        }
    }

    // SummationListener -- A class that implements the ActionListener.
    // It performs summation of two signed integers.
    class SummationListener implements ActionListener {

        final int ARYLEN = 8;
        int[] binaryIntOne = new int[ARYLEN];
        int[] binaryIntTwo = new int[ARYLEN];
        boolean overflowFlag = false;

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == sumBtn)) {
                return;
            }
            if (overflowFlag == true) {
                overflowFlag = false;
            }
            String sSignedOne = signedOneTxt.getText();
            String sSignedTwo = signedTwoTxt.getText();
            int[] sumResult;

            loadIntToAry(sSignedOne, sSignedTwo);
            // if both integers have the same sign: do addition
            // otherwise do subtraction
            if (binaryIntOne[0] == binaryIntTwo[0]) {
                sumSprite.setCommand(Consts.ADDB);
                sumResult = addBinary(binaryIntOne, binaryIntTwo);
                if (overflowFlag == true) {
                    statusLbl.setText("Overflow has occurred");
                    overflowFlag = false;
                }
            } else {
                sumSprite.setCommand(Consts.SUBB);
                String sOne = "";
                String sTwo = "";
                // get rid of the"|" character and sign bit
                for (int i = 1; i < binaryIntOne.length; i++) {
                    sOne += binaryIntOne[i];
                    sTwo += binaryIntTwo[i];
                }
                // determine which one is bigger that minus the smaller one
                if (Integer.parseInt(sOne) > Integer.parseInt(sTwo)) {
                    sumResult = subBinary(binaryIntOne, binaryIntTwo);
                } else {
                    sumResult = subBinary(binaryIntTwo, binaryIntOne);
                }
            }
            String sSum = sumToStr(sumResult);
            sumTxt.setText(sSum);

            // start render the operations
            if (overflowFlag == false) {
                convSprite.setVisible(false);
                sumSprite.initSummationData(sSignedOne, sSignedTwo,
                        binaryIntOne, binaryIntTwo);
                sumSprite.setActive(true);
                sumSprite.setVisible(true);
            } else {
                System.out.println("Overflow");
            }
        }

        private void loadIntToAry(String sSignedOne, String sSignedTwo) {
            int j = 0;
            for (int i = 0; i < sSignedOne.length(); i++) {
                String oneS = sSignedOne.substring(i, i + 1);
                String twoS = sSignedTwo.substring(i, i + 1);

                if ((!(oneS.equals("|"))) && (!(oneS.equals(" ")))) {
                    binaryIntOne[j] = Integer.parseInt(oneS);
                    binaryIntTwo[j] = Integer.parseInt(twoS);
                    j++;
                }
            }
        }

        private int[] addBinary(int[] binaryIntOne, int[] binaryIntTwo) {
            int[] tempSum = new int[ARYLEN];
            int[] carryBit = new int[ARYLEN];
            int sumBit;
            for (int i = binaryIntOne.length - 1; i > 0; i--) {
                sumBit = binaryIntOne[i] + binaryIntTwo[i] + carryBit[i];
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
            }
            tempSum[0] = binaryIntOne[0];
            if (carryBit[0] == 1) {
                overflowFlag = true;
            }
            return tempSum;
        }

        private int[] subBinary(int[] bOne, int[] bTwo) {
            // the values of bigger might be changed
            // here, make a copy to prevent bOne from being changed
            int[] bigger = new int[ARYLEN];
            int[] smaller = new int[ARYLEN];
            for (int i = 0; i < ARYLEN; i++) {
                bigger[i] = bOne[i];
                smaller[i] = bTwo[i];
            }
            //displayAry(bigger);
            int[] tempDiff = new int[ARYLEN];
            int[] borrowBit = new int[ARYLEN];
            int diffBit;
            for (int i = bigger.length - 1; i > 0; i--) {
                if (bigger[i] >= smaller[i]) {
                    diffBit = bigger[i] - smaller[i];
                } else {
                    borrowBit[i] = 2;
                    bigger[i - 1] -= 1;
                    diffBit = borrowBit[i] + bigger[i] - smaller[i];
                }
                tempDiff[i] = diffBit;
            }
            tempDiff[0] = bigger[0];
            return tempDiff;
        }

        private String sumToStr(int[] sumAry) {
            String sumStr = "| " + sumAry[0] + " | ";
            for (int i = 1; i < sumAry.length; i++) {
                sumStr += sumAry[i] + " ";
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
    }

    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getSource() == resetBtn)) {
                return;
            }
            convBtn.setEnabled(true);
            sumBtn.setEnabled(false);
            resetBtn.setEnabled(false);
            
            statusLbl.setText("");
            intOneTxt.setText("");
            intOneTxt.requestFocus(true);
            intTwoTxt.setText("");
            signedOneTxt.setText("");
            signedTwoTxt.setText("");
            sumTxt.setText("");
            gameCanvas.stopGame();
            gameCanvas.renewGame();
        }
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        // from gameCanvas to get convSprite and sumSprite
        // for avoiding passing both objects
        convSprite = this.gameCanvas.getConvSprite();
        sumSprite = gameCanvas.getSumSprite();  
    }

    public JButton getSumBtn() {
        return sumBtn;
    }

    public JButton getConvBtn() {
        return convBtn;
    }

    public JButton getResetBtn() {
        return resetBtn;
    }
}
