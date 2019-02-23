/*
 * ArrowSprite.java - A class that defines an arrow that points to
 * the center position of the current barSprite.
 */
package quicksort;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class ArrowSprite extends AbsSprite2D {

    @SuppressWarnings("FieldMayBeFinal")
    private PropertyChangeSupport pChange;
    private boolean leftIdx;
    private boolean rightIdx;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ArrowSprite() {
        pChange = new PropertyChangeSupport(this);
        initSprite();
    }

    @Override
    public void initSprite() {
        setX(Consts.MARGIN + Consts.BARWIDTH / 2);
        setY(Consts.BASEY + Consts.INITBAR);
        setColor(Color.red);
        setVisible(true);
        setActive(true);
        leftIdx = false;
        rightIdx = false;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.drawLine(getX(), getY(), getX() - 5, getY() + 5);
        g2d.drawLine(getX() + 1, getY(), getX() - 5 + 1, getY() + 5);
        g2d.drawLine(getX() - 1, getY(), getX() - 5 - 1, getY() + 5);
        g2d.drawLine(getX(), getY(), getX() + 5, getY() + 5);
        g2d.drawLine(getX() + 1, getY(), getX() + 5 + 1, getY() + 5);
        g2d.drawLine(getX() - 1, getY(), getX() + 5 - 1, getY() + 5);
        g2d.drawLine(getX(), getY(), getX(), getY() + 15);
        g2d.drawLine(getX() + 1, getY(), getX() + 1, getY() + 15);
        g2d.drawLine(getX() - 1, getY(), getX() - 1, getY() + 15);
        if ((leftIdx) || (rightIdx)) {
            fireArrowDone();
            leftIdx = false;
            rightIdx = false;
        }
    }

    public void fireArrowDone() {
        pChange.firePropertyChange("arrowDone", "-1", "1");
    }

    @Override
    public void updateSprite() {
    }

    public void setLeftIdx(boolean leftIdx) {
        this.leftIdx = leftIdx;
    }

    public void setRightIdx(boolean rightIdx) {
        this.rightIdx = rightIdx;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
