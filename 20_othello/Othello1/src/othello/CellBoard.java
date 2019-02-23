/*
 * CellBoard.java -- A class that defines the cellBoard of 8x8 cells.
 */
package othello;

import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class CellBoard extends AbsSprite {

    private CellSprite[][] cellBoard;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CellBoard() {
        super();
        initSprite();
    }

    @Override
    public void initSprite() {
        int startX = Consts.GAPW;
        int startY = Consts.GAPW;
        cellBoard = new CellSprite[Consts.MAXCELL][Consts.MAXCELL];
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                CellSprite aCell = new CellSprite();
                aCell.setX(startX);
                aCell.setY(startY);
                cellBoard[row][col] = aCell;
                startX += (Consts.CELLW + Consts.GAPW);
            }
            startX = Consts.GAPW;
            startY += (Consts.CELLH + Consts.GAPW);
        }
        setActive(false);
        setVisible(true);
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Consts.BGCOLOR);
        g2d.fillRect(0, 0, Consts.BOARDW, Consts.BOARDH);
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                cellBoard[row][col].paintSprite(g2d);
            }
        }
    }

    @Override
    public void updateSprite() {
    }
}
