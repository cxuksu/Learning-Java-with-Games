/*
 * AbsSprite.java - A abstract class that specify the rool of the sprite
 * inheritance hierarchy.
 */
package trysuspendthread;

import java.awt.Graphics2D;

public abstract class AbsSprite {
    private boolean active;
    private boolean visible;
    private int priority;

    public abstract void updateSprite();
    public abstract void paintSprite(Graphics2D g2d);
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    // suspend the sprite
    public void suspend() {
        setVisible(false);
        setActive(false);
    }
    
    // restore the sprite
    public void restore() {
        setVisible(true);
        setActive(true);
    }
}
