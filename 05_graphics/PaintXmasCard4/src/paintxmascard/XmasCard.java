/*
 * XmasCard.java - A canvas for painting a Christmas card.
 */
package paintxmascard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class XmasCard extends JPanel {

    private String recName;
    private String merryXmas;
    private String signature;
    private ReadFile readFile;
    private ReadImage readImage;
    private int x, y;
    // due to CV_WINDTH is doubled, left side of MAXX_DIS is changed
    private int leftMAXX_DIS;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public XmasCard() {
        leftMAXX_DIS = Consts.CV_WIDTH / 2 - Consts.MARGIN;
        inputRecName();
        inputMerryXmas();
        inputSignature();
        inputImage();
        repaint();
    }

    private void inputRecName() {
        recName = JOptionPane.showInputDialog("Enter recipient's name: ");
    }

    private void inputMerryXmas() {
        readFile = new ReadFile();
        readFile.selectFile();
        merryXmas = readFile.readContent();
    }

    private void inputSignature() {
        signature = JOptionPane.showInputDialog("Enter sender's name: ");
    }

    private void inputImage() {
        readImage = new ReadImage();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        paintRecName(g);
        paintMerryXmas(g);
        paintSignature(g);
        paintImage(g);
    }

    private void paintRecName(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();
        y = Consts.MINY_DIS + charHeight;
        g.drawString(recName, Consts.MINX_DIS, y);
    }

    private void paintMerryXmas(Graphics g) {
        int displayWidth = leftMAXX_DIS - Consts.MINX_DIS;
        g.setFont(new Font("Edwardian Script ITC", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();

        // concatenate words into a subStr and paint it centered
        StringTokenizer st
                = new StringTokenizer(merryXmas, " ");
        String subStr = "";
        y = y + charHeight;
        while (st.hasMoreTokens()) {
            String nextT = st.nextToken();
            int subStrW = fm.stringWidth(subStr) + fm.stringWidth(nextT);
            if (subStrW <= displayWidth) {
                subStr = subStr + nextT + " ";
            } else {
                y = y + Consts.NEXTLINE;
                int subStrWidth = fm.stringWidth(subStr);
                int midW = subStrWidth / 2;
                int startX = (leftMAXX_DIS - Consts.MINX_DIS) / 2 - midW;
                g.drawString(subStr, Consts.MINX_DIS + startX, y);
                subStr = nextT + " ";
            }
        }
        // for painting the last piece if any
        if (!subStr.equals(" ")) {
            y = y + Consts.NEXTLINE;
            int subStrWidth = fm.stringWidth(subStr);
            int midW = subStrWidth / 2;
            int startX = (leftMAXX_DIS - Consts.MINX_DIS) / 2 - midW;
            g.drawString(subStr, Consts.MINX_DIS + startX, y);
        }
    }

    private void paintSignature(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int startX = leftMAXX_DIS - fm.stringWidth(signature);
        int charHeight = fm.getAscent();
        y = y + Consts.NEXTLINE + charHeight;
        g.drawString(signature, startX, y);
    }

    private void paintImage(Graphics g) {
        BufferedImage img = readImage.getImage();
        int imgMinx = Consts.CV_WIDTH / 2 + Consts.MARGIN;
        int imgMiny = Consts.MINY_DIS;
        int imgMaxx = Consts.MAXX - Consts.MARGIN;
        int imgMaxy = Consts.MAXY_DIS;
        int imgW = imgMaxx - imgMinx;
        int imgH = imgMaxy - imgMiny;
        g.drawImage(img, imgMinx, imgMiny, imgW, imgH, null);
    }
}
