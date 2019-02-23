/*
 * XmasCard.java - A canvas for painting a Christmas card.
 */
package paintxmascard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
    ReadFile readFile;

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
        g.setFont(new Font("Time", Font.BOLD, 24));
        g.drawString(recName, 50, 100);
        g.setFont(new Font("Algerian", Font.BOLD, 30));
        g.drawString(merryXmas, 80, 150);
        g.setFont(new Font("Time", Font.BOLD, 24));
        g.drawString(signature, 50, 200);
    }
}
