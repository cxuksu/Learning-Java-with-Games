/*
 * PlayerBoard.java - A class that defines a player board, which displays
 * the current piece for indicating the current player.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class PlayerBoard extends AbsSprite2D {
    PieceSprite aPiece;
    private Color curPieceColor;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PlayerBoard() {
        super();
        aPiece = new PieceSprite();
        initSprite();
    }
    
    @Override
    public void initSprite() {
        setX(Consts.PLAYERX);
        setY(Consts.PLAYERY);
        setWidth(Consts.PLAYERW);
        setHeight(Consts.PLAYERH);
        setActive(false);
        setVisible(true);
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect(getX(), getY(),
                Consts.PLAYERW, Consts.PLAYERH);
        g2d.setColor(Color.RED);
        g2d.drawString("Current Player", getX()+18, getY()+40);
        
        aPiece.setX(getX()+getWidth()/2-Consts.PIECEW/2);
        aPiece.setY(getY()+(getHeight()+40)/2-Consts.PIECEH/2);
        aPiece.setWidth(Consts.PIECEW);
        aPiece.setHeight(Consts.PIECEH);
        aPiece.setColor(getCurPieceColor());
        aPiece.paintSprite(g2d);
    }
    
    @Override
    public void updateSprite() {    
    }

    public Color getCurPieceColor() {
        return curPieceColor;
    }

    public void setCurPieceColor(Color curPieceColor) {
        this.curPieceColor = curPieceColor;
    }
}
