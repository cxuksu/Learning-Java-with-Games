/*
 * AbsImageSprite.java - An abstract class that specify an image.
 */
package trysuspendthread;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class AbsImageSprite extends AbsSprite {
    private BufferedImage anImage;
    private int locx; // top-left corner
    private int locy; // top-left corner
    private int width;
    private int height;
    private int dx, dy;
    
    public AbsImageSprite() {
    }
    
    // The method for painting an image is the same so that
    // paintSprite() for images can be implemented here
    @Override
    public void paintSprite(Graphics2D g2d) {
        if (isVisible()) {
            g2d.drawImage(anImage, getLocx(), getLocy(), null);
        }
    }
    
    public int getLocx() {
        return locx;
    }

    public void setLocx(int locx) {
        this.locx = locx;
    }

    public int getLocy() {
        return locy;
    }

    public void setLocy(int locy) {
        this.locy = locy;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public BufferedImage getAnImage() {
        return anImage;
    }

    public void setAnImage(BufferedImage anImage) {
        this.anImage = anImage;
    }
}
