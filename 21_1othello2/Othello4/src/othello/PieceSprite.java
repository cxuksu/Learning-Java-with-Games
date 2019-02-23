/*
 * PieceSprite.java - A class that loads original six white images and six 
 * black images. And then, it makes them tranparent and displays them. When
 * flip starts, its updateSprite() method animates the images.
 */
package othello;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class PieceSprite extends AbsSprite2D {

    private BufferedImage[] pieces;
    private final int MAXPIECE = 12;
    private int transparentColor = -1;
    private boolean transparent = true;
    // for flip a piece stored in array pieces[]
    // set 0 and 6 will flip black to white
    // set 6 and 12 (0) will flip white to black
    private int imageIdx = 0;
    private int numTurns = 0;
    private boolean flipStop;
    private GameCanvas gameCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceSprite() {
        super();
        pieces = new BufferedImage[MAXPIECE];
        initSprite();
    }

    @Override
    public void initSprite() {
        loadImages();
        flipStop = true;
        setActive(true);
        setVisible(true);
    }

    // To load images with transparent background color into the array pieces.
    private void loadImages() {
        for (int i = 0; i < MAXPIECE; i++) {
            pieces[i] = getBufferedImage(i + 1);
        }
    }

    public BufferedImage getBufferedImage(int i) {
        BufferedImage transImage = null;
        try {
            BufferedImage oriImage = ImageIO.read(getClass().getResource(
                    "../images/piece" + i + ".PNG"));
            transImage = makeTransparency(oriImage, oriImage.getWidth(),
                    oriImage.getHeight());
        } catch (Exception ex) {
        }
        return transImage;
    }

    // Makes background transparent. (1) get the color of the pixel at the
    // top-left corner from the original image imageOri, which is the background
    // color transparentColor. (2) get the color of all pixels one-by-one;
    // if the color of the pixel is the same as the color transparentColor
    // and if it is required to make transparency, set the Alpha bits at
    // the same position in the altered image bImage with 0; otherwise, leave it
    // as is. (3) if the color of the pixel is not same as the transparentColor, 
    // leave it as is. Thus, all pixels with the background color will be 
    // switched to be transparent. Finally, return The altered image bImage.
    public BufferedImage makeTransparency(BufferedImage imageOri,
            int width, int height) {
        BufferedImage bImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        transparentColor = imageOri.getRGB(0, 0);

        for (int x = 0; x < imageOri.getWidth(); x++) {
            for (int y = 0; y < imageOri.getHeight(); y++) {
                if (imageOri.getRGB(x, y) == getTransparentColor()) {
                    if (isTransparent()) {
                        // bit-wise AND with the 0x00FFFFFF to make Alpha as 0
                        bImage.setRGB(x, y, (imageOri.getRGB(x, y) & 0x00FFFFFF));
                    } else {
                        bImage.setRGB(x, y, imageOri.getRGB(x, y));
                    }
                } else {
                    bImage.setRGB(x, y, imageOri.getRGB(x, y));
                }
            }
        }
        return bImage;
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        // it takes care of both "no flip" paint (in playerBoard) and "flip" paint
        // "no flip" paint needs the color. "flip" paint needs changing imageIdx
        if (flipStop) { // no flip, check the color then set imageIdx
            if (getColor() == Consts.WHITEP) {
                setImageIdx(6); // image with index 6 is a while piece
            } else if (getColor().equals(Consts.BLACKP)) {
                setImageIdx(0); // image with index 0 is a black piece
            }
        }
        // during flip, imageIdx is dynamically changed by updateSprite()
        g2d.drawImage(pieces[imageIdx], getX(), getY(), null);
    }

    @Override
    public void updateSprite() {
        if (flipStop == false) { // start flip issued by rules
            AllClips.getClips(Consts.FLIP).playNorewind(); // play exactly once
            imageIdx = (imageIdx + 1) % numTurns;
            if ((numTurns == 6) && (imageIdx == 0)) { // flip black to white
                imageIdx = 6;
                setFlipStop(true); // stop flip issued by rules
            } else if ((numTurns == 12) && (imageIdx == 0)) { // flip white to black
                setFlipStop(true);
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

    public int getImageIdx() {
        return imageIdx;
    }
}
