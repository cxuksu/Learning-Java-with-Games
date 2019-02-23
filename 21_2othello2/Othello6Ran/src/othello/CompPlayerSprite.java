/*
 * CompPlayerSprite.java - A class that implements a computer player.
 */
package othello;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class CompPlayerSprite extends AbsSprite {

    private boolean cpTurn;
    private boolean foundFirst;
    private Point theValidMove;
    private CellBoard theCellBoard;
    private PieceBoard thePieceBoard;
    private ArrayList<Point> validMoves;
    private GameCanvas gameCanvas;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CompPlayerSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        validMoves = new ArrayList<Point>();
        setActive(true);
        setVisible(false);
    }
    
    @Override
    public void updateSprite() {
        // until all flip finished caused by human player
        // compPlayer then starts its play
        if (isCpTurn()) {
            allPossibleValidMoves();
            if (validMoves.size() > 0) {
                theValidMove = randomSelect(validMoves);
            } else {
                System.out.println("No more possible moves");
                gameCanvas.stopGame();
            }

            int newRow = theValidMove.x;
            int newCol = theValidMove.y;
            int x = newCol * Consts.CELLW + Consts.CELLW / 2;
            int y = newRow * Consts.CELLH + Consts.CELLH / 2;
            // simulate a mouse move and a mouse click
            // just like a human player does
            theCellBoard.setMoveXY(x, y);
            thePieceBoard.setClickXY(x, y);
        }
        setCpTurn(false);
    }

    // go through the entire cellBoard and look for
    // all possible valid moves
    public void allPossibleValidMoves() {
        validMoves.clear();
        PieceSprite[][] thePieceB = 
                (PieceSprite[][])thePieceBoard.getPieceBoard();
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                // only check null positions -- a final smart idea
                if (thePieceB[row][col] == null) {
                    theCellBoard.setNewRow(row);
                    theCellBoard.setNewCol(col);
                    // check all rules for valid moves of (row, col)
                    theCellBoard.fireCheckRules();
                    if (theCellBoard.isRuleValid()) {
                        validMoves.add(new Point(row, col));
                        theCellBoard.setRuleValid(false);
                    }
                }
            }
        }
    }
    
    public Point randomSelect(ArrayList<Point> validMoves) {
        int selected = (int) (Math.random() * (validMoves.size()));
        return validMoves.get(selected);
    }
    
    // go through the entire cellBoard and look for
    // all possible valid moves
    public void firstValidMove() {
        foundFirst = false;
        PieceSprite[][] thePieceB
                = (PieceSprite[][]) thePieceBoard.getPieceBoard();
        for (int row = 0; row < Consts.MAXCELL; row++) {
            for (int col = 0; col < Consts.MAXCELL; col++) {
                // only check null positions -- a final smart idea
                if (thePieceB[row][col] == null) {
                    theCellBoard.setNewRow(row);
                    theCellBoard.setNewCol(col);
                    // check all rules for valid moves of (row, col)
                    theCellBoard.fireCheckRules();
                    if (theCellBoard.isRuleValid()) {
                        theValidMove = new Point(row, col);
                        theCellBoard.setRuleValid(false);
                        foundFirst = true;
                    }
                }
                if (foundFirst) {
                    break;
                }
            }
            if (foundFirst) {
                break;
            }
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
    }

    public void setCellBoard(CellBoard cellBoard) {
        this.theCellBoard = cellBoard;
    }

    public void setPieceBoard(PieceBoard pieceBoard) {
        this.thePieceBoard = pieceBoard;
    }

    public boolean isCpTurn() {
        return cpTurn;
    }

    public void setCpTurn(Boolean b) {
        this.cpTurn = b;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
