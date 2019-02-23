/*
 * RuleBase.java - A class that defines a rule base for instantiating 
 * all flipping rules and inserting them into the linear data structure
 */
package rule;

import java.awt.Color;
import java.util.ArrayList;
import othello.PieceSprite;

/**
 *
 * @author cxu
 */
public class RuleBase {

    private ArrayList<AbsRule> rules;
    private PieceSprite[][] pieceBoard;
    private Color curPieceColor;
    private int newRow, newCol;

    public RuleBase() {
        rules = new ArrayList<AbsRule>();
    }

    public void initRules() {
        UpRule upRule = new UpRule();
        rules.add(upRule);
        DownRule downRule = new DownRule();
        rules.add(downRule);
        LeftRule leftRule = new LeftRule();
        rules.add(leftRule);
        RightRule rightRule = new RightRule();
        rules.add(rightRule);
        UpRightRule upRightRule = new UpRightRule();
        rules.add(upRightRule);
        UpLeftRule upLeftRule = new UpLeftRule();
        rules.add(upLeftRule);
        DownRightRule downRightRule = new DownRightRule();
        rules.add(downRightRule);
        DownLeftRule downLeftRule = new DownLeftRule();
        rules.add(downLeftRule);
    }

    public void setRulesPieceBoard() {
        for (int i = 0; i < rules.size(); i++) {
            rules.get(i).setPieceBoard(pieceBoard);
        }
    }

    public void setRulesColor() {
        for (int idx = 0; idx < rules.size(); idx++) {
            rules.get(idx).setCurPieceColor(getCurPieceColor());
        }
    }

    public void flipPiece() {
        for (int idx = 0; idx < rules.size(); idx++) {
            AbsRule aRule = rules.get(idx);
            aRule.setFlipFlag(true);
            aRule.isValid(newRow, newCol);
            aRule.flipPiece();
            aRule.setFlipFlag(false);
        }
    }

    public boolean isRuleValid() {
        boolean ruleValid = false;
        for (int idx = 0; idx < rules.size(); idx++) {
            if ((rules.get(idx)).isValid(newRow, newCol)) {
                ruleValid = true;
            }
        }
        return ruleValid;
    }

    public void setPieceBoard(PieceSprite[][] pieceBoard) {
        this.pieceBoard = pieceBoard;
        setRulesPieceBoard();
    }

    public PieceSprite[][] getPieceBoard() {
        return pieceBoard;
    }

    public void setCurPieceColor(Color curPieceColor) {
        this.curPieceColor = curPieceColor;
        setRulesColor();
    }

    public Color getCurPieceColor() {
        return this.curPieceColor;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }
}
