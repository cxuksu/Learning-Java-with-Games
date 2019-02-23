/*
 * BrickSprite.java - A class that defines one brick sprite, which is an
 * image.
 */
package breakout;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author cxu
 */
public class BrickSprite extends AbsSpriteImage {

    public BrickSprite() {
        super();
    }

    @Override
    public void initSprite() {
        // its (x, y) are set by the BrickGroup.java
        setWidth(Consts.BRICK_W);
        setHeight(Consts.BRICK_H);
        setActive(true);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
        // bricks are static.
    }
    
    // The algorithm used for checking whether the ball hits bricks
    // is based on the touch point of ball's left, right, top,
    // or bottom.  This checking is more accurate than checking
    // two rectangles collision.
    public String hitBrick(Rectangle ballRect) {
        String hitPosn = "noHit";
        Point ballLeft = new Point((int) ballRect.getX(),
                (int) (ballRect.getY() + ballRect.getHeight()/2));
        Point ballRight = new Point((int) (ballRect.getX() + ballRect.getWidth()),
                (int) (ballRect.getY() + ballRect.getHeight()/2));
        Point ballTop = new Point((int) (ballRect.getX() + ballRect.getWidth()/2),
                (int) (ballRect.getY()));
        Point ballBottom = new Point((int) (ballRect.getX() + ballRect.getWidth()/2),
                (int) (ballRect.getY() + ballRect.getHeight()));
        if (isVisible()) {
            Rectangle brickRect = new Rectangle(getX(), getY(), getWidth(),
                    getHeight());
            if (brickRect.contains(ballLeft)) {
                setVisible(false);
                hitPosn = "hitX";
            } else if (brickRect.contains(ballRight)) {
                setVisible(false);
                hitPosn = "hitX";
            } else if (brickRect.contains(ballTop)) {
                setVisible(false);
                hitPosn = "hitY";
            } else if (brickRect.contains(ballBottom)) {
                setVisible(false);
                hitPosn = "hitY";
            }
        }
        return hitPosn;
    }
}
