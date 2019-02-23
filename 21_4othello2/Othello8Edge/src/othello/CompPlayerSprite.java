/*
 * CompPlayerSprite.java - A class that implements a computer player.
 */
package othello;

import java.awt.Graphics2D;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class CompPlayerSprite extends AbsSprite {

    private boolean cpTurn;
    private Point theValidMove;
    private CellBoard theCellBoard;
    private PieceBoard thePieceBoard;

    private ArrayList<Point> validMoveAry;
    private ArrayList<Integer> totalNumFlipAry;
    private int totalNumFlip;
    private PropertyChangeSupport stopGame;
    // an element of allClosest is a Point of (cloest, index)
    private ArrayList<Point> allClosest;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CompPlayerSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        validMoveAry = new ArrayList<Point>();
        totalNumFlipAry = new ArrayList<Integer>();
        stopGame = new PropertyChangeSupport(this);
        allClosest = new ArrayList<Point>();
        setActive(true);
        setVisible(false);
    }

    @Override
    public void updateSprite() {
        // until all flip finished caused by human player
        // compPlayer then starts its play
        if (isCpTurn()) {
            allPossibleValidMoves();
            theValidMove = findBestMove();
            if (theValidMove == null) {
                System.out.println("No more possible move");
                fireTerminate();
            } else {
                int newRow = theValidMove.x;
                int newCol = theValidMove.y;
                int x = newCol * Consts.CELLW + Consts.CELLW / 2;
                int y = newRow * Consts.CELLH + Consts.CELLH / 2;
                // simulate a mouse move and a mouse click
                // just like a human player does
                theCellBoard.setMoveXY(x, y);
                thePieceBoard.setClickXY(x, y);
            }
        }
        setCpTurn(false);
    }

    // go through the entire cellBoard and look for
    // all possible valid moves
    public void allPossibleValidMoves() {
        validMoveAry.clear();
        totalNumFlipAry.clear();
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
                        // for the "best" algorithm, get totalNumFlip
                        validMoveAry.add(new Point(row, col));
                        totalNumFlipAry.add(theCellBoard.getTotalNumFlip());
                        theCellBoard.setRuleValid(false);
                    }
                }
            }
        }
    }

    // based on closest to edges and then maximum number of flipping pieces
    public Point findBestMove() {
        int bestIdx = 0;
        int largestNum = 0;
        int aNum;

        allClosest.clear();
        findClosest();
        if (allClosest.size() == 1) {
            bestIdx = (int) (allClosest.get(0).getY());
        } else {
            // if multiple valid cells have the same degree of closest, 
            // select the one that makes max number of flipping piece
            for (int k = 0; k < allClosest.size(); k++) {
                int cellIdx = (int) (((Point) (allClosest.get(k))).getY());
                aNum = (totalNumFlipAry.get(cellIdx));
                if (aNum > largestNum) {
                    largestNum = aNum;
                    bestIdx = cellIdx;
                }
            }
        }
        return validMoveAry.get(bestIdx);
    }

    public void findClosest() {
        int toL, toR, toT, toB;
        int lessX, lessY, lessXY;
        int closest = -1;
        int aClosest;
        Point aPair;

        // looking for a valid cell that is closest to an edge
        for (int i = 0; i < validMoveAry.size(); i++) {
            Point coor = validMoveAry.get(i);
            toL = Math.abs((int) coor.getX() - 0);
            toR = Math.abs((int) coor.getX() - (Consts.MAXCELL - 1));
            toT = Math.abs((int) coor.getY() - 0);
            toB = Math.abs((int) coor.getY() - (Consts.MAXCELL - 1));
            if (toL < toR) {
                lessX = toL;
            } else {
                lessX = toR;
            }
            if (toT < toB) {
                lessY = toT;
            } else {
                lessY = toB;
            }
            if (lessX < lessY) {
                lessXY = lessX;
            } else {
                lessXY = lessY;
            }
            // the first element of allClosest always keeps the closest.
            // other elements in allClosest are equal to the first element.
            // if a new closest comes, update the first element and remove
            // all remaining elements
            if (closest == -1) { // the first time
                closest = lessXY; // to indicate no more first time
                aPair = new Point(lessXY, i);
                allClosest.add(aPair);
            } else { // not the first time
                // replacing or adding the index of the closest
                aClosest = (int) (allClosest.get(0).getX()); // return lessXY
                if (lessXY < aClosest) { // a new closest
                    aPair = new Point(lessXY, i);
                    allClosest.add(0, aPair);
                    if (allClosest.size() > 1) {
                        for (int j = 1; j < allClosest.size(); j++) {
                            allClosest.remove(j);
                        }
                    }
                } else if (lessXY == aClosest) { // same degree of closest
                    aPair = new Point(lessXY, i);
                    allClosest.add(aPair);
                }
            }
        }
    }

    public void fireTerminate() {
        stopGame.firePropertyChange("terminateGame", "-1", "1");
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        stopGame.addPropertyChangeListener(pcl);
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

    public ArrayList<Integer> getTotalNumFlipAry() {
        return totalNumFlipAry;
    }

    public void setTotalNumFlipAry(ArrayList<Integer> totalNumFlipAry) {
        this.totalNumFlipAry = totalNumFlipAry;
    }

    public int getTotalNumFlip() {
        return totalNumFlip;
    }

    public void setTotalNumFlip(int totalNumFlip) {
        this.totalNumFlip = totalNumFlip;
    }
}
