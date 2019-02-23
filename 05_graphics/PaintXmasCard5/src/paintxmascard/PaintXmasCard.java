/*
 * PaintXmasCard -- to paint a Christmas card using graphical programming
 */
package paintxmascard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author cxu
 */
public class PaintXmasCard extends JFrame {
    private XmasCard aCard;
    private Component canvas;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PaintXmasCard() {
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Paint String");
        Dimension d = new Dimension(1000, 400);
        setSize(d.width, d.height);
        
        aCard = new XmasCard();
        aCard.setDimension(d);
        canvas = add(aCard);
        
        setVisible(true);
    }
    
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new PaintXmasCard();
    }
}

