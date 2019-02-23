/*
 * UpRule.java - A rule that places a piece on the down position
 * to flip the opponent pieces on the up direction.
 */
package rule;

import java.awt.Color;
import othello.Consts;

/**
 *
 * @author cxu
 */
public class UpRule extends AbsRule {

    private int startRow, startCol;
    private int untilRow, untilCol;

    public UpRule() {
        super();
    }

    @Override
    public boolean isValid(int newRow, int newCol) {
        boolean valid = false;
        Color theColor = getCurPieceColor();
        if (verify(newRow, newCol, theColor)) {
            valid = true;
        }
        return valid;
    }

    public boolean verify(int newRow, int newCol, Color theColor) {
        boolean flag = false;
        if (((newRow - 1) >= 0)
                && ((getPieceBoard())[newRow - 1][newCol] != null)) {
            if (!(pieceBoard[newRow - 1][newCol].getColor().equals(theColor))) {
                if (getFlipFlag()) {
                    startRow = newRow - 1;
                    startCol = newCol;
                    untilRow = newRow - 1;
                    untilCol = newCol;
                }
                int row = newRow - 2;
                while ((row >= 0) && (pieceBoard[row][newCol] != null)) {
                    if (pieceBoard[row][newCol].getColor().equals(theColor)) {
                        if (getFlipFlag()) {
                            untilRow = row;
                            untilCol = newCol;
                        }
                        flag = true;
                        break;
                    } else {
                        row = row - 1;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void flipPiece() {
        if (getFlipFlag()) {
            Color theColor = getCurPieceColor();
            int row = startRow;
            int col = startCol;
            while (row > untilRow) {
                pieceBoard[row][col].setFlipStop(false);
                pieceBoard[row][col].setColor(theColor);

                if (theColor.equals(Consts.WHITEP)) {
                    pieceBoard[row][col].setImageIdx(0);
                    pieceBoard[row][col].setNumTurns(6);
                } else if (theColor.equals(Consts.BLACKP)) {
                    pieceBoard[row][col].setImageIdx(6);
                    pieceBoard[row][col].setNumTurns(12);
                }
                row--;
            }
        }
        untilRow = startRow;
        untilCol = startCol;
    }
}
