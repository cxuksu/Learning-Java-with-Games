/*
 * Board.java - A class defines a board for displaying the sentence.
 */
package wheelvideo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.StringTokenizer;
import javax.swing.JPanel;

/**
 *
 * @author cxu
 */
public class Board extends JPanel {

    private String sentenceStr;
    private int sentenceLen;
    private ReadFile readFile;

    private int cx, cy; // center point (x, y)
    private char[] currAry;
    private int px, py; // paint block starting point (x, y)
    private int bWidth, bHeight; // width and height of each char
    private int sx, sy; // paint string starting point (x, y)
    private double marginPerc = 0.98; // making margins
    private boolean hasNextLine = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Board() {
        this.setPreferredSize(new Dimension(Consts.CV_WIDTH, Consts.BOARD_H));
        initSentence();
        initBoard();
    }

    private void initSentence() {
        readFile = new ReadFile();
        readFile.selectFile();
        sentenceStr = readFile.readContent();
        sentenceStr = sentenceStr.toUpperCase();
        sentenceLen = sentenceStr.length();
    }

    private void initBoard() {
        cx = (int) ((Consts.MAXX_DIS - Consts.MINX_DIS) / 2);
        cy = (int) (Consts.BOARD_H / 2);
        py = 10;
        bWidth = 30;
        bHeight = 40;
        sx = px + 4;
        sy = py + 30;
        currAry = new char[sentenceLen];
        initCharAry(); // init it with empty char
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Stroke solid = new BasicStroke(5.0f);
        g2d.setStroke(solid);

        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Times", Font.BOLD, 30));

        // the maximum number of characters can be displayed along the width
        int numCharDisp = (int) (((Consts.MAXX_DIS - Consts.MINX_DIS)
                * marginPerc) / bWidth);
        // concatenate words into a subStr and paint it centered
        String subStr = "";
        int numCharSubStr = subStr.length();
        int row = 0;
        int nextLineIdx = 0;
        StringTokenizer st
                = new StringTokenizer(sentenceStr, " ");
        while (st.hasMoreTokens()) {
            String nextT = st.nextToken();
            numCharSubStr = numCharSubStr + nextT.length();
            if (numCharSubStr <= numCharDisp) {
                subStr = subStr + nextT + " "; // add a blank between two words
                numCharSubStr += 1;
            } else {
                hasNextLine = true; // enter here means that the second line exist
                paintOneRow(g2d, subStr, row, nextLineIdx);

                // parameters for the next line
                nextLineIdx = subStr.length(); // index of the second line 
                // start the subStr in the next line
                subStr = nextT + " ";
                numCharSubStr = subStr.length();
                row++; // counting for number of lines
            }
        }
        // painting the last piece if any (after "while" finishes)
        // the last piece could be the first row or the second row
        if (!subStr.equals(" ")) {
            paintOneRow(g2d, subStr, row, nextLineIdx);
        }
    }

    public void paintOneRow(Graphics2D g2d, String subStr, int row, int nextLineIdx) {
        // paint the panel: px = (center x - half of subStr width)
        // "- 1" is to minus the added blank character
        px = cx - (int) (((subStr.length() - 1) / 2) * bWidth);
        sx = px + 4;

        if (!hasNextLine) { // if only has one line, it should be at the middle
            py = cy - (int) (bHeight / 2);
        }
        sy = py + 30; // the coordinates of the bottom-left point
        paintEmptyPanel(g2d, subStr, row);
        paintSubStr(g2d, subStr, row, nextLineIdx);
    }

    public void paintEmptyPanel(Graphics2D g2d, String subStr, int row) {
        for (int i = 0; i < subStr.length() - 1; i++) {
            g2d.drawRect(px + i * bWidth, py + row * bHeight,
                    bWidth, bHeight);
        }
    }

    public void paintSubStr(Graphics2D g2d, String subStr, int row, int nextLineIdx) {
        for (int k = 0; k < subStr.length() - 1; k++) {
            int idx = k + nextLineIdx; // comes from the first line
            if (currAry[idx] != ' ') {
                g2d.drawString(Character.toString(currAry[idx]),
                        sx + k * bWidth, sy + row * bHeight);
            }
        }
    }

    public void initCharAry() {
        for (int i = 0; i < sentenceLen; i++) {
            currAry[i] = ' ';
        }
    }
}
