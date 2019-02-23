/*
 * XmasCard.java -- A class for defining a Christmas card
 */
package printxmascard;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class XmasCard {

    private String recName;
    private String merryXmas;
    private String signature;

    public XmasCard() throws IOException {
        initCard();
    }

    private void initCard() throws IOException {
        recName = JOptionPane.showInputDialog("Enter recipient's name: ");
        merryXmas = readMerryXmas();
        signature = JOptionPane.showInputDialog("Enter sender's name: ");
    }

    private String readMerryXmas() throws IOException {
        ReadFile rFile;
        rFile = new ReadFile();
        return "    " + rFile.readGreeting();
    }

    public String concatenateStrings() {
        return (recName + "\n\n" + merryXmas + "\n\n" + signature);
    }

    public void printCard() {
        String cardInfo;
        cardInfo = concatenateStrings();
        System.out.println(cardInfo);
    }
}
