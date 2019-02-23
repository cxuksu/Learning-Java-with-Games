/*
 * HelloBanner - A project that illustrates an animation of a string.
 */
package hellobanner;

import javax.swing.JFrame;

public class HelloBanner extends JFrame {

    private int frameW = 400;
    private int frameH = 100;

    public HelloBanner() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("A string benner");
        setSize(frameW, frameH);

        BannerCv cv = new BannerCv(frameW, frameH);
        add(cv);

        setVisible(true);
    }

    public static void main(String[] args) {
        new HelloBanner();
    }
}
