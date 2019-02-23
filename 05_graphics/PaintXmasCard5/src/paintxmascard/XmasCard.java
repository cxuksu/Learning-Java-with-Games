/*
 * XmasCard.java -- a canvas for painting a Christmas card
 */
package paintxmascard;

import java.awt.Color;
import java.awt.Dimension;
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
    private ReadFile rFile;

    private Dimension d;
    private int y;
    private int minxDis, maxxDis, minyDis, maxyDis;
    private int halfWidth;

    private BufferedImage img;
    private ReadImage rImage;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public XmasCard() {
        inputRecName();
        inputMerryXmas();
        inputSignature();
        inputImage();
        repaint();
    }

    public void setDimension(Dimension d) {
        this.d = d;
        halfWidth = d.width / 2;
        setDisplayArea();
    }

    private void setDisplayArea() {
        int minx = 0;
        int maxx = halfWidth - 2 * Consts.EDGE_WIDTH;
        int miny = 0;
        int maxy = d.height - Consts.TOP_BAR_HEIGHT - 2 * Consts.EDGE_WIDTH;
        minxDis = minx + Consts.MARGIN;
        maxxDis = maxx - Consts.MARGIN;
        minyDis = miny + Consts.MARGIN;
        maxyDis = maxy - Consts.MARGIN;
    }

    private void inputRecName() {
        recName = JOptionPane.showInputDialog("Enter recipient's name: ");
    }

    private void inputMerryXmas() {
        rFile = new ReadFile();
        rFile.selectFile();
        merryXmas = rFile.readContent();
    }

    private void inputSignature() {
        signature = JOptionPane.showInputDialog("Enter sender's name: ");
    }

    private void inputImage() {
        rImage = new ReadImage();
        img = rImage.getImage();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        paintRecName(g);
        paintMerryXmas(g);
        paintSignature(g);
        paintImage(g);
        paintDecoration(g);
    }

    private void paintRecName(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();
        y = minyDis + charHeight;
        g.drawString(recName, minxDis, y);
    }

    private void paintMerryXmas(Graphics g) {
        int displayWidth = maxxDis - minxDis;
        g.setFont(new Font("Edwardian Script ITC", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();

        // concatenate words into a subStr
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
                int startX = (maxxDis - minxDis) / 2 - midW;
                g.drawString(subStr, minxDis + startX, y);
                subStr = nextT + " ";
            }
        }
        // for painting the last piece if any
        if (!subStr.equals(" ")) {
            y = y + Consts.NEXTLINE;
            int subStrWidth = fm.stringWidth(subStr);
            int midW = subStrWidth / 2;
            int startX = (maxxDis - minxDis) / 2 - midW;
            g.drawString(subStr, minxDis + startX, y);
        }
    }

    private void paintSignature(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int startX = maxxDis - fm.stringWidth(signature);
        int charHeight = fm.getAscent();
        y = y + Consts.NEXTLINE + charHeight;
        g.drawString(signature, startX, y);
    }

    private void paintImage(Graphics g) {
        int imgMinx = halfWidth + Consts.MARGIN;
        int imgMiny = minyDis;
        int imgMaxx = d.width - 2 * Consts.EDGE_WIDTH - Consts.MARGIN;
        int imgMaxy = maxyDis;
        int imgW = imgMaxx - imgMinx;
        int imgH = imgMaxy - imgMiny;
        g.drawImage(img, imgMinx, imgMiny, imgW, imgH, null);
    }

    // draw a dash for a unit and calculate the value of x for next drawing
    private int aDash(Graphics g, int x, int dWidth, int i,
            int numUnits, int midY) {
        x = x + dWidth;
        if (i < numUnits - 1) { // don't draw a dash for the last unit
            g.drawLine(x, midY, x + dWidth / 2, midY);
            x = x + dWidth / 2;
        }
        return x;
    }

    private void paintDecoration(Graphics g) {
        int dWidth = 10; // width of a unit in the decoration
        int dHeight = 10; // height of a unit in the decoration
        int dMargin = 10;
        int x = 5; // starting x
        int locY = dMargin; // starting y
        int midY = locY + dHeight / 2; // y coordinate of the small dash
        int lengthDash = dWidth / 2; // length of one dash
        int numUnits
                = ((maxxDis + Consts.MARGIN) - (minxDis - Consts.MARGIN))
                / (dWidth + lengthDash);

        g.setColor(Color.blue);
        // top decoration
        y = locY;
        for (int i = 0; i < numUnits; i++) {
            if (i % 2 == 0) { // 0, 2, 4, ... are rectangles
                g.drawRect(x, y, dWidth, dHeight);
                x = aDash(g, x, dWidth, i, numUnits, midY);
            } else { // 1, 3, 5, ... are circles
                g.drawOval(x, y, dWidth, dHeight);
                x = aDash(g, x, dWidth, i, numUnits, midY);
            }
        }
        // bottom decoration
        x = 5;
        y = maxyDis + Consts.MARGIN - dMargin - dHeight;
        midY = y + dHeight / 2;
        for (int i = 0; i < numUnits; i++) {
            if (i % 2 == 0) { // 0, 2, 4 ... are circles
                g.drawOval(x, y, dWidth, dHeight);
                x = aDash(g, x, dWidth, i, numUnits, midY);
            } else { // 1, 3, 5 ... are rectangles
                g.drawRect(x, y, dWidth, dHeight);
                x = aDash(g, x, dWidth, i, numUnits, midY);
            }
        }
    }
}
