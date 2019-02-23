/*
 * BallSprite.java - A class that defines an image ball sprite
 */
package trysuspendthread;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.imageio.ImageIO;

public class BallSprite extends AbsImageSprite {
    private BufferedImage theBall;
    private PropertyChangeSupport pChange;
    private int count = 0;
    private SuspendRequestor suspender;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BallSprite() {
        super();
        pChange = new PropertyChangeSupport(this);
        try {
            theBall = ImageIO.read(getClass().getResource("../images/ball.png"));
        } catch(Exception ex) {
        }
        setAnImage(theBall);
        initXY();
        setWidth(2 * Consts.RADIUS);
        setHeight(2 * Consts.RADIUS);
        setDx(1);
        setDy(2);
        setActive(true); // force it to be static at the beginning
        setVisible(true);
        suspender = new SuspendRequestor();
    }

    // the initial coordinates (x, y) is used often 
    public void initXY() {
        setLocx(Consts.frameW/2 - Consts.RADIUS);
        setLocy(Consts.frameH - Consts.PADDLE_INIT_Y - Consts.PADDLE_H);
    }
    
    @Override
    public void updateSprite() {
        if (isActive()) {
            setLocx(getLocx() + getDx());
            setLocy(getLocy() + getDy());
        }
        checkEnv();
        // send the new position to its listener for checking whether
        // it hits bricks or not
        //fireNewLocation(new Point(getLocx(), getLocy()));
    }
    
    public void checkEnv() {
        if ((getLocx() <= 0) || // bouncing on left and right edges
                ((getLocx()+2*Consts.RADIUS) >= Consts.frameW)) {
            setDx(-getDx());
            //AllClips.playClip(0, false);
        }
        if (getLocy() <= 0) { // bouncing on top edge
            setDy(-getDy());
            //AllClips.playClip(0, false);
        }
        if ((getLocy()) >= Consts.frameH-35) {
            count++;
            System.out.println("count = " + count);
            if (count >= 5) {
                System.out.println("count = " + count);
               suspender.requestSuspend();
               //pChange.firePropertyChange("pauseBall", false, true);
            }
            setDy(-getDy());
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
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
    
}
