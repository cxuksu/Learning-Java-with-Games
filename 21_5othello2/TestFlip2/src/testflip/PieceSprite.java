/*
 * PieceSprite.java - A class that loads original six white images and six 
 * black images. And then, it makes them transparent and displays them. When
 * flip starts, its updateSprite() method animates the images.
 */
package testflip;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PieceSprite extends AbsSpriteImage {

    private BufferedImage[] pieces;
    private final int MAXPIECE = 12;
    private int transparentColor = -1;
    private boolean transparent = true;
    // for flip a piece
    // 0 and 6 will flip black to white
    // 6 and 12 will flip white to black
    private int imageIdx = 0;
    private int numTurns = 6;
    private boolean flipStop;
    private GameCanvas gameCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceSprite() {
        pieces = new BufferedImage[MAXPIECE];
        initSprite();
    }

    @Override
    public void initSprite() {
        loadImages();
        flipStop = false; // to allow flip
    }

    // To load images with transparent background color into the array pieces.
    private void loadImages() {
        for (int i = 0; i < MAXPIECE; i++) {
            pieces[i] = getBufferedImage(i + 1);
        }
        setActive(true);
        setVisible(true);
    }

    public BufferedImage getBufferedImage(int i) {
        BufferedImage bImage = null;
        try {
            BufferedImage bTempImage = ImageIO.read(getClass().getResource(
                    "../images/piece" + i + ".PNG"));
            bImage = new BufferedImage(bTempImage.getWidth(),
                    bTempImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            bImage = makeTransparent(bImage, bTempImage);
        } catch (Exception ex) {
        }
        return bImage;
    }

    // Makes background transparent. (1) get the color of the pixel at the
    // top-left corner (0, 0) from the original image bImageTemp, which is the
    // background color. Name it as transparentColor. (2) get the color of all
    // other pixels one-by-one; if the color of the other pixel is the same as
    // the transparentColor and if it is required to be transparency, set the
    // pixel at the same position in the processed image bImage as transparent.
    // (3) if the color of the other pixel is not same as the transparentColor,
    // leave it as is. Thus, all pixels with the background color will be 
    // switched to be transparent. Finally, return the processed image bImage.
    public BufferedImage makeTransparent(BufferedImage bImage,
            BufferedImage bImageTemp) {
        transparentColor = bImageTemp.getRGB(0, 0);

        for (int x = 0; x < bImageTemp.getWidth(); x++) {
            for (int y = 0; y < bImageTemp.getHeight(); y++) {
                if (bImageTemp.getRGB(x, y) == getTransparentColor()) {
                    if (isTransparent()) {
                        bImage.setRGB(x, y, (bImageTemp.getRGB(x, y) & 0x00FFFFFF));
                    } else {
                        bImage.setRGB(x, y, bImageTemp.getRGB(x, y));
                    }
                } else {
                    bImage.setRGB(x, y, bImageTemp.getRGB(x, y));
                }
            }
        }
        return bImage;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        if (isVisible()) {
            g2d.drawImage(pieces[imageIdx], getX(), getY(), null);
        }
    }

    @Override
    public void updateSprite() { // animate images to mimic flipping a piece
        if (flipStop == false) { // start flip
            imageIdx = (imageIdx + 1) % numTurns;
            if ((numTurns == 6) && (imageIdx == 0)) { // flip black to white
                imageIdx = 6;
                numTurns = 12;
                //setFlipStop(true); // stop flip
            } else if ((numTurns == 12) && (imageIdx == 0)) { // flip white to black
                imageIdx = 0;
                numTurns = 6;
                //setFlipStop(true);
            }
        }
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

    public void setImageIdx(int idx) {
        this.imageIdx = idx;
    }

    public void setNumTurns(int numTurns) {
        this.numTurns = numTurns;
    }

    public void setFlipStop(boolean b) {
        this.flipStop = b;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
