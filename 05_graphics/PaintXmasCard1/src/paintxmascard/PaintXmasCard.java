/*
 * PaintXmasCard.java - The main class of the project PaintXmasCard.
 */
package paintxmascard;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author cxu
 */
public class PaintXmasCard extends JFrame {

    private XmasCard aCard;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PaintXmasCard() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paint A Christmas Card");
        setSize(500, 400);

        aCard = new XmasCard();
        add(aCard);

        setVisible(true);
    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new PaintXmasCard();
    }
}
