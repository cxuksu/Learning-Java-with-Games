/*
 * TryCheckBox.java - The main class of the project.
 */
package trycheckbox;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class TryCheckBox extends JFrame {

    public TryCheckBox() {
        setTitle("A survey");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        
        SurveyStu survey = new SurveyStu();
        add(survey);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new TryCheckBox();
    }
}
