/*
 * FieldWall.java - A class for implementing a bouncing wall that consists
 * of four wallUnits.
 */
package pongstru;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author cxu
 */
public class FieldWall extends AbsSprite2D {

    private Rectangle[] aWall;

    public FieldWall() {
        super();
    }

    @Override
    public void initSprite() {
        aWall = new Rectangle[4];
        // top wall unit
        aWall[0] = new Rectangle(Consts.MINX, Consts.MINY,
                Consts.MAXX - Consts.MINX, Consts.WALL_THICK);
        // bottom wall unit
        aWall[1] = new Rectangle(Consts.MINX, Consts.MAXY - Consts.WALL_THICK,
                Consts.MAXX - Consts.MINX, Consts.WALL_THICK);
        // left wall unit
        aWall[2] = new Rectangle(Consts.MINX, Consts.MINY,
                Consts.WALL_THICK, Consts.FIELD_HEIGHT);
        // right wall unit
        aWall[3] = new Rectangle(Consts.MAXX - Consts.WALL_THICK, Consts.MINY,
                Consts.WALL_THICK, Consts.FIELD_HEIGHT);
        setActive(false);
        setVisible(true);
    }

    @Override
    public void updateSprite() {
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.decode("#bbaa00"));
        for (int i = 0; i < 4; i++) {
            g2d.fillRect(aWall[i].x, aWall[i].y, aWall[i].width, aWall[i].height);
        }
    }

    public Rectangle[] getaWall() {
        return aWall;
    }

    public void setaWall(Rectangle[] aWall) {
        this.aWall = aWall;
    }
}
