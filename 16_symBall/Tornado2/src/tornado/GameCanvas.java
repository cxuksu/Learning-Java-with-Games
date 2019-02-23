/*
 * GameCanvas.java - A class defines the game canvas.
 */
package tornado;

import java.awt.Color;

/**
 *
 * @author cxu
 */
public class GameCanvas extends AbsGameCanvas {

    private BallSprite ballSprite;
    private RectSprite rectSprite;

    public GameCanvas() {
        super();
        initComponent();
    }

    private void initComponent() {
        getSpriteAry().clear();
        initSprite();
        startGame();
    }

    public void initSprite() {
        for (int section = 1; section <= 13; section++) {
            for (int j = 1; j <= 200; j++) {
                ballSprite = new BallSprite();
                rectSprite = new RectSprite();
                ballSprite.initSprite(section);
                rectSprite.initSprite(section);
                ballSprite.setColor(Color.LIGHT_GRAY);
                rectSprite.setColor(Color.LIGHT_GRAY);
                getSpriteAry().add(ballSprite);
                getSpriteAry().add(rectSprite);
            }
            for (int j = 1; j <= 700; j++) { 
                ballSprite = new BallSprite();
                rectSprite = new RectSprite();
                ballSprite.initSprite(section);
                rectSprite.initSprite(section);
                ballSprite.setColor(Color.GRAY);
                rectSprite.setColor(Color.GRAY);
                getSpriteAry().add(ballSprite);
                getSpriteAry().add(rectSprite);
            }
            for (int j = 1; j <= 1; j++) {
                ballSprite = new BallSprite();
                rectSprite = new RectSprite();
                ballSprite.initSprite(section);
                rectSprite.initSprite(section);
                ballSprite.setColor(Color.LIGHT_GRAY);
                rectSprite.setColor(Color.LIGHT_GRAY);
                getSpriteAry().add(ballSprite);
                getSpriteAry().add(rectSprite);
            }
        }
    }
}
