/*
 * PieceSprite.java - A class that defines a piece object.
 */
package othello;

import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class PieceSprite extends AbsSprite2D {
    
    private boolean fill;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceSprite() {
        initSprite();
    }
    
    @Override
    public void initSprite() {
         setWidth(Consts.PIECEW);
                setHeight(Consts.PIECEH);
                setFill(true);
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        if ((getColor() == Consts.WHITEP) || (getColor() == Consts.BLACKP)) {
            g2d.setColor(getColor());
            g2d.fillOval(getX(), getY(), getWidth(), getHeight());
        }
    }
    
    @Override
    public void updateSprite() {
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
