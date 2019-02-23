/*
 * ToRightBall.java - A class that extends BallSprite for defining a
 * ball sprite animated to the direction of right.
 */
package symball;

public class ToRightBall extends BallSprite {

    public void ToRightBall() {
        initSprite();
    }

    @Override
    public void updateSprite() {
        setX(getX() + 2);
        if (getX() >= Consts.CV_WIDTH) {
            setX(0);
        }
    }
}
