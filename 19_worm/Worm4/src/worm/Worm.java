/*
 * Worm.java - A class that implements an animated worm body
 */
package worm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
    private Treat treat;
    private GameCanvas gameCanvas;

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
        moveHeadOneStep();
        // if the worm eats the treat
        if (eatTreat()) {
            treat.initSprite();
            insertNewHead();
            // pause it for a half second
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
            }
        }
        if (collideEdge()) {
            AllClips.getClips(Consts.GAME_OVER).play();
            gameCanvas.stopGame();
        }
    }

    public boolean eatTreat() {
        boolean eat = false;
        Rectangle treatRect = new Rectangle(treat.getX(), treat.getY(),
                Consts.TREAT_WIDTH, Consts.TREAT_HEIGHT);
        Point wHeadP = wormBody.get(head);
        Rectangle wUnitRect = new Rectangle(wHeadP.x, wHeadP.y,
                2 * Consts.WORM_UNIT_RADIUS, 2 * Consts.WORM_UNIT_RADIUS);
        if (treatRect.intersects(wUnitRect)) {
            eat = true;
            gameCanvas.setNumOfEat(gameCanvas.getNumOfEat() + 1);
            new AllClips();
            AllClips.getClips(Consts.EAT_TREAT).play();
        }
        return eat;
    }

    // replace the original tail position with a new head object
    // it keeps the total number of units but move the head one step
    public void moveHeadOneStep() {
        next = head;
        head = tail;
        Point nextPoint;
        nextPoint = wormBody.get(next);
        Point newHeadP = prepareNewHead(nextPoint);

        wormBody.set(head, newHeadP); // replace the tail with new head
        tail = tail - 1;
        if (tail == -1) {
            tail = nUnit - 1;
        }
    }

    // insert a new head object at the head position and nUnit++
    public void insertNewHead() {
        next = head;
        head = tail;
        Point nextPoint;
        nextPoint = wormBody.get(next);
        Point newHeadP = prepareNewHead(nextPoint);

        wormBody.add(head + 1, newHeadP);
        head = head + 1;
        nUnit++;
    }

    // create a new head object: x = nextP.x + a unit
    public Point prepareNewHead(Point nextPoint) {
        Point newHeadP = null;
        switch (direction) {
            case Consts.EAST:
                newHeadP = new Point(nextPoint.x + 2 * Consts.WORM_UNIT_RADIUS,
                        nextPoint.y);
                break;
            case Consts.WEST:
                newHeadP = new Point(nextPoint.x - 2 * Consts.WORM_UNIT_RADIUS,
                        nextPoint.y);
                break;
            case Consts.SOUTH:
                newHeadP = new Point(nextPoint.x,
                        nextPoint.y + 2 * Consts.WORM_UNIT_RADIUS);
                break;
            case Consts.NORTH:
                newHeadP = new Point(nextPoint.x,
                        nextPoint.y - 2 * Consts.WORM_UNIT_RADIUS);
                break;
            default:
        }
        return newHeadP;
    }

    public boolean collideEdge() {
        boolean hitEdge = false;
        Point headP = wormBody.get(head);
        if (headP.x <= Consts.MINX) {
            hitEdge = true;
        } else if (headP.x >= Consts.MAXX) {
            hitEdge = true;
        } else if (headP.y <= Consts.MINY) {
            hitEdge = true;
        } else if (headP.y >= Consts.MAXY) {
            hitEdge = true;
        }
        return hitEdge;
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if ((this.direction == Consts.EAST)
                || (this.direction == Consts.WEST)) {
            if ((direction == Consts.SOUTH) || (direction == Consts.NORTH)) {
                this.direction = direction;
            }
        } else if ((this.direction == Consts.SOUTH)
                || (this.direction == Consts.NORTH)) {
            if ((direction == Consts.EAST) || (direction == Consts.WEST)) {
                this.direction = direction;
            }
        }
    }

    public ArrayList<Point> getWormBody() {
        return wormBody;
    }

    public Treat getTreat() {
        return treat;
    }

    public void setTreat(Treat treat) {
        this.treat = treat;
    }

    public void setGameCanvas(GameCanvas gameCanvas) {
        this.gameCanvas = gameCanvas;
    }
}
