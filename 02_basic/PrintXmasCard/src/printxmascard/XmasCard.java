/*
 * XmasCard.java -- A class for defining a Christmas card
 */
package printxmascard;

import javax.swing.JOptionPane;

/**
 *
 * @author cxu
 */
public class XmasCard {

    private String recName;
    private String merryXmas;
    private String signature;

    public XmasCard() {
        initCard();
    }
    
    private void initCard() {
        recName = JOptionPane.showInputDialog("Enter recipient's name: ");
        merryXmas = "    Merry Christmas and Happy New Year!!!";
        signature = JOptionPane.showInputDialog("Enter sender's name: ");
    }

    public void printCard() {
        System.out.println(recName);
        System.out.println();
        System.out.println(merryXmas);
        System.out.println();
        System.out.println(signature);
    }
}
