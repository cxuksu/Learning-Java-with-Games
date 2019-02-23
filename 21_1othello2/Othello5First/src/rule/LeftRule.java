/*
 * LeftRule.java - A rule that places a piece on the right position
 * to flip the opponent pieces on the left direction.
 */
package rule;

import java.awt.Color;
import othello.Consts;

/**
 *
 * @author cxu
 */
public class LeftRule extends AbsRule {
    int startRow, startCol;
    int untilRow, untilCol;
    
    public LeftRule() {
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
        if (((newCol-1) >= 0) &&
              ((getPieceBoard())[newRow][newCol-1] != null)) {
            if (!(pieceBoard[newRow][newCol-1].getColor().equals
                    (theColor))) {
                if (getFlipFlag()) {
                    startRow = newRow;
                    startCol = newCol - 1;
                    untilRow = newRow;
                    untilCol = newCol - 1;
                }
                int col = newCol - 2;
                while ((col >= 0) && (pieceBoard[newRow][col] != null)) {
                    if (pieceBoard[newRow][col].getColor().equals(theColor)) {
                        if (getFlipFlag()) {
                            untilRow = newRow;
                            untilCol = col;
                        }
                        flag = true;
                        break;
                    } else {
                        col = col - 1;
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
            while (col > untilCol) {
                pieceBoard[row][col].setFlipStop(false);
                pieceBoard[row][col].setColor(theColor);
                
                if (theColor.equals(Consts.WHITEP)) {
                    pieceBoard[row][col].setImageIdx(0);
                    pieceBoard[row][col].setNumTurns(6);
                } else if (theColor.equals(Consts.BLACKP)) {
                    pieceBoard[row][col].setImageIdx(6);
                    pieceBoard[row][col].setNumTurns(12);
                }
                col--;
            }
        }
        untilRow = startRow;
        untilCol = startCol;
    }
}

