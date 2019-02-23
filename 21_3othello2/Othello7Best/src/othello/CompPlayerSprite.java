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

    private ArrayList<Point> validMoveList;
    private ArrayList<Integer> totalNumFlipList;
    private int totalNumFlip;
    private PropertyChangeSupport stopGame;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public CompPlayerSprite() {
        initSprite();
    }

    @Override
    public void initSprite() {
        validMoveList = new ArrayList<Point>();
        totalNumFlipList = new ArrayList<Integer>();
        stopGame = new PropertyChangeSupport(this);
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
        validMoveList.clear();
        totalNumFlipList.clear();
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
                        validMoveList.add(new Point(row, col));
                        totalNumFlipList.add(theCellBoard.getTotalNumFlip());
                        theCellBoard.setRuleValid(false);
                    }
                }
            }
        }
    }

    public Point findBestMove() {
        int bestIdx = 0;
        int largestNum = 0;
        int aNum;
        Point thePoint = null;
        if (!(totalNumFlipList.isEmpty())) {
            for (int i = 0; i < totalNumFlipList.size(); i++) {
                aNum = totalNumFlipList.get(i);
                if (aNum > largestNum) {
                    largestNum = aNum;
                    bestIdx = i;
                }
            }
            //System.out.println("largestNum = " + largestNum);
            thePoint = validMoveList.get(bestIdx);
        }
        return thePoint;
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

    public ArrayList<Integer> getTotalNumFlipList() {
        return totalNumFlipList;
    }

    public void setTotalNumFlipList(ArrayList<Integer> totalNumFlipList) {
        this.totalNumFlipList = totalNumFlipList;
    }

    public int getTotalNumFlip() {
        return totalNumFlip;
    }

    public void setTotalNumFlip(int totalNumFlip) {
        this.totalNumFlip = totalNumFlip;
    }
}
