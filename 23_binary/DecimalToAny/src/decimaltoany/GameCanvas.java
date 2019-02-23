/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package decimaltoany;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private ControlPanel controlPanel;
    private RenderSprite renderSprite;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        setLayout(new BorderLayout());
        setSleepTime(1000); // set the thread sleep time for different games 

        initComponent();
        startGame();
    }

    @Override
    public void initComponent() {
        getSpriteAry().clear();
        if (controlPanel == null) { // it does not allow init it twice
            initControlPanel();
        }
        initRenderSprite();
    }

    public void initControlPanel() {
        controlPanel = new ControlPanel();
        controlPanel.setGameCanvas(this);
        add(controlPanel, BorderLayout.NORTH);
    }

    public void initRenderSprite() {
        // initComponent() clears the spriteAry for cleaning renderSprite
        renderSprite = new RenderSprite();
        renderSprite.addPropertyChangeListener(new RenderDone());
        renderSprite.setActive(false);
        renderSprite.setVisible(false);
        getSpriteAry().add(renderSprite);
        controlPanel.setRenderSprite(renderSprite);
    }

    class RenderDone implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("convDone")) {
                controlPanel.getConvBtn().setVisible(false);
                controlPanel.getResetBtn().setVisible(true);
            }
        }
    }

    @Override
    public void paintCurrScore(Graphics2D g2d) {

    }

    @Override
    public void announceTermination(Graphics2D g2d) {

    }
}
