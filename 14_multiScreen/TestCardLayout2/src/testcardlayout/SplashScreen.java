/*
 * SplashScreen.java -- A class that implements a splash screen.
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
public class SplashScreen extends JPanel implements ActionListener {

    private ScreenDeck parent;
    private CardLayout cardLayout;
    private JButton settingBtn;
    private JButton quitBtn;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public SplashScreen() {
        setLayout(new BorderLayout());
        initButtons();
        repaint();
    }

    public void initButtons() {
        JPanel btnP = new JPanel();

        settingBtn = new JButton("Open SettingPanel");
        settingBtn.addActionListener(this);

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(this);

        btnP.add(settingBtn);
        btnP.add(quitBtn);

        add(btnP, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        String script1
                = "This is the class SplashScreen.java";
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Times", Font.BOLD, 26));
        g2d.drawString(script1, 50, 100);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == settingBtn) {
            cardLayout.show(parent, "SettingPanel");
        } else if (evt.getSource() == quitBtn) {
            System.exit(0);
        }
    }

    public void setParent(ScreenDeck parent) {
        this.parent = parent;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }
}
