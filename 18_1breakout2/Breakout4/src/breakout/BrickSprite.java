/*
 * BrickSprite.java - A class that defines one brick sprite, which is an
 * image.
 */
package breakout;

/**
 *
 * @author cxu
 */
public class BrickSprite extends AbsSpriteImage {

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BrickSprite() {
        super();
        initSprite();
        // some parameters will be assigned by the method
        // initComponent() defined in the class GameCanvas in order to 
        // arrange all bricks in a 2D array format.
    }

    @Override
    public void initSprite() {
        setX(0); // unknown at this moment
        setY(0); // unknown at this moment
        setWidth(Consts.BRICK_W);
        setHeight(Consts.BRICK_H);
        setActive(false); // a static sprite
        setVisible(true);
    }

    @Override
    public void updateSprite() {
        // at this moment, bricks are static.
    }
}

