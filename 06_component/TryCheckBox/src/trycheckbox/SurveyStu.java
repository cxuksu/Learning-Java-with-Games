/*
 * SurveyStu.java - A class defines a survey sheet.
 */
package trycheckbox;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cxu
 */
public class SurveyStu extends JPanel {

    private JPanel oneQu;
    private JPanel qu1;
    private JPanel qu2;
    private JLabel questionLbl;
    private ButtonGroup quGroup;
    private JCheckBox CB1;
    private JCheckBox CB2;
    private JCheckBox CB3;
    private JCheckBox CB4;

    public SurveyStu() {
        qu1 = initOneQu("1. Which language do you know (all applicable) ",
                "Java", "C++", "C", "C#", 0);

        qu2 = initOneQu("2. Which course have you taken (all applicable) ",
                "Math", "Physics", "Biology", "Java programming", 1);
    }

    private JPanel initOneQu(String qu, String a1, String a2, String a3,
            String a4, int single) {
        oneQu = new JPanel();
        oneQu.setPreferredSize(new Dimension(450, 120));
        oneQu.setLayout(new GridLayout(5, 1));
        EmptyBorder eb = new EmptyBorder(new Insets(5, 10, 5, 10));
        oneQu.setBorder(eb);
        questionLbl = new JLabel(qu);
        CB1 = new JCheckBox(a1);
        CB2 = new JCheckBox(a2);
        CB3 = new JCheckBox(a3);
        CB4 = new JCheckBox(a4);

        if (single == 0) {
            oneQu.add(questionLbl);
            oneQu.add(CB1);
            oneQu.add(CB2);
            oneQu.add(CB3);
            oneQu.add(CB4);
            add(oneQu);
        } else {
            oneQu.add(questionLbl);
            quGroup = new ButtonGroup();
            quGroup.add(CB1);
            quGroup.add(CB2);
            quGroup.add(CB3);
            quGroup.add(CB4);
            //oneQu.add(quGroup);
            add(oneQu);
            //return quGroup;
        }
        
        return oneQu;
    }
}
