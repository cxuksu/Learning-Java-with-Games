/*
 * GameCanvas.java - A class that implements the game canvas.
 */
package trysuspendthread;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameCanvas extends AbsGameCanvas {
    private BallSprite ballSprite;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public GameCanvas() {
        super();
        initComponent();

        startGame();
    }

    @Override
    public void initComponent() {
        initBall();
    }

    public void initBall() {
        ballSprite = new BallSprite();
        ballSprite.addPropertyChangeListener(new ChangeListener());
        getSpriteAry().add(ballSprite);
    }
    
    class ChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String theChange = evt.getPropertyName();
            if (theChange.equals("pauseBall")) {
                setRunning(false);
                System.out.println("receive pauseBall");
            }
        }
    }

}
