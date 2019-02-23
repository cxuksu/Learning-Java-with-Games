/*
 * PieceBoard.java - A class defines a pieceBoard for keeping track of
 * dynamic behaviors of pieces placed by players.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author cxu
 */
public class PieceBoard extends AbsSprite {

    PieceSprite[][] pieceBoard;
    private int clickX = -1, clickY = -1;
    private Color curPieceColor;
    private PropertyChangeSupport pChange;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PieceBoard() {
        super();
        initSprite();
        pChange = new PropertyChangeSupport(this);
    }

    @Override
    public void initSprite() {
        pieceBoard = new PieceSprite[Consts.MAXCELL][Consts.MAXCELL];
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                pieceBoard[row][col] = null;
            }
        }
        setActive(true);
        setVisible(true);
    }

    public void addPiece(int row, int col, Color color) {
        PieceSprite aPiece = new PieceSprite();
        // edge gap + col*(CELLW+GAP) + ((CELLW-PIECEW)/2)
        aPiece.setX(Consts.GAPW + col * (Consts.CELLW + Consts.GAPW) + 2);
        aPiece.setY(Consts.GAPW + row * (Consts.CELLH + Consts.GAPW) + 2);
        aPiece.setColor(color);
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
        if (isValidClick()) {
            int newRow = getRow(clickY);
            int newCol = getCol(clickX);
            addPiece(newRow, newCol, curPieceColor);
            fireClickDone();
        }
        // reset the isValidClick() back to false
        // otherwise, the color will continue changing
        clickX = -1;
        clickY = -1;
    }

    public void setClickXY(int x, int y) {
        this.clickX = x;
        this.clickY = y;
    }

    public boolean isValidClick() {
        boolean validClick = false;
        if ((clickX >= 0) && (clickX <= Consts.BOARDW)
                && (clickY >= 0) && (clickY <= Consts.BOARDH)) {
            validClick = true;
        }
        return validClick;
    }

    public void fireClickDone() {
        pChange.firePropertyChange("clickDone", "-1", "1");
    }

    public int getRow(int mouseY) {
        int numRow = mouseY / (Consts.CELLH + Consts.GAPW);
        return numRow;
    }

    public int getCol(int mouseX) {
        int numCol = mouseX / (Consts.CELLW + Consts.GAPW);
        return numCol;
    }

    public Color getCurPieceColor() {
        return curPieceColor;
    }

    public void setCurPieceColor(Color curPieceColor) {
        this.curPieceColor = curPieceColor;
    }

    public PieceSprite[][] getPieceBoard() {
        return pieceBoard;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pChange.addPropertyChangeListener(pcl);
    }
}
