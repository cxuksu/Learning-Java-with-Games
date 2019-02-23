/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjradiobtn;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class TestJRadioBtn extends JFrame {

    private JPanel playerP;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public TestJRadioBtn() {
        setSize(500, 400);

        playerP = new PlayerPanel();
        add(playerP);

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TestJRadioBtn();
    }

}
