/*
 * GameCanvas.java - A class defines the game canvas.
 */
package tornado;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BallSprite ballSprite;

    public GameCanvas() {
        super();
        initComponent();
    }

    private void initComponent() {
        getSpriteAry().clear();
        initBallSprite();
        startGame();
    }

    public void initBallSprite() {
        ballSprite = new BallSprite();
        ballSprite.initSprite();
        getSpriteAry().add(ballSprite);
        repaint();
    }
}
