/*
 * AbsSpriteImage.java - An abstract class that specify an image.
 */
package gamestru;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author cxu
 */
public abstract class AbsSpriteImage extends AbsSprite {

    private BufferedImage anImage;
    private int x; // top-left corner
    private int y; // top-left corner
    private int width;
    private int height;
    private int dx;
    private int dy;

    public AbsSpriteImage() {
        super();
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.drawImage(anImage, getX(), getY(), null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
