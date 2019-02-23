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
    private double zoomRatio;
    private int imgWidth;
    private int imgHeight;
    private int xCoor, yCoor;
    private int boardW;
    private int boardH;
    
    public ShowBoard() {
        boardW = Consts.CV_WIDTH - 2 * Consts.BUTTON_W;
        boardH = Consts.CV_HEIGHT - Consts.TOP_BAR_HEIGHT;
        this.setPreferredSize(new Dimension(boardW, boardH));
        readImage = new ReadImage();
        theShowImg = readImage.getImage();
        calcPosition();
    }
    
    private void calcPosition() { // zoom the image to fit in the board
        if (theShowImg.getWidth() >= theShowImg.getHeight()) {
            zoomRatio = (boardW * 1.0) / theShowImg.getWidth();
            imgWidth = boardW;
            imgHeight = (int)(theShowImg.getHeight() * zoomRatio);
            xCoor = Consts.BUTTON_W; // x starting point
            yCoor = boardH / 2 - imgHeight / 2; // y starting point
        } else { // if height > width
            zoomRatio = (boardH * 1.0) / theShowImg.getHeight();
            imgHeight = boardH;
            imgWidth = (int)(theShowImg.getWidth() * zoomRatio);
            yCoor = 0; // y starting point
            xCoor = boardW / 2 - imgWidth / 2; // x starting point
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(theShowImg, xCoor, yCoor, imgWidth, imgHeight, this);
    }
}
