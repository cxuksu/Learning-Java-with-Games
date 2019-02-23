/*
 * PieceSprite.java - A class that loads original six white images and six 
 * black images. And then, it makes them transparent and displays them.
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
    private int imageIdx = 0;
    private int numTurns = 0;
    private boolean flipStop = true;
    private GameCanvas gameCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceSprite() {
        pieces = new BufferedImage[MAXPIECE];
        initSprite();
    }

    @Override
    public void initSprite() {
        loadImages();
    }

    // To load images with transparent background color into the array pieces.
    private void loadImages() {
        for (int i = 0; i < MAXPIECE; i++) {
            pieces[i] = getBufferedImage(i + 1);
        }
        setActive(false);
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
        int oriX = getX() + 60;
        setY(getY() + 160);
        for (int i = 0; i < MAXPIECE; i++) {
            if (i % 6 == 0) {
                setY(getY() + 60);
                setX(oriX);
            }
            g2d.drawImage(pieces[i], getX(), getY(), null);
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
