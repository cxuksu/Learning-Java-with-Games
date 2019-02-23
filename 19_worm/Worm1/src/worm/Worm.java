/*
 * Worm.java - A class that implements an animated worm body
 */
package worm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author cxu
 */
public class Worm extends AbsSprite2D {

    private ArrayList<Point> wormBody;
    private int initLen;
    private Point wHead;
    private int direction;
    private int headP, prevP, tailP;
    private int nUnit; // total number of body units (include head)

    public Worm() {
        wormBody = new ArrayList();
        initLen = 5;
    }

    @Override
    public void initSprite() {
        // add the head
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.WORM_UNIT_RADIUS);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.WORM_UNIT_RADIUS);
        wHead = new Point(getX(), getY());
        wormBody.add(wHead);
        // add remaining body units (initLen - 1)
        int i;
        for (i = 1; i < initLen; i++) {
            wormBody.add(new Point(getX() - i * 2 * Consts.WORM_UNIT_RADIUS, getY()));
        }

        // set up pointers
        headP = 0;
        prevP = 1;
        tailP = i - 1; //i = initLen; the tail index = i - 1;
        nUnit = initLen;
        direction = Consts.EAST;
        setVisible(true);
        setActive(false);
    }

    @Override
    public void updateSprite() {
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        Point head = wormBody.get(headP);
        g2d.fillOval(head.x, head.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        g2d.setColor(Color.BLACK);
        int ptr = prevP;
        for (int i = 0; i < nUnit - 1; i++) {
            g2d.fillOval(wormBody.get(ptr).x, wormBody.get(ptr).y,
                    2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
            ptr = (ptr + 1) % (nUnit);
        }
    }

    public ArrayList<Point> getWormBody() {
        return wormBody;
    }
}
