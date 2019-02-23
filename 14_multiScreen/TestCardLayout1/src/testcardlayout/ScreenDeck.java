/*
 * ScreenDeck.java -- A class that uses a CardLayout to organize three
 * screens corresponding to three classes as a screen "deck".
 */
package testcardlayout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ScreenDeck extends JPanel implements ActionListener {

    private JPanel screenStack;
    private JPanel buttons;
    private CardLayout cardLayout;
    private JButton canvasBtn;
    private JButton splashBtn;
    private JButton settingBtn;

    public ScreenDeck() {
        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        initButtons();
        initScreenStack();
    }

    private void initButtons() {
        buttons = new JPanel();
        canvasBtn = new JButton("Open GameCanvas");
        canvasBtn.addActionListener(this);
        buttons.add(canvasBtn);

        splashBtn = new JButton("Open SplashScreen");
        splashBtn.addActionListener(this);
        buttons.add(splashBtn);

        settingBtn = new JButton("Open SettingPanel");
        settingBtn.addActionListener(this);
        buttons.add(settingBtn);

        add(buttons, BorderLayout.NORTH);
    }

    public void initScreenStack() {
        screenStack = new JPanel();
        cardLayout = new CardLayout();
        screenStack.setLayout(cardLayout);

        GameCanvas gameCanvas = new GameCanvas();
        screenStack.add(gameCanvas, "GameCanvas");

        SplashScreen splashScreen = new SplashScreen();
        screenStack.add(splashScreen, "SplashScreen");

        SettingPanel settingPanel = new SettingPanel();
        screenStack.add(settingPanel, "SettingPanel");

        add(screenStack, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) screenStack.getLayout();
        if (evt.getSource() == canvasBtn) {
            cl.show(screenStack, "GameCanvas");
        } else if (evt.getSource() == splashBtn) {
            cl.show(screenStack, "SplashScreen");
        } else {
            cl.show(screenStack, "SettingPanel");
        }
    }

}
