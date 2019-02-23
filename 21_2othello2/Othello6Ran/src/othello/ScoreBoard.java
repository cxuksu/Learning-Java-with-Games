/*
 * ScoreBoard.java - A class that defines a score board, which displays
 * the current scores of both while player and black player.
 */
package othello;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class ScoreBoard extends AbsSprite2D {
    
    private int numWhite;
    private int numBlack;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ScoreBoard() {
        super();
        numWhite = 0;
        numBlack = 0;
        initSprite();
    }
    
    @Override
    public void initSprite() {
        setX(Consts.SCOREX);
        setY(Consts.SCOREY);
        setWidth(Consts.SCOREW);
        setHeight(Consts.SCOREH);
        setColor(Color.WHITE);
        setActive(false);
        setVisible(true);
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
        g2d.setColor(Color.RED);
        int x = getX() + 14;
        int y = getY() + 48;
        g2d.drawString("White Player: " + numWhite, x, y);
        y = y + 40;
        g2d.drawString("Black Player: " + numBlack, x, y);
        int diff = numWhite - numBlack;
        if (diff > 0) {
            y += 40;
            g2d.drawString("While wins " + diff, x, y);
        } else if (diff < 0) {
            y += 40;
            g2d.drawString("Black wins " + Math.abs(diff), x, y);
        } else {
            y += 40;
            g2d.drawString("Two players tied", x, y);
        }
    }
    
    @Override
    public void updateSprite() {
        
    }

    public int getNumWhite() {
        return numWhite;
    }
    
    public void setNumWhite(int numWhite) {
        this.numWhite = numWhite;
    }

    public int getNumBlack() {
        return numBlack;
    }
    
    public void setNumBlack(int numBlack) {
        this.numBlack = numBlack;
    }
}
