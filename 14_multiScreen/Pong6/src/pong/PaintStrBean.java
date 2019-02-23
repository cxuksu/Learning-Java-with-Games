/*
 * PaintStrBean.java - A bean to paint a string on the center position of
 * a painting area along the x-coordinate.
 */
package pong;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.StringTokenizer;

/**
 *
 * @author cxu
 */
public class PaintStrBean {

    private int paintAreaMinX;
    private int paintAreaMaxX;
    private int paintY;

    public PaintStrBean() {
    }

    public void paintLongString(Graphics2D g2d, String paintStr) {
        // for displaying the description, Consts.java sets MARGIN = 20. 
        // Thus, MAXX_DIS and MINX_DIS are used
        int areaWidth = paintAreaMaxX - paintAreaMinX;

        FontMetrics fm = g2d.getFontMetrics();
        int charHeight = fm.getAscent();

        // concatinate words into a subStr and paint it centerized
        StringTokenizer st
                = new StringTokenizer(paintStr, " ");
        String subStr = "";

        paintY = paintY + charHeight;
        while (st.hasMoreTokens()) {
            String nextT = st.nextToken();
            int subStrW = fm.stringWidth(subStr) + fm.stringWidth(nextT);
            if (subStrW <= areaWidth) {
                subStr = subStr + nextT + " ";
            } else {
                paintY = paintY + Consts.NEXTLINE;
                int subStrWidth = fm.stringWidth(subStr);
                int midW = subStrWidth / 2;
                int startX = areaWidth / 2 - midW;
                g2d.drawString(subStr, paintAreaMinX + startX, paintY);
                subStr = nextT + " ";
            }
        }
        // for painting the last piece if any
        if (!subStr.equals(" ")) {
            paintY = paintY + Consts.NEXTLINE;
            int subStrWidth = fm.stringWidth(subStr);
            int midW = subStrWidth / 2;
            int startX = areaWidth / 2 - midW;
            g2d.drawString(subStr, paintAreaMinX + startX, paintY);
        }
    }

    public void setPaintAreaMinX(int paintAreaMinX) {
        this.paintAreaMinX = paintAreaMinX;
    }

    public void setPaintAreaMaxX(int paintAreaMaxX) {
        this.paintAreaMaxX = paintAreaMaxX;
    }

    public int getPaintY() {
        return paintY;
    }

    public void setPaintY(int paintY) {
        this.paintY = paintY;
    }
}
