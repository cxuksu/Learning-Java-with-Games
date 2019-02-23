/*
 * AbsSprite.java - An abstract class as the root class of the sprite 
 * inheritance hierarchy.
 */
package symball;

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
