/*
 * PaintXmasCard3.java - The main class of the project PaintXmasCard3.
 */
package paintxmascard;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author cxu
 */
public class PaintXmasCard3 extends JFrame {
    private XmasCard aCard;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PaintXmasCard3() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paint A Christmas Card");
        setSize(Consts.CV_WIDTH, Consts.CV_HEIGHT);
        
        aCard = new XmasCard();
        add(aCard);
        
        setVisible(true);
    }
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new PaintXmasCard3();
    }
}

