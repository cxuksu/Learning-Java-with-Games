/*
 * ToLeftBall.java - A class that extends BallSprite and defines a ball
 * animted to the direction of left.
 */

package symball;

public class ToLeftBall extends BallSprite {
    
    public void ToLeftBall() {
        initSprite();
    }

    @Override
    public void updateSprite() {
        setX(getX() - 2);
        if (getX() <= 0) {
            setX(Consts.CV_WIDTH);
        }
    }
}
