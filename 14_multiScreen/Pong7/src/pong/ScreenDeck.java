/*
 * ScreenDeck.java -- A class that implements the control panel of the game.
 */
package pong;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ScreenDeck extends JPanel {

    private CardLayout cardLayout;
    
    public ScreenDeck() {
        initComponent();
    }

    private void initComponent() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // instantiate gameCanvas first since it is required by the splashSheet
        GameCanvas gameCanvas = new GameCanvas();
        gameCanvas.setParent(this);
        gameCanvas.setCardLayout(cardLayout);
        add(gameCanvas, "GameCanvas");
        
        SplashScreen splashSheet = new SplashScreen();
        splashSheet.setParent(this);
        splashSheet.setCardLayout(cardLayout);
        splashSheet.setGameCanvas(gameCanvas);
        add(splashSheet, "SplashSheet");
        cardLayout.show(this, "SplashSheet");
    }
}
