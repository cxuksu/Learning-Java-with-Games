/*
 * ReadImage.java -- a class to read a set of images into the project
 */
package imageslider;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class ReadImage {

    private BufferedImage img;
    private ArrayList<BufferedImage> sceneryList;
    private Image rightArrow;
    private Image leftArrow;

    public ReadImage() {
        sceneryList = new ArrayList<>();
        initImage();
    }

    private void initImage() {
        try {
            for (int idx = 0; idx < 6; idx++) {
                img = ImageIO.read(getClass().getResource(
                        "../images/scenery" + (idx + 1) + ".jpeg"));
                sceneryList.add(img);
            }
            rightArrow = ImageIO.read(getClass().getResource(
                    "../images/rightArrow.png"));
            leftArrow = ImageIO.read(getClass().getResource(
                    "../images/leftArrow.png"));
        } catch (IOException ex) {
        }
    }

    public ArrayList<BufferedImage> getSceneryList() {
        return sceneryList;
    }

    public BufferedImage getImage(int idx) {
        return sceneryList.get(idx);
    }

    public Image getRightArrow() {
        return rightArrow;
    }

    public Image getLeftArrow() {
        return leftArrow;
    }
}
