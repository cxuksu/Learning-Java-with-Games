/*
 * AbsRule.java - An abstract class as the root of the rule inheritance hierarchy.
 */
package rule;

import java.awt.Color;
import othello.PieceSprite;

/**
 *
 * @author cxu
 */
public abstract class AbsRule {

    protected Color curPieceColor;
    protected PieceSprite[][] pieceBoard;
    private boolean flipFlag = false;

    public AbsRule() {
    }

    public abstract boolean isValid(int newRow, int newCol);

    public abstract void flipPiece();

    public void setCurPieceColor(Color curPieceColor) {
        this.curPieceColor = curPieceColor;
    }

    public Color getCurPieceColor() {
        return curPieceColor;
    }

    public void setPieceBoard(PieceSprite[][] pieceBoard) {
        this.pieceBoard = pieceBoard;
    }

    public PieceSprite[][] getPieceBoard() {
        return pieceBoard;
    }

    public void setFlipFlag(boolean b) {
        this.flipFlag = b;
    }

    public boolean getFlipFlag() {
        return flipFlag;
    }
}
