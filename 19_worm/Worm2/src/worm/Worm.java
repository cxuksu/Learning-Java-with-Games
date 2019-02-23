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
    private int head, next, tail;
    private int nUnit; // total number of body units (include headP)

    public Worm() {
        super();
        wormBody = new ArrayList();
        initLen = 5;
    }

    @Override
    public void initSprite() {
        // add the head unit
        setX((Consts.MAXX - Consts.MINX) / 2 - Consts.WORM_UNIT_RADIUS);
        setY((Consts.MAXY - Consts.MINY) / 2 - Consts.WORM_UNIT_RADIUS);
        wHead = new Point(getX(), getY());
        wormBody.add(wHead);
        // add remaining body units (initLen - 1)
        for (int i = 1; i < initLen; i++) {
            wormBody.add(new Point(getX() - i * 2 * Consts.WORM_UNIT_RADIUS, getY()));
        }

        // set up pointers
        head = 0;
        next = 1;
        tail = initLen - 1; //i = initLen; the tail index = i - 1;
        nUnit = initLen;
        direction = Consts.EAST;
        setVisible(true);
        setActive(true);
    }

    @Override
    public void updateSprite() {
        next = head;
        head = tail;
        Point nextP;
        nextP = wormBody.get(next);

        // set up the direction = EAST
        switch (direction) {
            case Consts.EAST:
                wormBody.set(this.head, new Point(
                        nextP.x + 2 * Consts.WORM_UNIT_RADIUS, nextP.y));
                break;
            default:
        }
        tail = tail - 1;
        if (tail == -1) {
            tail = nUnit - 1;
        }
    }

    @Override
    public void paintSprite(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        Point headP = wormBody.get(this.head);
        g2d.fillOval(headP.x, headP.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        g2d.setColor(Color.BLACK);
        int ptr = next;
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
