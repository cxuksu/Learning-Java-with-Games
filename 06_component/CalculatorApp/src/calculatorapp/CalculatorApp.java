/*
 * CalculatorApp -- A project that implements a calculator
 */
package calculatorapp;

import javax.swing.JFrame;

public class CalculatorApp extends JFrame {
    private CalculatorFace face;

    public CalculatorApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("A Calculator");
        setSize(160, 220);

        face = new CalculatorFace();
        add(face);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}
