/*
 * XmasCard.java - A canvas for painting a Christmas card.
 */
package paintxmascard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
    private int x, y;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public XmasCard() {
        inputRecName();
        inputMerryXmas();
        inputSignature();
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

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        paintRecName(g);
        paintMerryXmas(g);
        paintSignature(g);
    }
    
    private void paintRecName(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();
        y = Consts.MINY_DIS + charHeight;
        g.drawString(recName, Consts.MINX_DIS, y);
    }

    private void paintMerryXmas(Graphics g) {
        int displayWidth = Consts.MAXX_DIS - Consts.MINX_DIS;
        g.setFont(new Font("Edwardian Script ITC", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        int charHeight = fm.getAscent();

        // concatinate words into a subStr and paint it centerized
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
                int startX = (Consts.MAXX_DIS - Consts.MINX_DIS) / 2 - midW;
                g.drawString(subStr, Consts.MINX_DIS + startX, y);
                subStr = nextT + " ";
            }
        }
        // for painting the last piece if any
        if (!subStr.equals(" ")) {
            y = y + Consts.NEXTLINE;
            int subStrWidth = fm.stringWidth(subStr);
            int midW = subStrWidth / 2;
            int startX = (Consts.MAXX_DIS - Consts.MINX_DIS) / 2 - midW;
            g.drawString(subStr, Consts.MINX_DIS + startX, y);
        }
    }

    private void paintSignature(Graphics g) {
        g.setFont(new Font("Time", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int startX = Consts.MAXX_DIS - fm.stringWidth(signature);
        int charHeight = fm.getAscent();
        y = y + Consts.NEXTLINE + charHeight;
        g.drawString(signature, startX, y);
    }
}
