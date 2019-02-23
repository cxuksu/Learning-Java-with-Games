/*
 * CalculatorFace.java -- A class defines the GUI of the calculator
 */
package calculatorapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalculatorFace extends JPanel implements ActionListener {

    private JTextField dispOperand1;
    private JTextField dispOperand2;
    private JTextField dispLine;
    private JTextField dispResult;
    private JPanel topPortion;
    private JButton bZero = new JButton("0");
    private JButton bOne = new JButton("1");
    private JButton bTwo = new JButton("2");
    private JButton bThree = new JButton("3");
    private JButton bFour = new JButton("4");
    private JButton bFive = new JButton("5");
    private JButton bSix = new JButton("6");
    private JButton bSeven = new JButton("7");
    private JButton bEight = new JButton("8");
    private JButton bNine = new JButton("9");
    private JButton bDot = new JButton(".");
    private JButton bEqual = new JButton("=");
    private JButton bAdd = new JButton("+");
    private JButton bSub = new JButton("-");
    private JButton bMul = new JButton("*");
    private JButton bDiv = new JButton("/");
    private JButton bReset = new JButton("Reset");
    // global variables for operations and controls
    private String ope1;
    private String ope2;
    private String op;
    private String res;
    private boolean getOpe1;
    private boolean getOpe2;
    private boolean getOp;
    private boolean getEqual;

    public CalculatorFace() {
        initComponents();
        addAction();
        initReset();
    }

    // initialize GUI
    private void initComponents() {
        // all buttons
        JPanel tb = new JPanel(); // tb -- top board
        JPanel pp = new JPanel(); // pp -- pad panel
        pp.setLayout(new GridLayout(4, 3, 5, 5));
        bSeven.setPreferredSize(new Dimension(30, 20));
        pp.add(bSeven);
        bEight.setPreferredSize(new Dimension(30, 20));
        pp.add(bEight);
        bNine.setPreferredSize(new Dimension(30, 20));
        pp.add(bNine);
        bFour.setPreferredSize(new Dimension(30, 20));
        pp.add(bFour);
        bFive.setPreferredSize(new Dimension(30, 20));
        pp.add(bFive);
        bSix.setPreferredSize(new Dimension(30, 20));
        pp.add(bSix);
        bOne.setPreferredSize(new Dimension(30, 20));
        pp.add(bOne);
        bTwo.setPreferredSize(new Dimension(30, 20));
        pp.add(bTwo);
        bThree.setPreferredSize(new Dimension(30, 20));
        pp.add(bThree);
        bZero.setPreferredSize(new Dimension(30, 20));
        pp.add(bZero);
        bDot.setPreferredSize(new Dimension(30, 20));
        pp.add(bDot);
        bEqual.setPreferredSize(new Dimension(30, 20));
        pp.add(bEqual);
        tb.add(pp);

        JPanel cp = new JPanel(); // cp -- calculation panel
        cp.setLayout(new GridLayout(4, 1, 5, 5));
        bAdd.setPreferredSize(new Dimension(30, 20));
        cp.add(bAdd);
        bSub.setPreferredSize(new Dimension(30, 20));
        cp.add(bSub);
        bMul.setPreferredSize(new Dimension(30, 20));
        cp.add(bMul);
        bDiv.setPreferredSize(new Dimension(30, 20));
        cp.add(bDiv);
        tb.add(cp);

        // the console
        dispOperand1 = new JTextField(20);
        dispOperand1.setHorizontalAlignment(JTextField.RIGHT);
        dispOperand1.setBorder(null);
        dispOperand2 = new JTextField();
        dispOperand2.setHorizontalAlignment(JTextField.RIGHT);
        dispOperand2.setBorder(null);
        dispLine = new JTextField("----------------------------------");
        dispLine.setHorizontalAlignment(JTextField.RIGHT);
        dispLine.setBorder(null);
        dispResult = new JTextField();
        dispResult.setHorizontalAlignment(JTextField.RIGHT);
        dispResult.setBorder(null);
        topPortion = new JPanel();
        topPortion.setLayout(new GridLayout(4, 1));
        topPortion.add(dispOperand1);
        topPortion.add(dispOperand2);
        topPortion.add(dispLine);
        topPortion.add(dispResult);

        // organize all components
        this.setLayout(new BorderLayout());
        this.add(topPortion, BorderLayout.NORTH);
        this.add(tb, BorderLayout.CENTER);
        this.add(bReset, BorderLayout.SOUTH);
    }

    // assign action listeners to components
    private void addAction() {
        bZero.addActionListener(this);
        bOne.addActionListener(this);
        bTwo.addActionListener(this);
        bThree.addActionListener(this);
        bFour.addActionListener(this);
        bFive.addActionListener(this);
        bSix.addActionListener(this);
        bSeven.addActionListener(this);
        bEight.addActionListener(this);
        bNine.addActionListener(this);
        bDot.addActionListener(this);
        bAdd.addActionListener(this);
        bSub.addActionListener(this);
        bMul.addActionListener(this);
        bDiv.addActionListener(this);
        bEqual.addActionListener(this);
        bReset.addActionListener(this);
    }

    // initialize values for starting and reseting
    private void initReset() {
        ope1 = "";
        ope2 = "";
        op = "";
        res = "";
        getOpe1 = true;
        getOpe2 = false;
        getOp = false;
        getEqual = false;
        dispOperand1.setText("");
        dispOperand2.setText("");
        dispResult.setText("");
    }

    // implementing the action listener
    @Override
    public void actionPerformed(ActionEvent evt) {
        String aLetter = "";
        Object obj = (Object) evt.getSource();
        if (obj.equals(bZero)) {
            aLetter = "0";
            getOp = false;
        } else if (obj.equals(bOne)) {
            aLetter = "1";
            getOp = false;
        } else if (obj.equals(bTwo)) {
            aLetter = "2";
            getOp = false;
        } else if (obj.equals(bThree)) {
            aLetter = "3";
            getOp = false;
        } else if (obj.equals(bFour)) {
            aLetter = "4";
            getOp = false;
        } else if (obj.equals(bFive)) {
            aLetter = "5";
            getOp = false;
        } else if (obj.equals(bSix)) {
            aLetter = "6";
            getOp = false;
        } else if (obj.equals(bSeven)) {
            aLetter = "7";
            getOp = false;
        } else if (obj.equals(bEight)) {
            aLetter = "8";
            getOp = false;
        } else if (obj.equals(bNine)) {
            aLetter = "9";
            getOp = false;
        } else if (obj.equals(bDot)) {
            aLetter = ".";
            getOp = false;
        } else if (obj.equals(bAdd)) {
            assignOp("+");
        } else if (obj.equals(bSub)) {
            assignOp("-");
        } else if (obj.equals(bMul)) {
            assignOp("*");
        } else if (obj.equals(bDiv)) {
            assignOp("/");
        }

        // accumulate digits for ope1 and ope2
        if (getOpe1 && !getOpe2) { // first operand
            if (!getEqual) {
                ope1 = ope1 + aLetter;
            }
            dispOperand1.setText(ope1);
        } else if (!getOpe1 && getOpe2) { // second operand
            if (!getEqual && !getOp) { // cp won't be added
                ope2 = ope2 + aLetter;
            }
            dispOperand2.setText(op + " " + ope2);
        }

        // equal sign trigers computations and reset
        if (obj.equals(bEqual)) {
            if (!(ope1.equals("")) && !(op.equals(""))
                    && !(ope2.equals(""))) {
                getEqual = true;
                double ope1Value = Double.parseDouble(ope1);
                double ope2Value = Double.parseDouble(ope2);
                if (op.equals("+")) {
                    doAdd(ope1Value, ope2Value);
                } else if (op.equals("-")) {
                    doSub(ope1Value, ope2Value);
                } else if (op.equals("*")) {
                    doMul(ope1Value, ope2Value);
                } else if (op.equals("/")) {
                    doDiv(ope1Value, ope2Value);
                }
            }
        } else if (obj.equals(bReset)) {
            initReset();
        }
    }

    private void assignOp(String operator) {
        if (!ope1.equals("")) { // only when ope1 is ready
            op = operator;
            getOp = true;
            getOpe1 = false;
            getOpe2 = true;
        }
    }

    private void doAdd(double ope1, double ope2) {
        double resValue = ope1 + ope2;
        res = "" + resValue;
        dispResult.setText(res);
    }

    private void doSub(double ope1, double ope2) {
        double resValue = ope1 - ope2;
        res = "" + resValue;
        dispResult.setText(res);
    }

    private void doMul(double ope1, double ope2) {
        double resValue = ope1 * ope2;
        res = "" + resValue;
        dispResult.setText(res);
    }

    private void doDiv(double ope1, double ope2) {
        double resValue = ope1 / ope2;
        res = "" + resValue;
        dispResult.setText(res);
    }
}
