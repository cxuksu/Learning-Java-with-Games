/*
 * ControlPanel.java - A class that defines the control panel for the user
 * to input data for the conversion.
 */
package decimaltoany;

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
        JLabel titleLbl = new JLabel("Convert positive integer in decimal " +
                "to any base", 0);
        titleLbl.setForeground(Color.red);
        JLabel gapLbl = new JLabel(" ", 0);
        titleP.add(titleLbl);
        titleP.add(gapLbl);
        inP.add(titleP, BorderLayout.NORTH);

        // the data panel is on CENTER of the input panel
        JPanel dataP = new JPanel();
        dataP.setLayout(new GridLayout(4, 3));
        JLabel intLbl = new JLabel("The integer in decimal: ");
        intTxt = new JTextField();
        JLabel intGap = new JLabel("  ", 0);
        dataP.add(intLbl);
        dataP.add(intTxt);
        dataP.add(intGap);
        
        JLabel baseLbl = new JLabel("The base of the target: ");
        baseTxt = new JTextField();
        convBtn = new JButton("Do Conversion");
        convBtn.addActionListener(new ConversionListener());
        convBtn.setActionCommand(Consts.CONV+""); // convert int to be String
        dataP.add(baseLbl);
        dataP.add(baseTxt);
        dataP.add(convBtn);
        
        JLabel empty1Lbl = new JLabel("    ", 0);
        JLabel empty2Lbl = new JLabel("    ", 0);
        JLabel empty3Lbl = new JLabel("    ", 0);
        dataP.add(empty1Lbl);
        dataP.add(empty2Lbl);
        dataP.add(empty3Lbl);
        
        JLabel targetLbl = new JLabel("The integer in the base: ");
        targetTxt = new JTextField();
        resetBtn = new JButton("Reset");
        resetBtn.setActionCommand(Consts.RESET+"");
        resetBtn.addActionListener(new ResetListener());
        dataP.add(targetLbl);
        dataP.add(targetTxt);
        dataP.add(resetBtn);
        resetBtn.setVisible(false);
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
            if (!(evt.getActionCommand().equals(Consts.CONV+""))) {
                return;
            }
            statusLbl.setForeground(Color.red);
            String inputIntStr = intTxt.getText();
            String baseStr = baseTxt.getText();
            if ((inputIntStr.equals("") || (baseStr.equals("")))) {
                statusLbl.setText("No input integer or base");
                return;
            }

            // verify input data for possible errors
            int correctValue = verifyInput(inputIntStr, baseStr);
            switch (correctValue) {
                case 0: // no erroe, start working
                    statusLbl.setText("");
                    int inputInt = Integer.parseInt(inputIntStr);
                    baseInt = Integer.parseInt(baseStr);
                    String sOut = aryToString(deciIntToAnyBase(
                            inputInt, baseInt));
                    targetTxt.setText(sOut);

                    // start ploting
                    renderSprite.setInitData(inputInt, baseInt);
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
        
        public String aryToString(char[] ary) {
            String theS = "";
            for (int i=0; i<ary.length; i++) {
                theS += (ary[i] + " ");
            }
            return theS;
        }

        private int verifyInput(String sInt, String sBase) {
            int correctVal = 0;
            int theBase = Integer.parseInt(sBase);
            if ((theBase < 2) || (theBase > 16)) {
                correctVal = 1;
                return correctVal;
            }
            for (int i=0; i<sInt.length(); i++) {
                int theV = realValue(sInt.substring(i, i+1));
                if (theV > 9) {
                    correctVal = 2;
                    break;
                }
            }
            return correctVal;
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
            
            while(theV > 0) {
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
            for (int i=0; i<len; i++) {
                switch (((theV.get(i)))) {
                    case 10:
                        theA[start+len-i-1] = 'A';
                        break;
                    case 11:
                        theA[start+len-i-1] = 'B';
                        break;
                    case 12:
                        theA[start+len-i-1] = 'C';
                        break;
                    case 13:
                        theA[start+len-i-1] = 'D';
                        break;
                    case 14:
                        theA[start+len-i-1] = 'E';
                        break;
                    case 15:
                        theA[start+len-i-1] = 'F';
                        break;
                    default:
                        theA[start+len-i-1] =
                                (char)((((theV.get(i)))) + 48);
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
            if (str.charAt(0) < 'A') {
                value = Integer.parseInt(str);
            }
            return value;
        }
    }
    
    class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!(evt.getActionCommand().equals(Consts.RESET+""))) {
                return;
            }
            if ((JButton)evt.getSource() == resetBtn) {
                convBtn.setVisible(true);
                resetBtn.setVisible(false);
                intTxt.requestFocus();
                intTxt.setText("");
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
