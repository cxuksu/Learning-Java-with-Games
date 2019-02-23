/*
 * ReadImage.java -- a class to read an image into the project
 */
package paintxmascard;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author cxu
 */
public class ReadImage {

    private BufferedImage img;

    public ReadImage() {
        try {
            img = ImageIO.read(getClass().getResource(
                    "../images/xmasImg4.jpeg"));
        } catch (IOException ex) {
        }
    }

    public BufferedImage getImage() {
        return img;
    }
}
