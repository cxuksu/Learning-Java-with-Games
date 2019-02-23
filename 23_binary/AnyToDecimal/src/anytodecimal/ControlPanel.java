/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data and control the conversion.
 */
package anytodecimal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author cxu
 */
public class ControlPanel extends JPanel {

    private JTextField intTxt;
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
        JLabel titleLbl = new JLabel("Convert positive integer in any base "
                + "to decimal", 0);
        titleLbl.setForeground(Color.red);
        JLabel gapLbl = new JLabel(" ", 0);
        titleP.add(titleLbl);
        titleP.add(gapLbl);
        inP.add(titleP, BorderLayout.NORTH);

        // the data panel is on CENTER of the input panel
        JPanel dataP = new JPanel();
        dataP.setLayout(new GridLayout(4, 3));
        JLabel intLbl = new JLabel("The integer in any base: ");
        intTxt = new JTextField();
        JLabel intGap = new JLabel("  ", 0);
        dataP.add(intLbl);
        dataP.add(intTxt);
        dataP.add(intGap);

        JLabel baseLbl = new JLabel("The base of the integer: ");
        baseTxt = new JTextField();
        convBtn = new JButton("Do Conversion");
        convBtn.addActionListener(new ConversionListener());
        convBtn.setActionCommand(Consts.CONV + ""); // convert int to be String
        dataP.add(baseLbl);
        dataP.add(baseTxt);
        dataP.add(convBtn);

        JLabel empty1Lbl = new JLabel("    ", 0);
        JLabel empty2Lbl = new JLabel("    ", 0);
        JLabel empty3Lbl = new JLabel("    ", 0);
        dataP.add(empty1Lbl);
        dataP.add(empty2Lbl);
        dataP.add(empty3Lbl);

        JLabel targetLbl = new JLabel("The integer in decimal: ");
        targetTxt = new JTextField();
        resetBtn = new JButton("Reset");
        resetBtn.setActionCommand(Consts.RESET + "");
        resetBtn.addActionListener(new ResetListener());
        dataP.add(targetLbl);
        dataP.add(targetTxt);
        dataP.add(resetBtn);
        resetBtn.setEnabled(false);
        inP.add(dataP, BorderLayout.CENTER);

        // the status panel is on SOUTH of the input panel
        JPanel statusP = new JPanel();
        statusP.setPreferredSize(new Dimension(Consts.CV_WIDTH, 30));
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
            String inputIntStr = intTxt.getText();
            String baseStr = baseTxt.getText();
            if ((inputIntStr.equals("") || (baseStr.equals("")))) {
                statusLbl.setText("No input integer or base");
                convBtn.setEnabled(false);
                resetBtn.setEnabled(true);
                return;
            }

            // verify input data for possible errors
            int correctValue = verifyInput(inputIntStr, baseStr);
            switch (correctValue) {
                case 0: // no error, start working
                    statusLbl.setText("");
                    baseInt = Integer.parseInt(baseStr);
                    int decimalInt = convertAnyBaseInt(inputIntStr, baseInt);
                    targetTxt.setText(String.valueOf(decimalInt));
                    convBtn.setEnabled(false);
                    resetBtn.setEnabled(true);

                    // start ploting
                    renderSprite.setInitData(inputIntStr, baseInt);
                    renderSprite.setActive(true);
                    renderSprite.setVisible(true);
                    break;
                case 1:
                    statusLbl.setText("The base should be "
                            + "in the range of [2, 16]");
                    convBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                    break;
                case 2:
                    statusLbl.setText("Input digits should be "
                            + "in the range of [0, F]");
                    convBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                    break;
                case 3:
                    statusLbl.setText("Input digits cannot be larger than base. "
                            + "Enter numbers again");
                    convBtn.setEnabled(false);
                    resetBtn.setEnabled(true);
                    break;
                default:
            }
        }

        private int verifyInput(String sInt, String sBase) {
            int correctVal = 0;
            int theBase = Integer.parseInt(sBase);
            if ((theBase < 2) || (theBase > 16)) {
                correctVal = 1;
                return correctVal;
            }
            for (int i = 0; i < sInt.length(); i++) {
                int theV = realValue(sInt.substring(i, i + 1));
                int theB = realValue(sBase);
                if (theV > 16) {
                    correctVal = 2;
                    break;
                }
                if (theB <= theV) {
                    correctVal = 3;
                    break;
                }
            }
            return correctVal;
        }

        public int convertAnyBaseInt(String sAnyBase, int iBase) {
            int theD = 0;
            for (int i = sAnyBase.length(); i > 0; i--) {
                int theValue = realValue(sAnyBase.substring(i - 1, i));
                theD += (theValue * Math.pow(iBase, sAnyBase.length() - i));
            }
            return theD;
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
            if (str.charAt(0) < 'A') {
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
                resetBtn.setEnabled(false);
                convBtn.setEnabled(true);
                intTxt.requestFocus();
                intTxt.setText("");
                baseTxt.setText("");
                targetTxt.setText("");
                statusLbl.setText("");
                gameCanvas.stopGame();
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

    public JButton getResetBtn() {
        return resetBtn;
    }
}
