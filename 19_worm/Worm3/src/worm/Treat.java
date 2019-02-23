/*
 * Treat.java - A class that implements a treat.
 */

package worm;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author cxu
 */
public class Treat extends AbsSprite2D {
    
    public Treat() {
        super();
    }
    
    @Override
    public void initSprite() {
        boolean regXY = true;
        boolean regTreat = true;
        while (regXY && regTreat) {
            regXY = false;
            regTreat = false;
            
            int xRan = (int) Math.abs(Math.random() * Consts.MAXX) + 1;
            if ((xRan + Consts.TREAT_WIDTH) > Consts.MAXX) {
                regXY = true;
            } else if (xRan < Consts.MINX_DIS) {
                regXY = true;
            } else {
                setX(xRan);
            }
            int yRan = (int) Math.abs(Math.random() * Consts.MAXY_DIS) + 1;
            if ((yRan + Consts.TREAT_HEIGHT) > Consts.MAXY_DIS) {
                regXY = true;
            } else if (yRan < Consts.MINY_DIS) {
                regXY = true;
            } else {
                setY(yRan);
            }
        }
        setVisible(true);
        setActive(false);
    }
    
    @Override
    public void updateSprite() {
    }
    
    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.decode("#00dd00")); // light green
        g2d.fillRect(getX(), getY(), Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
    }
}
