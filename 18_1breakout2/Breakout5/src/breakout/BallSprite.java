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
        initXY();
        setWidth(2 * Consts.BALL_RADIUS);
        setHeight(2 * Consts.BALL_RADIUS);
        setDx(1);
        setDy(2);
        setActive(false);
        setVisible(true);
    }

    public void initXY() {
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.BALL_RADIUS);
        setY(Consts.MAXY - Consts.PADDLE_INIT_Y - 2 * Consts.BALL_RADIUS);
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
        if ((getX() <= 0) // hits left and right edges
                || ((getX() + 2 * Consts.BALL_RADIUS) >= Consts.MAXX)) {
            setDx(-getDx());
            AllClips.getClips(Consts.HIT_EDGE).play();
        }
        if (getY() <= 0) { // hits top edge
            setDy(-getDy());
            AllClips.getClips(Consts.HIT_EDGE).play();
        }
        if ((getY() + 2 * Consts.BALL_RADIUS) >= Consts.MAXY) { // ball lost
            setActive(false);
            setVisible(true);
            initXY();
            AllClips.getClips(Consts.BALL_LOST).play();
            Point initP = new Point(getX(), getY());
            fireInitLocation(initP);
        }
    }

    public void fireNewLocation(Point p) {
        Point oldLoc = new Point(0, 0);
        Point newLoc = p;
        pChange.firePropertyChange("newP", oldLoc, newLoc);
    }

    public void fireInitLocation(Point p) {
        Point oldLoc = new Point(0, 0);
        Point newLoc = p;
        pChange.firePropertyChange("initP", oldLoc, newLoc);
    }

    public void hitBrickResetVelocity(String dir) {
        if (dir.equals("hitX")) {
            setDx(-getDx());
        } else if (dir.equals("hitY")) {
            setDy(-getDy());
        }
        AllClips.getClips(Consts.HIT_BRICK).play();
    }

    public void hitPaddleResetVelocity(String dir) {
        if (dir.equals("hitYL")) {
            if (getDx() >= 0) { // fly from left to right
                // negate both vx and vy
                setDx(-getDx());
                setDy(-getDy());
            } else { // fly from right to left
                // negate vy
                setDx(getDx());
                setDy(-getDy());
            }
        } else if (dir.equals("hitYR")) {
            if (getDx() >= 0) { // fly from left to right
                // negate vy
                setDx(getDx());
                setDy(-getDy());
            } else { // fly from right to left
                // negate both vx and vy
                setDx(-getDx());
                setDy(-getDy());
            }
        } else if (dir.equals("hitXR")) {
            // negate both vx and vy
            setDx(-getDx());
            setDy(-getDy());
        } else if (dir.equals("hitXL")) {
            // negate both vx and vy
            setDx(-getDx());
            setDy(-getDy());
        } else { // noHit
            // keep them the same
            setDx(getDx());
            setDy(getDy());
        }
        AllClips.getClips(Consts.HIT_PADDLE).play();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
