/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjradiobtn;

import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private ButtonGroup fileGroup;
    private JLabel fileLbl;
    private JRadioButton fileajRBtn;
    private JRadioButton filekzRBtn;
    private JPanel selFileP;

    public PlayerPanel() {
        initComponent();
    }

    public void initComponent() {
        // Select a file for reading a given word
        fileajRBtn = new JRadioButton("wordaj.txt");
        filekzRBtn = new JRadioButton("wordkz.txt");
        
        fileGroup = new ButtonGroup();
        fileGroup.add(fileajRBtn);
        fileGroup.add(filekzRBtn);
        
        selFileP = new JPanel();
        selFileP.setLayout(new GridLayout(3, 1));
        selFileP.add(new JLabel("Select a file for reading a word: "));
        selFileP.add(fileajRBtn);
        selFileP.add(filekzRBtn);
        
        this.add(selFileP);
    }
}
