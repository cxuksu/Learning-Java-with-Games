/*
 * ScreenDeck.java -- A class that implements the screenDeck of the game.
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

        // instantiate gameCanvas first since it is required by the splashScreen
        GameCanvas gameCanvas = new GameCanvas();
        gameCanvas.setParent(this);
        gameCanvas.setCardLayout(cardLayout);
        add(gameCanvas, "GameCanvas");
        
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setParent(this);
        splashScreen.setCardLayout(cardLayout);
        splashScreen.setGameCanvas(gameCanvas);
        add(splashScreen, "SplashScreen");
        cardLayout.show(this, "SplashScreen");
    }
}
