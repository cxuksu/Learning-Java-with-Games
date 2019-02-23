/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class PieceBoard extends AbsSprite {

    PieceSprite[][] pieceBoard;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceBoard() {
        super();
        initSprite();
        //initPieces(); // called by GameCanvas.java
    }

    @Override
    public void initSprite() {
        pieceBoard = new PieceSprite[Consts.MAXCELL][Consts.MAXCELL];
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                pieceBoard[row][col] = null;
            }
        }
        setActive(false);
        setVisible(true);
    }

    public void addPiece(int row, int col, Color color) {
        PieceSprite aPiece = new PieceSprite();
        // edge gap + col*(CELLW+GAP) + ((CELLW-PIECEW)/2)
        aPiece.setX(Consts.GAPW + col * (Consts.CELLW + Consts.GAPW) + 2);
        aPiece.setY(Consts.GAPW + row * (Consts.CELLH + Consts.GAPW) + 2);
        aPiece.setColor(color);
        aPiece.setFill(true);
        pieceBoard[row][col] = aPiece;
    }

    public void initPieces() {
        addPiece(3, 3, Consts.WHITEP);
        addPiece(4, 4, Consts.WHITEP);
        addPiece(3, 4, Consts.BLACKP);
        addPiece(4, 3, Consts.BLACKP);
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                if (pieceBoard[row][col] != null) {
                    pieceBoard[row][col].paintSprite(g2d);
                }
            }
        }
    }

    @Override
    public void updateSprite() {
    }
}
