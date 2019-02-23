/*
 * GameCanvas.java - A class defines the canvas.
 */
package testwheel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class GameCanvas extends JPanel {

    private WheelGUI wheelGUI;
    private JButton turnWheelBtn;

    public GameCanvas() {
        this.setLayout(new BorderLayout());
        initComponent();
    }

    private void initComponent() {
        initWheelGUI();
        initJButton();
    }

    public void initWheelGUI() {
        wheelGUI = new WheelGUI();
        this.add(wheelGUI, BorderLayout.CENTER);
    }

    public void initJButton() {
        turnWheelBtn = new JButton("Turn the wheel");
        turnWheelBtn.addActionListener(new MyActionListener());
        this.add(turnWheelBtn, BorderLayout.SOUTH);
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == turnWheelBtn) {
                wheelGUI.reStart();
            }
        }
    }
}
