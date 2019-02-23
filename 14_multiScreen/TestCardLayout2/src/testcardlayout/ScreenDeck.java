/*
 * ScreenDeck.java -- A class that implements the screen "deck".
 */
package testcardlayout;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ScreenDeck extends JPanel {

    private CardLayout cardLayout;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ScreenDeck() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        initScreenDeck();
    }

    public void initScreenDeck() {
        GameCanvas gameCanvas = new GameCanvas();
        add(gameCanvas, "GameCanvas");
        gameCanvas.setParent(this);
        gameCanvas.setCardLayout(cardLayout);

        SplashScreen splashScreen = new SplashScreen();
        add(splashScreen, "SplashScreen");
        splashScreen.setParent(this);
        splashScreen.setCardLayout(cardLayout);

        SettingPanel settingPanel = new SettingPanel();
        add(settingPanel, "SettingPanel");
        settingPanel.setParent(this);
        settingPanel.setCardLayout(cardLayout);

        cardLayout.show(this, "SplashScreen");
    }
}
