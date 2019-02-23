/*
 * ControlPanel.java - A class that defines the control panel for the project
 * QuickSort. It provides four buttons: New, Fill, Append, Delete for making
 * a "preferred" array.
 */
package multisort;

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

    private int numberCells; // initial size; it can be changed by users
    private int numberData; // number of data in the array
    private JPanel controlP;
    private JButton newBtn;
    private JButton fillBtn;
    private JButton appendBtn;
    private JButton deleteBtn;
    private JLabel valueLabel;
    private JTextField valueField;
    private JPanel msgP;
    private JLabel msgLabel;
    private JTextField msgText;

    private GameCanvas gameCanvas;
    private BarComposite barComposite;
    private ActionListener listener;

    private JPanel sortP;
    private JButton bubbleBtn;
    private JButton quickBtn;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ControlPanel() {
        setPreferredSize(new Dimension(Consts.CV_WIDTH, Consts.CONT_P_H));
        initControlPanel();
    }

    private void initControlPanel() {
        setLayout(new GridLayout(3, 1));
        listener = new MyActionListener();

        controlP = new JPanel();
        add(controlP);

        newBtn = new JButton("New");
        controlP.add(newBtn);
        newBtn.addActionListener(listener);
        fillBtn = new JButton("Fill");
        controlP.add(fillBtn);
        fillBtn.addActionListener(listener);
        appendBtn = new JButton("Append");
        controlP.add(appendBtn);
        appendBtn.addActionListener(listener);
        deleteBtn = new JButton("Delete(index)");
        controlP.add(deleteBtn);
        deleteBtn.addActionListener(listener);

        valueLabel = new JLabel("    Value: ");
        controlP.add(valueLabel);
        valueField = new JTextField(5);
        controlP.add(valueField);

        sortP = new JPanel();
        add(sortP);
        bubbleBtn = new JButton("BubbleSort");
        bubbleBtn.addActionListener(listener);
        sortP.add(bubbleBtn);
        quickBtn = new JButton("QuickSort");
        quickBtn.addActionListener(listener);
        sortP.add(quickBtn);

        msgP = new JPanel();
        add(msgP);
        msgLabel = new JLabel("Message: ");
        msgText = new JTextField(30);
        msgText.setForeground(Color.red);
        msgText.setText(" Enter a value before selecting an operation");
        msgP.add(msgLabel);
        msgP.add(msgText);
    }

    public void findBarComposite() {
        // so not necessary to pass barComposite, whihc is in the
        // global spriteAry as the first element get(0)
        barComposite = (BarComposite) gameCanvas.getSpriteAry().get(0);
        numberCells = barComposite.getNumberCells(); // initialize numberCells
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("New")) {
                String inputValue = valueField.getText();
                if (!(inputValue.equals(""))) {
                    numberCells = Integer.parseInt(inputValue);
                    barComposite.setNumberCells(numberCells);
                    barComposite.eraseBarList();
                    barComposite.initSprite(); // re-init a new array
                    newBtn.setEnabled(false);
                    msgText.setText(" A new array has been created");
                } else {
                    msgText.setText(" Enter the number of cells "
                            + "for the new array");
                }
            } else if (evt.getActionCommand().equals("Fill")) {
                String fillNumStr = valueField.getText();
                if ((!newBtn.isEnabled()) && (fillNumStr.equals(""))) {
                    msgText.setText(" Enter the number of filling data");
                } else {
                    // using the default value of numberCells
                    if ((newBtn.isEnabled()) && (fillNumStr.equals(""))) {
                        numberData = numberCells;
                    } else if (!fillNumStr.equals("")) { // using the specified
                        numberData = Integer.parseInt(fillNumStr);
                    }
                    if (numberData > numberCells) {
                        msgText.setText(
                                " The number of fill in data is larger than "
                                + "the number of cells");
                    } else {
                        barComposite.setNumberData(numberData);
                        barComposite.fillBars();
                        fillBtn.setEnabled(false);
                        newBtn.setEnabled(false);
                        msgText.setText(" The array has been filled");
                    }
                }
            } else if (evt.getActionCommand().equals("Append")) {
                if (numberData < numberCells) { // possible to insert new data
                    String appendValueStr = valueField.getText();
                    if (!appendValueStr.equals("")) {
                        int appendValue = Integer.parseInt(appendValueStr);
                        barComposite.setAppendValue(appendValue);
                        barComposite.appendElement();
                        numberData++;
                        barComposite.setNumberData(numberData);
                        gameCanvas.setCurrentArrow(numberData - 1);
                        msgText.setText(" The new value has been appended");
                    } else {
                        msgText.setText(" Enter the value for appending");
                    }
                } else {
                    msgText.setText(" No more empty cell for "
                            + "appending new data");
                }
            } else if (evt.getActionCommand().equals("Delete(index)")) {
                String deleteValueStr = valueField.getText();
                if (deleteValueStr.equals("")) {
                    msgText.setText(" Enter an index for the deletion");
                } else {
                    int deleteIdx = Integer.parseInt(deleteValueStr);
                    if ((deleteIdx >= 0) && (deleteIdx < numberData)) {
                        gameCanvas.setCurrentArrow(deleteIdx);
                        barComposite.deleteElement(deleteIdx);
                        numberData--;
                        barComposite.setNumberData(numberData);
                        msgText.setText(" The item has been deleted");
                    } else {
                        msgText.setText(" The index is out of range");
                    }
                }
            } else if (evt.getActionCommand().equals("BubbleSort")) {
                msgText.setText(" Bubble sorting");
                // initialize mutual lock as false to allow either one running
                if (!gameCanvas.isMutualLock()) {
                    // when one sort starts, set the mutual lock as true to
                    // preventing any of them would be started so that only
                    // one of them running at a time.
                    gameCanvas.setMutualLock(true);
                    // until the sorting process finished the mutual lock
                    // will be released for both algorithms.(see the inner 
                    // class SortAlgoListener in the class GameCanvas.java)

                    // only after "Fill", bubble sort can be initialized
                    gameCanvas.initBubbleSort();
                    gameCanvas.initInOutArrow();
                    gameCanvas.erasePivotArrow();
                    gameCanvas.getBubbleSort().start();
                }
            } else if (evt.getActionCommand().equals("QuickSort")) {
                msgText.setText(" Quick sorting");
                // make mutual lock to allow only one of them working at a time
                if (!gameCanvas.isMutualLock()) {
                    // when one sort starts, set the mutual lock as true to
                    // preventing any of them would be started so that only
                    // one of them running at a time.
                    gameCanvas.setMutualLock(true);

                    // sort should be initialized after "Fill"
                    gameCanvas.initQuickSort();
                    gameCanvas.initInOutArrow();
                    gameCanvas.initPivotArrow(); // quick sort needs pivot arrow
                    gameCanvas.getQuickSort().start();
                }
            }
            valueField.setText("");
            gameCanvas.repaint(); // for re-paint the changes before sorting
        }
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
