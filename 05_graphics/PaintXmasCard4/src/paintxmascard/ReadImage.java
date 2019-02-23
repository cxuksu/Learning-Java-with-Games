/*
 * ReadImage.java - A class to read an image into the project.
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
                    "../images/Winter.jpg"));
        } catch (IOException ex) {
        }
    }
    
    public BufferedImage getImage() {
        return img;
    }
}
