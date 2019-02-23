/*
 * SettingPanel.java - A class defines the setting panel.
 */
package testcardlayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class SettingPanel extends JPanel implements ActionListener {

    private JButton canvasBtn;
    private CardLayout cardLayout;
    private ScreenDeck parent;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SettingPanel() {
        setLayout(new BorderLayout());
        initButtons();
        repaint();
    }

    public void initButtons() {
        JPanel btnP = new JPanel();

        canvasBtn = new JButton("Start Game");
        canvasBtn.addActionListener(this);

        btnP.add(canvasBtn);

        add(btnP, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 26));
        g2d.drawString("This is the class SettingPanel.java", 50, 100);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == canvasBtn) {
            cardLayout.show(parent, "GameCanvas");
        }
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public void setParent(ScreenDeck parent) {
        this.parent = parent;
    }
}
