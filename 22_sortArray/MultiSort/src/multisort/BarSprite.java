/*
 * BarSprite.java - A class that defines a bar sprite.
 */
package multisort;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class BarSprite extends AbsSprite2D {

    private boolean moveRight;
    private boolean moveLeft;
    private int rightLimit;
    private int leftLimit;
    @SuppressWarnings("FieldMayBeFinal")
    private PropertyChangeSupport pChange;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BarSprite() {
        pChange = new PropertyChangeSupport(this);
        initSprite();
    }

    @Override
    public void initSprite() {
        setWidth(Consts.BARWIDTH);
        setHeight(Consts.INITBAR);
        setVisible(true);
        setActive(true);
        int rRed = (int) (Math.random() * 256);
        int rBlue = (int) (Math.random() * 256);
        int rGreen = (int) (Math.random() * 256);
        Color aColor = new Color(rRed, rBlue, rGreen);
        setColor(aColor);
    }

    @Override
    public void updateSprite() {
        if (isMoveRight()) {
            if (getX() < getRightLimit()) {
                setX(getX() + 1);
            } else {
                setMoveRight(false);
                fireUpdateDone();
            }
        } else if (isMoveLeft()) {
            if (getX() > getLeftLimit()) {
                setX(getX() - 1);
            } else {
                setMoveLeft(false);
            }
        }
    }

    public void fireUpdateDone() {
        pChange.firePropertyChange("updateDone", "-1", "1");
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public int getRightLimit() {
        return rightLimit;
    }

    public void setRightLimit(int rightLimit) {
        this.rightLimit = rightLimit;
    }

    public int getLeftLimit() {
        return leftLimit;
    }

    public void setLeftLimit(int leftLimit) {
        this.leftLimit = leftLimit;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
