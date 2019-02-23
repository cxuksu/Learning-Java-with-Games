/*
 * ReadImage.java -- a class to read an image into the project
 */
package imageslider;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


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
