/*
 * BallSprite.java - A class that defines an image ball sprite
 */
package breakout;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class BallSprite extends AbsSpriteImage {

    private BufferedImage ballImg;
    private PropertyChangeSupport pChange;

    public BallSprite() {
        super();
        pChange = new PropertyChangeSupport(this);
    }

    @Override
    public void initSprite() {
        try {
            ballImg = ImageIO.read(getClass().getResource("../images/ball.png"));
        } catch (IOException ex) {
        }
        setAnImage(ballImg);
        setX((Consts.MAXX - Consts.MINX) / 2);
        setY(Consts.MAXY - 60);
        setWidth(2 * Consts.BALL_RADIUS);
        setHeight(2 * Consts.BALL_RADIUS);
        setDx(1);
        setDy(2);
        setActive(true);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
        setX(getX() + getDx());
        setY(getY() + getDy());
        checkEnv();
        // send the new position to its listener for checking whether
        // it hits bricks or not
        fireNewLocation(new Point(getX(), getY()));
    }

    // collides with the four edges to make a bouncing ball
    public void checkEnv() {
        if ((getX() <= 0)
                || ((getX() + 2 * Consts.BALL_RADIUS) >= Consts.MAXX)) {
            setDx(-getDx());
        }
        if ((getY() <= 0)
                || ((getY() + 2 * Consts.BALL_RADIUS) >= Consts.MAXY)) {
            setDy(-getDy());
        }
    }

    public void fireNewLocation(Point p) {
        Point oldLoc = new Point(0, 0);
        Point newLoc = p;
        pChange.firePropertyChange("newP", oldLoc, newLoc);
    }

    public void hitBrickResetVelocity(String dir) {
        if (dir.equals("hitX")) {
            setDx(-getDx());
        } else if (dir.equals("hitY")) {
            setDy(-getDy());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
