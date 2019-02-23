/*
 * ShowBoard.java - A class defines the show panel for showing an image.
 */
package imageslider;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class ShowBoard extends JPanel {

    private ReadImage readImage;
    private BufferedImage theShowImg;
    private int boardW;
    private int boardH;
    private double zoomRatio;
    private int imgWidth;
    private int imgHeight;
    private int xCoor, yCoor;
    private int imgIdx;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ShowBoard() {
        boardW = Consts.CV_WIDTH - 2 * Consts.BUTTON_W;
        boardH = Consts.CV_HEIGHT - Consts.TOP_BAR_HEIGHT;
        this.setPreferredSize(new Dimension(boardW, boardH));
    }

    public void initShowImg() {
        // for getting the first image; one time
        imgIdx = readImage.getSceneryList().size() / 2; // the middle index
    }

    public void findShowImg() {
        theShowImg = readImage.getImage(imgIdx);
        calcPosition();
    }

    public void calcPosition() {
        // zoom the image to fit in the board
        if (theShowImg.getWidth() >= theShowImg.getHeight()) {
            zoomRatio = (boardW * 1.0) / theShowImg.getWidth();
            imgWidth = boardW;
            imgHeight = (int) (theShowImg.getHeight() * zoomRatio);
            xCoor = 0; // x starting point relative to the board
            yCoor = boardH / 2 - imgHeight / 2; // y starting point
        } else { // if height > width
            zoomRatio = (boardH * 1.0) / theShowImg.getHeight();
            imgHeight = boardH;
            imgWidth = (int) (theShowImg.getWidth() * zoomRatio);
            yCoor = 0;
            xCoor = boardW / 2 - imgWidth / 2;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(theShowImg, xCoor, yCoor, imgWidth, imgHeight, this);
    }

    public void setReadImage(ReadImage readImage) {
        this.readImage = readImage;
    }

    public int getImgIdx() {
        return imgIdx;
    }

    public void setImgIdx(int imgIdx) {
        this.imgIdx = imgIdx;
    }
}
