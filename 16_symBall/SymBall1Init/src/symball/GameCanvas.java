/*
 * GameCanvas.java - A concrete class implements the game canvas. It is the
 * subclass of the abstract class AbsGameCanvas.java.
 */
package symball;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BallSprite ballSprite;

    public GameCanvas() {
        super();
        initComponent();
        startGame();
    }

    private void initComponent() {
        initBallSprite();
    }

    public void initBallSprite() {
        ballSprite = new BallSprite();
        ballSprite.initSprite();
        getSpriteAry().add(ballSprite);
    }
}
