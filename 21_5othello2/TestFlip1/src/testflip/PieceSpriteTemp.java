/*
 * PieceSpriteTemp.java - A class loads six original white images and six original 
 * black images. And then displays them on the GUI.
 */
package testflip;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PieceSpriteTemp extends AbsSpriteImage {

    private BufferedImage[] oriPieces;
    private final int MAXPIECE = 12;
    private int transparentColor = -1;
    private boolean transparent = true;

    private GameCanvas gameCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceSpriteTemp() {
        oriPieces = new BufferedImage[MAXPIECE];
        initSprite();
    }

    @Override
    public void initSprite() {
        loadOriginalImages();
    }

    private void loadOriginalImages() {
        System.out.println("in original");
        BufferedImage bTempImage = null;
        for (int i = 0; i < MAXPIECE; i++) {
            try {
                bTempImage = ImageIO.read(getClass().getResource(
                        "../images/piece" + (i + 1) + ".PNG"));
            } catch (Exception ex) {
            }
            oriPieces[i] = bTempImage;
        }
        setActive(false);
        setVisible(true);
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        int oriX = getX() + 60;
        for (int i = 0; i < MAXPIECE; i++) {
            if (i % 6 == 0) {
                setY(getY() + 60);
                setX(oriX);
            }
            g2d.drawImage(oriPieces[i], getX(), getY(), null);
            setX(getX() + 60);
        }
        gameCanvas.stopGame();
    }

    @Override
    public void updateSprite() {
    }

    public int getTransparentColor() {
        return transparentColor;
    }

    public void setTransparentColor(int transparentColor) {
        this.transparentColor = transparentColor;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
