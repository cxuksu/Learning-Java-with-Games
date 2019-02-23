/*
 * AbsSprite.java - A abstract class that specify the root of the sprite
 * inheritance hierarchy.
 */
package breakout;

/**
 *
 * @author cxu
 */
public abstract class AbsSprite {
    private boolean active;
    private boolean visible;
    private int priority;

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
}
