/*
 * ImageSlider.java - The main class of an image slider.
 */
package imageslider;

import javax.swing.JFrame;

/**
 *
 * @author cxu
 */
public class ImageSlider extends JFrame {

    public ImageSlider() {
        setTitle("Image Slider");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        SliderPanel sp = new SliderPanel();
        add(sp);
        
        setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ImageSlider();
    }
}
