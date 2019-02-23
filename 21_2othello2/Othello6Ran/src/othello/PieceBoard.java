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

    private PieceSprite[][] pieceBoard;
    private int clickX = -1, clickY = -1;
    private Color curPieceColor;
    private PropertyChangeSupport pChange;

    private int ruleValidRow = -1, ruleValidCol = -1;
    //private boolean ruleValid = false;

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
        // invoke updateSprite() of all pieceSprites for possible flipping
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                if (pieceBoard[row][col] != null) {
                    pieceBoard[row][col].updateSprite();
                }
            }
        }
        
        if (isValidClick()) {
            int newRow = getRow(clickY);
            int newCol = getCol(clickX);
            // only for the possible playing cell; ignore other cells
            if ((newRow == ruleValidRow) && (newCol == ruleValidCol)) {
                addPiece(newRow, newCol, curPieceColor);
                setRuleValid(-1, -1, false);
                fireClickDone();
            }
        }
        // reset the isValidClick() back to false
        // otherwise, the color will continue changing
        clickX = -1;
        clickY = -1;
        
        // verify all of pieceSprites finish the flipping pieces
        boolean allFlipStop = true;
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                if ((pieceBoard[row][col] != null) && 
                        (!(pieceBoard[row][col].isFlipStop()))) {
                    allFlipStop = false;
                    break;
                }
            }
        }
        // and then if current piece color is black (computer splayer
        // plays black piece), it submits the signal "cpStarts"
        if ((allFlipStop == true) && (curPieceColor == Consts.BLACKP)) {
            pChange.firePropertyChange("cpStarts", -1, 1);
        }
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

    public void setRuleValid(int row, int col, boolean b) {
        ruleValidRow = row;
        ruleValidCol = col;
        //this.ruleValid = b;
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
